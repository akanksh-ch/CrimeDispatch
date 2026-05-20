package CrimeDispatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Central business logic coordinator implementing core functionalities for the
 * Birmingham Crime Pattern Analysis & Police Dispatch System.
 *
 * @author Akanksh Chitimalla
 * @version 20/05/2026
 */
public class MyController implements Controller {

    // Initialise all object storage
    ArrayList<Incident> incidents;
    ArrayList<CrimeDispatch.PoliceUnit> policeUnits;
    ArrayList<CrimeDispatch.PoliceStation> policeStations;
    CrimeDispatch.DirectedWeightedGraph<CrimeDispatch.Vertex<String>, CrimeDispatch.Edge<CrimeDispatch.Vertex<String>, Double>, Double> dwgraph;
    CrimeDispatch.Dijkstra<CrimeDispatch.Vertex<String>, CrimeDispatch.Edge<CrimeDispatch.Vertex<String>, Double>, Double> dijkstra;

    /**
     * Initializes systemic parameters and pipelines the ingestion parsing configuration algorithms.
     */
    public MyController() {
        // Load incidents into memory
        this.incidents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/crime_incidents.csv"))) {
            br.readLine();
            // skip header

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    incidents.add(new Incident(line));
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Initialise the police units as well
        this.policeUnits = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/police_units.csv"))) {
            br.readLine();
            // skip header

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    policeUnits.add(new PoliceUnit(line));
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Initialise the police stations as well
        this.policeStations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/police_stations.csv"))) {
            br.readLine();
            // skip header

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    policeStations.add(new PoliceStation(line));
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Load graph
        this.dwgraph = new CrimeDispatch.DirectedWeightedGraph<>();
        Map<String, CrimeDispatch.Vertex<String>> vertexCache = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/road_network.csv"))) {
            br.readLine();
            // Skips header
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String sourceName = data[0].trim();
                String targetName = data[1].trim();
                double distance = Double.parseDouble(data[2].trim());

                CrimeDispatch.Vertex<String> source = vertexCache.computeIfAbsent(sourceName, name -> new CrimeDispatch.Vertex<>(name));
                CrimeDispatch.Vertex<String> target = vertexCache.computeIfAbsent(targetName, name -> new CrimeDispatch.Vertex<>(name));

                dwgraph.addVertex(source);
                dwgraph.addVertex(target);
                dwgraph.addEdge(new CrimeDispatch.Edge<>(source, target, distance));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.dijkstra = new CrimeDispatch.Dijkstra<>(dwgraph); //
    }

    @Override
    public String displayIncidentsByPriority(String date) {
        // sort by severity using Java Comparator interface definitions
        incidents.sort(Comparator
                .comparingInt((Incident incident) -> incident.severity).reversed()
                .thenComparing((incident) -> incident.time));

        // I couldn't find insert() or append() in regular 'String' class
        StringBuilder result = new StringBuilder();

        // Display header
        result.append("High priority crime incidents\n\n");
        result.append("Rank ID\tLocation\tDistrict\tCrime Type\tSev Date\tTime\n");
        int rankCounter = 1;
        for(int i = 0; i < incidents.size(); i ++){
            if (incidents.get(i).date.equals(LocalDate.parse(date))){
                result.append(String.format("%d\t", rankCounter++)) // Incident rank
                        .append(incidents.get(i).toString()) // Full incident details
                        .append("\n"); // New line
            }
        };
        return result.toString();
    }

