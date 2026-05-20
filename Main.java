package CrimeDispatch;

/**
 * The entry point for the Birmingham Crime Pattern Analysis & Police Dispatch System.
 * Creates a MyController instance and launches the text-based user interface.
 */
public class Main {

    public static void main(String[] args) {
        //new TUI(new MyController());

        MyController controller = new MyController();

        String testDate = "2026-04-14";
        String testLocation = "Sparkhill";

        System.out.println("FR1: Display Incidents By Priority");
        String priorityOutput = controller.displayIncidentsByPriority(testDate);
        System.out.println(priorityOutput);

        System.out.println("FR2: Dispatch Police Units");
        String dispatchOutput = controller.dispatchPoliceUnits(testDate);
        System.out.println(dispatchOutput);

        System.out.println("FR3: Find Shortest Patrol Route");
        String routeOutput = controller.findShortestPatrolRoute(testLocation);
        System.out.println(routeOutput);

        System.out.println("FR4: Analyse Crime Hotspots");
        String hotspotOutput = controller.analyseCrimeHotspots(testDate);
        System.out.println(hotspotOutput);

    }
}

