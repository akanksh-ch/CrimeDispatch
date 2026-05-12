package CrimeDispatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A controller interface for the Birmingham Crime Pattern Analysis & Police
 * Dispatch System. This controller defines the main features that the intended
 * prototype system is expected to provide.
 *
 * @author Hai Wang
 * @version 14/04/2026
 */
public interface Controller {

    /**
     * Reads all crime incidents from the data file and displays them ranked in
     * order of decreasing severity. Incidents with equal severity are further
     * ordered by the time they were reported (earliest first).
     *
     * @param date The date for which incidents should be displayed (format: YYYY-MM-DD).
     * @return A String representation of all crime incidents ranked by priority.
     */
    String displayIncidentsByPriority(String date){
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

        // my javascript knowledge is useful here too
        incidents.forEach(incident -> {
            result.append(incident.toString()).append("\n");
        });

        return result.toString();
    };

    /**
     * Allocates available police units to crime incidents based on the following
     * criteria:
     * <ul>
     *   <li>Incidents are processed in order of decreasing severity. Incidents
     *       with the same severity are processed in the order they were reported
     *       (earliest first).</li>
     *   <li>Only units with an Available Status of "Yes" may be assigned.</li>
     *   <li>Each unit has a Maximum Capacity and cannot be assigned more incidents
     *       than its capacity permits.</li>
     *   <li>At each step, the next incident is assigned to the available unit that
     *       has handled the fewest incidents so far (least-loaded approach). If
     *       multiple units are tied, any of them may be chosen.</li>
     * </ul>
     * If all available units reach their maximum capacity, remaining incidents are
     * placed on a waiting list and displayed separately.
     *
     * @param date The date of incidents to dispatch (format: YYYY-MM-DD).
     * @return A String representation of the dispatch assignments and waiting list.
     */
    String dispatchPoliceUnits(String date);

    /**
     * Accepts a crime location as input and computes the shortest route from each
     * police station to that location using the city road network. Identifies and
     * displays the nearest police station and the full shortest route.
     * Uses Dijkstra's algorithm to compute shortest paths.
     * If no path exists from any station to the given location, returns an
     * appropriate message.
     *
     * @param crimeLocation The name of the location where the crime has occurred.
     * @return A String representation of the nearest police station and the
     *         shortest patrol route to the crime location.
     */
    String findShortestPatrolRoute(String crimeLocation);

    /**
     * Analyses crime incidents grouped by district to identify crime hotspots.
     * For each district, computes the total number of incidents and retrieves the
     * last 3 most recently reported incidents (most recent first).
     * Districts are listed in order of decreasing total incident count.
     *
     * @param date The date for which hotspot analysis should be performed (format: YYYY-MM-DD).
     * @return A String representation of the crime hotspot analysis by district.
     */
    String analyseCrimeHotspots(String date);
}
