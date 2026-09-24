package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared file I/O utility. Every class needing to read/write a .txt data file
 * should use this instead of writing its own BufferedReader/BufferedWriter code.
 *
 * Design choice: read methods swallow IOExceptions internally and return an
 * empty list (a missing file just means "no data yet", not a crash). Write
 * methods return a boolean so the caller can decide how to react to failure
 * (e.g. show an error dialog) without needing their own try-catch.
 */
public class FileHelper {

    /**
     * Reads every line of a text file into a List<String>.
     * Returns an empty list if the file doesn't exist yet or can't be read —
     * callers don't need to handle an exception, just check if the list is empty.
     */
    public static List<String> readAllLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            // File not found or unreadable — treat as "no data yet", not a crash.
            System.out.println("Note: could not read " + filePath + " (" + e.getMessage() + ")");
        }
        return lines;
    }

    /**
     * Overwrites the ENTIRE file with the given lines. Use this for updates/deletes,
     * where you've already read all lines, modified the list in memory, and need
     * to save the full corrected version back.
     * Returns true if the write succeeded, false otherwise.
     */
    public static boolean writeAllLines(String filePath, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) { // false = overwrite
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error writing " + filePath + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Appends ONE line to the end of the file without touching existing content.
     * Use this for create operations, or append-only files like auditlog.txt.
     * Returns true if the append succeeded, false otherwise.
     */
    public static boolean appendLine(String filePath, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) { // true = append
            bw.write(line);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error appending to " + filePath + ": " + e.getMessage());
            return false;
        }
    }
}
