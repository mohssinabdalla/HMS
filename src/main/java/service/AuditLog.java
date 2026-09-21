package service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Append-only activity log. Every Admin action calls logAction() right before
 * returning. Deliberately append-only (never edits existing lines) so multiple
 * teammates working in parallel + Git rarely produces merge conflicts here.
 */
public class AuditLog {

    private static final String LOG_FILE = "data/auditlog.txt";

    public boolean logAction(String userId, String actionType, String targetEntity, String details) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logId = "L" + System.currentTimeMillis();
        String line = String.join(",", logId, userId, actionType, targetEntity, timestamp,
                details == null ? "" : details);

        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(line);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getLogsByUser(String userId) {
        List<String> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[1].equals(userId)) {
                    results.add(line);
                }
            }
        } catch (IOException e) {
            // File may not exist yet on first run — that's fine, just return empty list
        }
        return results;
    }

    public List<String> getLogsByDateRange(String startDate, String endDate) {
        // startDate/endDate format: yyyy-MM-dd
        List<String> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 4) {
                    String dateOnly = parts[4].split(" ")[0];
                    if (dateOnly.compareTo(startDate) >= 0 && dateOnly.compareTo(endDate) <= 0) {
                        results.add(line);
                    }
                }
            }
        } catch (IOException e) {
            // File may not exist yet
        }
        return results;
    }
}
