package CrimeDispatch;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyController implements Controller{

    ArrayList<Incident> incidents;

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
        incidents.sort(Comparator
                // refer https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#comparingInt-java.util.function.ToIntFunction-
                .comparingInt((incident) -> incident.severity));

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

        incidents.sort(Comparator
                .comparingInt((Incident incident) -> incident.severity)
                .thenComparing((incident) -> incident.time, Comparator.reverseOrder())
                .thenComparing((incident) -> incident.date, Comparator.reverseOrder())
        );
        return "";
    }

    @Override
    public String findShortestPatrolRoute(String crimeLocation) {
        return "";
    }

    @Override
    public String analyseCrimeHotspots(String date) {
        return "";
    }
}
