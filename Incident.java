package CrimeDispatch;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data entity holding structural parameter representations of tracked emergency reporting logs.
 *
 * @author Akanksh Chitimalla
 * @version 20/05/2026
 */
public class Incident {

    // Package-private logging variable storage definitions
    String id;
    String location;
    String district;
    String crime_type;
    int severity;
    LocalDate date;
    LocalTime time;

    /**
     * Formulates an internal log mapping metrics by breaking down a parsed CSV line structure.
     *
     * @param incident A raw string entry extracted directly from file streams.
     */
    public Incident(String incident) {
        String[] items = incident.split(",");
        this.id = items[0];
        this.location = items[1];
        this.district = items[2];
        this.crime_type = items[3];
        this.severity = Integer.parseInt(items[4]);
        this.date = LocalDate.parse(items[5]);
        this.time = LocalTime.parse(items[6]);
    }

    @Override
    public String toString() {
        return id + "\t" +
                location + "\t" +
                district + "\t" +
                crime_type + "\t" +
                severity + "\t" +
                date + "\t" +
                time;
    }
}