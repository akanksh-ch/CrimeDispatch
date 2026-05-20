package CrimeDispatch;

import CrimeDispatch.Incident;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MyController implements Controller{

    ArrayList<Incident> incidents;
    ArrayList<CrimeDispatch.PoliceUnit> policeUnits;
    ArrayList<CrimeDispatch.PoliceStation> policeStations;
    CrimeDispatch.DirectedWeightedGraph<CrimeDispatch.Vertex<String>, CrimeDispatch.Edge, Double> dwgraph;
    CrimeDispatch.Dijkstra dijkstra;

    public MyController(){
        // Load incidents into memory

        this.incidents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/crime_incidents.csv"))) {

            br.readLine(); // skip header

            String line;

            while ((line = br.readLine()) != null) {
                incidents.add(new Incident(line));
            }

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Initialise the police units as well

        this.policeUnits = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/police_units.csv"))) {

            br.readLine(); // skip header

            String line;

            while ((line = br.readLine()) != null) {
                policeUnits.add(new PoliceUnit(line));
            }

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Load graph

        // Initialised graph
        this.dwgraph = new CrimeDispatch.DirectedWeightedGraph();

        try (BufferedReader br = new BufferedReader(new FileReader("data/road_network.csv"))) {
            br.readLine(); // Skips header
            String line;

            while((line = br.readLine()) != null) {
                String[] data = line.split(",");
                CrimeDispatch.Vertex source = new CrimeDispatch.Vertex(data[0]);
                CrimeDispatch.Vertex target = new CrimeDispatch.Vertex(data[1]);

                dwgraph.addVertex(source);
                dwgraph.addVertex(target);
                dwgraph.addEdge(new CrimeDispatch.Edge<>(source, target, Double.parseDouble(data[2])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CrimeDispatch.Dijkstra dijkstra = new CrimeDispatch.Dijkstra(dwgraph);

        // Initialise and load police stations from the CSV file
        this.policeStations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/police_stations.csv"))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines if any exist in the CSV
                if (!line.trim().isEmpty()) {
                    policeStations.add(new PoliceStation(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Critical Error: Could not load data/police_stations.csv", e);
        }

    }

    @Override
    public String displayIncidentsByPriority(String date){
        // sort by severity
        // using https://www.geeksforgeeks.org/java/java-comparator-interface/
        incidents.sort(Comparator
                // refer https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#comparingInt-java.util.function.ToIntFunction-
                .comparingInt((Incident incident) -> incident.severity).reversed());

        // I couldn't find insert() or append() in regular 'String' class
        StringBuilder result = new StringBuilder();

        // Display header
        result.append("High priority crime incidents\n\n");
        result.append("Rank ID\tLocation\tDistrict\tCrime Type\tSev Date\tTime\n");

        for(int i = 0; i < incidents.size(); i ++){

            if (incidents.get(i).date.equals(LocalDate.parse(date))){

                result.append(String.format("%d\t", i+1)) // Incident rank
                        .append(incidents.get(i).toString()) // Full incident details
                        .append("\n"); // New line

            }
        };

        return result.toString();
    };

    @Override
    public String dispatchPoliceUnits(String date) {
        // sort by both severity and time

        List<Incident> sortedIncidents = new ArrayList<>(incidents.stream()
                .sorted(
                        Comparator
                                .comparingInt((Incident incident) -> incident.severity).reversed()
                                .thenComparing((incident) -> incident.time, Comparator.reverseOrder())
                                .thenComparing((incident) -> incident.date, Comparator.reverseOrder())
                )
                .toList());

        // Process assignments using least-loaded strategy

        StringBuilder result = new StringBuilder();

        result.append("Police Unit Dispatch (").append(date).append(")\n");
        result.append("Incident ID\tLocation\tDistrict\tCrime Type\tSev\tDate\tTime\tAssigned Unit (ID\tName\tStatus\tCurrent Loc\tMaxCap\tLoad)\n");

        for (Incident incident : sortedIncidents) {
            PoliceUnit bestTargetUnit = null;
            int minLoad = Integer.MAX_VALUE;

            // Find the available unit with the lowest current workload
            for (PoliceUnit unit : policeUnits) {
                if (unit.status.equalsIgnoreCase("Yes") && unit.incidentsHandled < unit.maxCapacity) {
                    if (unit.incidentsHandled < minLoad) {
                        minLoad = unit.incidentsHandled;
                        bestTargetUnit = unit;
                    }
                }
            }

            // Assign unit or add to waiting list if all are full
            if (bestTargetUnit != null) {
                bestTargetUnit.incidentsHandled++;

                // Using your string appending rules seamlessly paired with your new toString formats
                result.append(incident.toString()).append("\t")
                        .append(bestTargetUnit.toString()).append("\n");
            }

        }

        return result.toString();
    };

    @Override
    public String findShortestPatrolRoute(String crimeLocation) {
        // 1. Convert the input string location to a proper structural Vertex object
        CrimeDispatch.Vertex<String> targetVertex = dwgraph.getVertex(crimeLocation);
        if (targetVertex == null) {
            return "Error: The location '" + crimeLocation + "' does not exist in the road network.\n";
        }

        List<CrimeDispatch.Vertex> bestPath = null;
        String bestStationName = "";
        double minDistance = Double.MAX_VALUE;

        CrimeDispatch.Dijkstra dijkstraEngine = new CrimeDispatch.Dijkstra(dwgraph);

        for (PoliceStation station : policeStations) {
            CrimeDispatch.Vertex<String> stationVertex = dwgraph.getVertex(station.getLocation());

            // Skip if this station's vertex location isn't present in the road network graph
            if (stationVertex == null) {
                continue;
            }

            List<CrimeDispatch.Vertex> currentPath = dijkstraEngine.findShortestRoute(stationVertex, targetVertex);

            // A valid traversal must have at least 2 nodes (Station -> Destination)
            // It must explicitly start at the station node and end at the crime scene target node
            if (currentPath != null && currentPath.size() >= 2
                    && currentPath.get(0).equals(stationVertex)
                    && currentPath.get(currentPath.size() - 1).equals(targetVertex)) {

                double currentDistance = dijkstraEngine.getRouteDistance(currentPath);
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    bestPath = currentPath;
                    bestStationName = station.getName();
                }
            }
        }

        // Handle disconnected edge paths cleanly or when no stations can reach the location
        if (bestPath == null || bestPath.isEmpty()) {
            return "No patrol route available from any operational police station to '" + crimeLocation + "'.\n";
        }

        StringBuilder result = new StringBuilder();
        result.append("Shortest Patrol Route\n--------------------------------------------------\n");
        result.append("Crime Location:         ").append(crimeLocation).append("\n");
        result.append("Nearest Police Station: ").append(bestStationName).append("\n");
        result.append("Route:                  ");

        for (int i = 0; i < bestPath.size(); i++) {
            result.append(bestPath.get(i).toString());
            if (i < bestPath.size() - 1) {
                result.append(" -> ");
            }
        }
        result.append("\n");
        result.append(String.format("Total Distance:         %.1f km\n", minDistance));

        return result.toString();
    }

    @Override
    public String analyseCrimeHotspots(String date) {
        StringBuilder result = new StringBuilder();

        // Make a hashmap of Location(string) and incidents(int) and insert all incidents, resulting in popularity hashmap
        Map<String, Integer> hotspots = new HashMap<>();

        for (Incident incident : incidents) {
            if (!hotspots.containsKey(incident.location)) { // if starting from null
                hotspots.put(incident.location, 1); // intialise to 1
            } else {
                hotspots.put(incident.location, hotspots.get(incident.location) + 1); // Increment counter
            }
        }

        // gather latest 3 incidents from every location (keys)

        // sort incidents
        List<Incident> sortedIncidents = new ArrayList<>(incidents.stream()
                .sorted(
                        Comparator.comparing((Incident incident) -> incident.location)
                                .thenComparing(incident -> incident.date, Comparator.reverseOrder())
                                .thenComparing(incident -> incident.time, Comparator.reverseOrder())
                )
                .toList());

        Map<String, List<Incident>> last3incidents = new HashMap<>();

        for (Incident incident : sortedIncidents) {
            List<Incident> list = last3incidents.computeIfAbsent(incident.location, key -> new ArrayList<>());
            if (list.size() < 3) {
                list.add(incident);
            }
        }


        // print table

        result.append("Crime Hotspot Analysis by District (" + date + ")\n");
        result.append("District\tTotal Incidents\t Recent Incidents (Last 3, Recent first)\n");

        for (String location : hotspots.keySet()) {
            result.append(String.format("%s\t%d\t%s\n",
                    location,
                    hotspots.get(location),
                    last3incidents.get(location).stream().map(incident -> incident.id).collect(Collectors.joining(", ", "(", ")"))
            ));
        }

        return result.toString();
    }
}
