package CrimeDispatch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * A utility class providing helper methods for reading from and writing to
 * data files used by the Birmingham Crime Pattern Analysis & Police Dispatch
 * System.
 *
 * @author Hai Wang
 * @version 14/04/2026
 */
public class Utility {

    /**
     * Saves the given string data to a file with the specified file name.
     *
     * @param data     The string data to be saved.
     * @param fileName The name of the file to save to.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void saveFile(String data, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(data);
        writer.close();
    }

}