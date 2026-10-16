package model;

/**
 * Represents feedback submitted by a Patient.
 */
public class Feedback {

    private String feedbackId;
    private int rating;
    private String comment;

    public Feedback(String feedbackId, int rating, String comment) {
        this.feedbackId = feedbackId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters

    public String getFeedbackId() {
        return feedbackId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    /**
     * Submits the feedback.
     */
    public void submit() {
        System.out.println("Feedback " + feedbackId + " submitted.");
    }

    /**
     * Converts feedback into CSV format.
     */
    public String toFileString() {
        return String.join(",",
                feedbackId,
                String.valueOf(rating),
                comment
        );
    }
}