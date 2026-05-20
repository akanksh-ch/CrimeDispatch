package CrimeDispatch;

/**
 * The entry point for Crime Dispatch Program
 * Creates a MyController instance and launches the system test evaluations.
 *
 * @author Akanksh Chitimalla
 * @version 20/05/2026
 */
public class Main {

    /**
     * Main runtime configuration trigger point.
     *
     * @param args Standard CLI startup sequence adjustments.
     */
    public static void main(String[] args) {
        new TUI(new MyController());
    }
}