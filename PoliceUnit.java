package CrimeDispatch;

/**
 * Tracks systemic variables associated with unique field response operational vectors.
 *
 * @author Akanksh Chitimalla
 * @version 20/05/2026
 */
public class PoliceUnit {

    // Package-private unit attribute definitions
    String id;
    String name;
    String status;
    String current_location;
    int maxCapacity;
    int incidentsHandled;

    /**
     * Formulates instance components out of sequential parsed data strings.
     *
     * @param policeUnit Sequential string line elements extracted directly from log definitions.
     */
    public PoliceUnit(String policeUnit) {
        String[] items = policeUnit.split(",");
        this.id = items[0];
        this.name = items[1];
        this.status = items[2];
        this.current_location = items[3];
        this.maxCapacity = Integer.parseInt(items[4]);
        this.incidentsHandled = 0;
    }

    @Override
    public String toString() {
        return id + "\t" +
                name + "\t" +
                status + "\t" +
                current_location + "\t" +
                maxCapacity + "\t" +
                incidentsHandled;
    }
}