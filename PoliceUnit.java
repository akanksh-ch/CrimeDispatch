package CrimeDispatch;

public class PoliceUnit {
    String id;
    String name;
    String status;
    String current_location;
    int maxCapacity;
    int incidentsHandled;

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