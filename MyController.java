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
        // Load police units into memory

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
        // sort by both severity and time

        List<Incident> sortedIncidents = new ArrayList<>(incidents.stream()
                .sorted(
                        Comparator
                                .comparingInt((Incident incident) -> incident.severity).reversed()
                                .thenComparing((incident) -> incident.time, Comparator.reverseOrder())
                                .thenComparing((incident) -> incident.date, Comparator.reverseOrder())
                )
                .toList());
        return "";
    }

    @Override
    public String findShortestPatrolRoute(String crimeLocation) {
        return "";
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
