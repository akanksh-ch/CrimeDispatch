package crimedispatch;

import java.util.Scanner;

/**
 * A simple text-based user interface for the Birmingham Crime Pattern Analysis
 * & Police Dispatch System.
 *
 * @author Hai Wang
 * @version 14/04/2026
 */
public class TUI {

    // The controller that handles all business logic
    private Controller controller;

    // Scanner for reading user input from the standard input stream
    private Scanner stdIn;

    /**
     * Constructs a TUI with the given controller and starts the main input loop.
     *
     * @param controller The controller to delegate operations to.
     */
    public TUI(Controller controller) {
        this.controller = controller;

        // Creates a Scanner object for obtaining user input
        stdIn = new Scanner(System.in);

        // Main application loop — keeps running until the user exits
        while (true) {
            displayMenu();
            getAndProcessUserOption();
        }
    }

    /**
     * Displays the application header and the summary of menu options.
     */
    private void displayMenu() {
        display(header());
        display(menu());
    }

    /**
     * Reads a user command from standard input and processes it.
     */
    private void getAndProcessUserOption() {
        String command = stdIn.nextLine().trim();

        switch (command) {
            case "1":
                // FR1: Display all crime incidents ranked by priority
                display("Displaying crime incidents by priority...");
                display(controller.displayIncidentsByPriority("2026-04-14"));
                break;

            case "2":
                // FR2: Dispatch police units to incidents
                display("Dispatching police units to crime incidents...");
                display(controller.dispatchPoliceUnits("2026-04-14"));
                break;

            case "3":
                // FR3: Find shortest patrol route to a given crime location
                display("Enter the crime location: ");
                String crimeLocation = stdIn.nextLine().trim();
                display("Finding shortest patrol route to: " + crimeLocation);
                display(controller.findShortestPatrolRoute(crimeLocation));
                break;

            case "4":
                // FR4: Analyse crime hotspots by district
                display("Analysing crime hotspots by district...");
                display(controller.analyseCrimeHotspots("2026-04-14"));
                break;

            case "5":
                // Exit the application
                display("Goodbye!");
                System.exit(0);
                break;

            default:
                // Unrecognised command
                display(unrecognisedCommandErrorMsg(command));
        }
    }

    /**
     * Returns the application header string.
     *
     * @return the header
     */
    private static String header() {
        return "\nBirmingham Crime Pattern Analysis & Police Dispatch System\n";
    }

    /**
     * Returns the user menu string.
     *
     * @return the menu
     */
    private static String menu() {
        return "Enter the number associated with your chosen menu option.\n" +
               "1: Display crime incidents by priority.\n" +
               "2: Dispatch police units to crime incidents.\n" +
               "3: Find shortest patrol route to a crime location.\n" +
               "4: Analyse crime hotspots by district.\n" +
               "5: Exit this application.\n";
    }

    /**
     * Displays the given information to the standard output stream.
     *
     * @param info the information to display
     */
    private void display(String info) {
        System.out.println(info);
    }

    /**
     * Returns an error message for an unrecognised command.
     *
     * @param command the unrecognised command entered by the user
     * @return an error message string
     */
    private static String unrecognisedCommandErrorMsg(String command) {
        return String.format("Cannot recognise the given command: %s.%n", command);
    }
}