    @Override
    public String dispatchPoliceUnits(String date) {
        // sort by both severity and time
        List<Incident> sortedIncidents = new ArrayList<>(incidents.stream()
                .filter(incident -> incident.date.equals(LocalDate.parse(date)))
                .sorted(
                        Comparator
                                .comparingInt((Incident incident) -> incident.severity).reversed()
                                .thenComparing((incident) -> incident.time)
                )
                .toList());

        // Process assignments using least-loaded strategy
        StringBuilder result = new StringBuilder();
        result.append("Police Unit Dispatch (").append(date).append(")\n");
        result.append("Incident ID\tLocation\tDistrict\tCrime Type\tSev\tDate\tTime\tAssigned Unit (ID\tName\tStatus\tCurrent Loc\tMaxCap\tLoad)\n");

        List<Incident> waitingList = new ArrayList<>();
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
            } else {
                waitingList.add(incident);
            }
        }

        // print waiting list table if items remain unassigned
        result.append("\nIncidents Placed on Waiting List:\n");
        if (waitingList.isEmpty()) {
            result.append("None\n");
        } else {
            result.append("Incident ID\tLocation\tDistrict\tCrime Type\tSev\tDate\tTime\n");
            for (Incident waitingIncident : waitingList) {
                result.append(waitingIncident.toString()).append("\n");
            }
        }
        return result.toString();
    }

    @Override
    public String findShortestPatrolRoute(String crimeLocation) {
        // 1. Convert the input string location to a proper structural Vertex object
        CrimeDispatch.Vertex<String> targetVertex = dwgraph.getVertex(crimeLocation);
        if (targetVertex == null) {
            return "Error: The location '" + crimeLocation + "' does not exist in the road network.\n";
        }

        // Properly parameterized to match the clean types and fix the assignment error
        List<CrimeDispatch.Vertex<String>> bestPath = null;
        String bestStationName = "";
        double minDistance = Double.MAX_VALUE;

        // Explicit diamond types applied to lock type-safety with the graph definition
        CrimeDispatch.Dijkstra<CrimeDispatch.Vertex<String>, CrimeDispatch.Edge<CrimeDispatch.Vertex<String>, Double>, Double> dijkstraEngine =
                new CrimeDispatch.Dijkstra<>(dwgraph);

        // 2. Scan all stations to locate the mathematically nearest starting point
        for (PoliceStation station : policeStations) {
            CrimeDispatch.Vertex<String> stationVertex = dwgraph.getVertex(station.getLocation());
            if (stationVertex == null) {
                continue;
            }

            // Using explicit type variables to fetch the route path smoothly
            List<CrimeDispatch.Vertex<String>> currentPath = dijkstraEngine.findShortestRoute(stationVertex, targetVertex);

            // Ensure a valid path was found back to the target node
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

        // 3. Non-Functional Requirement Protection: Handle disconnected edge paths cleanly
        if (bestPath == null || bestPath.isEmpty()) {
            return "No patrol route available from any operational police station to '" + crimeLocation + "'.\n";
        }

        // 4. Construct the final Text-Based User Interface (TUI) display string
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
        LocalDate targetDate = LocalDate.parse(date);

        // Make a hashmap of Location(string) and incidents(int) and insert all incidents, resulting in popularity hashmap
        Map<String, Integer> hotspots = new HashMap<>();
        for (Incident incident : incidents) {
            if (incident.date.equals(targetDate)) {
                hotspots.put(incident.location, hotspots.getOrDefault(incident.location, 0) + 1);
            }
        }

        // gather latest 3 incidents from every location (keys) and sort incidents
        List<Incident> sortedIncidents = new ArrayList<>(incidents.stream()
                .filter(incident -> incident.date.equals(targetDate))
                .sorted(
                        Comparator.comparing((Incident incident) -> incident.location)
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

        // sort districts by total counts descending
        List<Map.Entry<String, Integer>> sortedDistricts = new ArrayList<>(hotspots.entrySet());
        sortedDistricts.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // print table
        result.append("Crime Hotspot Analysis by District (" + date + ")\n");
        result.append("District\tTotal Incidents\t Recent Incidents (Last 3, Recent first)\n");

        for (Map.Entry<String, Integer> entry : sortedDistricts) {
            String location = entry.getKey();
            int totalCount = entry.getValue();

            String recentIds = last3incidents.containsKey(location)
                    ? last3incidents.get(location).stream().map(incident -> incident.id).collect(Collectors.joining(", ", "(", ")"))
                    : "()";
            result.append(String.format("%s\t%d\t%s\n", location, totalCount, recentIds));
        }

        return result.toString();
    }
}