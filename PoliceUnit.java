package CrimeDispatch;

public class PoliceUnit {
    public String id;
    public String name;
    public String status;
    public int maxCapacity;

    // To track incidents handled by this police unit
    public int incidentsHandled;

    public PoliceUnit(String policeUnit) {
        String[] items = policeUnit.split(",");

        this.id = items[0].trim();
        this.name = items[1].trim();
        this.status = items[2].trim();
        this.maxCapacity = Integer.parseInt(items[3].trim());

        // Initialised to 0 when loaded fresh from memory file
        this.incidentsHandled = 0;
    }

    @Override
    public String toString() {
        return id + "\t" +
                name + "\t" +
                status + "\t" +
                maxCapacity + "\t" +
                incidentsHandled;
    }
}