package CrimeDispatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class MyController implements Controller{

    @Override
    public String displayIncidentsByPriority(String date){
        // Load incidents into memory

        ArrayList<Incident> incidents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/crime_incidents.csv"))) {

            br.readLine(); // skip header

            String line;

            while ((line = br.readLine()) != null) {
                incidents.add(new Incident(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // I couldn't find insert() or append() in regular 'String' class
        StringBuilder result = new StringBuilder();

        // Display header
        result.append("Rank ID\tLocation\tDistrict\tCrime Type\tSev Date\tTime\n");

        // Sorting the incidents by severity (implemented Comparable)
        Collections.sort(incidents);
        Collections.reverse(incidents);

        // my JavaScript knowledge is useful here too
        for(int i = 0; i < incidents.size(); i ++){

            if (incidents.get(i).date.equals(LocalDate.parse(date))){

                result.append(String.format("%d\t", i)) // Incident rank
                        .append(incidents.get(i).toString()) // Full incident details
                        .append("\n"); // New line

            }
        };

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
