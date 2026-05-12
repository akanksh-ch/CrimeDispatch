package CrimeDispatch;

import java.time.LocalDate;
import java.time.LocalTime;

public class Incident implements Comparable<Incident> {
    String id;
    String location;
    String district;
    String crime_type;
    int severity;
    LocalDate date;
    LocalTime time;

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
    public int compareTo(Incident other) {
        return Integer.compare(this.severity, other.severity);
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
