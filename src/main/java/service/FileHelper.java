package service;

import exceptions.DataFileNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared file I/O utility. Every class needing to read/write a .txt data file
 * should use this instead of writing its own BufferedReader/BufferedWriter code.
 *
 * UPDATED: readAllLines() now throws DataFileNotFoundException when the file
 * genuinely can't be found/opened, and only returns an empty List when the
 * file exists but has no content — these are now two distinct, unambiguous
 * outcomes, per your chosen design.
 */
public class FileHelper {

    /**
     * Reads every line of a text file into a List<String>.
     * Throws DataFileNotFoundException if the file can't be found/opened.
     * Returns an empty list ONLY if the file exists but has no content.
     */
    public static List<String> readAllLines(String filePath) throws DataFileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new DataFileNotFoundException("Data file not found: " + filePath);
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            // File exists but couldn't actually be opened/read (permissions, etc.)
            throw new DataFileNotFoundException("Could not read data file: " + filePath + " (" + e.getMessage() + ")");
        }
        return lines; // empty list here means: file exists, genuinely has no data yet
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
     * Creates the file automatically if it doesn't exist yet (this is how we'll
     * generate admins.txt, doctors.txt, etc. via code, rather than hand-typing them).
     * Returns true if the append succeeded, false otherwise.
     */
    public static boolean appendLine(String filePath, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) { // true = append (creates file if missing)
            bw.write(line);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error appending to " + filePath + ": " + e.getMessage());
            return false;
        }
    }
}
