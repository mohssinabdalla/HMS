package service;

import model.Feedback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles Patient feedback storage.
 */
public class PatientFeedbackService {

    private static final String FEEDBACK_FILE = "src/main/data/feedback.txt";
    
    public PatientFeedbackService() {
    }

    /**
     * Saves feedback into feedback.txt and records the action in auditlog.txt.
     */
    public boolean submitFeedback(Feedback feedback, String patientId) {

        if (feedback == null || patientId == null || patientId.isBlank()) {
            return false;
        }

        try (FileWriter fw = new FileWriter(FEEDBACK_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(feedback.toFileString());
            bw.newLine();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Counts how many feedback entries already exist, so Patient can
     * generate the next sequential feedback ID.
     */
    public int getFeedbackCount() {
        int count = 0;
        File file = new File(FEEDBACK_FILE);
        if (!file.exists()) {
            return 0;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}