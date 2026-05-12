package CrimeDispatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MyController implements Controller{

    @Override
    public String displayIncidentsByPriority(String date){
        // Load incidents into memory

        ArrayList<Incident> incidents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/crime_incidents.csv"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                incidents.add(new Incident(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // I couldn't find insert() or append() in regular 'String' class
        StringBuilder result = new StringBuilder();

        // Display header
        result.append("Rank ID\tLocation\tDistrict\tCrime Type\tSev Date\tTime");

        // Sorting the incidents by severity (implemented Comparable)
        Collections.sort(incidents);

        // my JavaScript knowledge is useful here too
        incidents.forEach(incident -> {
            result.append(incident.toString()).append("\n");
        });

        return result.toString();
    };

    @Override
    public String dispatchPoliceUnits(String date) {
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
