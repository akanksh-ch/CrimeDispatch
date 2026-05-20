package CrimeDispatch;

/**
 * Entity modeling institutional properties for localized police deployment bases.
 *
 * @author Akanksh Chitimalla
 * @version 20/05/2026
 */
public class PoliceStation {
    private final String name;
    private final String location;

    /**
     * Constructs a PoliceStation by parsing a comma-separated line from a CSV file.
     * Expected format: StationName,Location
     *
     * @param csvLine The raw line split containing explicit registry variables.
     */
    public PoliceStation(String csvLine) {
        String[] items = csvLine.split(",");
        // Basic length check to prevent IndexOutOfBoundsException on malformed lines
        if (items.length >= 2) {
            this.name = items[0].trim();
            this.location = items[1].trim();
        } else {
            throw new IllegalArgumentException("Invalid CSV line format for PoliceStation: " + csvLine);
        }
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return name + "\t" + location;
    }
}