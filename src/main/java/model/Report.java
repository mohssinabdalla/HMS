package model;

public abstract class Report {
    
    // common details for report
    protected String reportId;
    protected String dateGenerated;

    public Report(String reportId, String generatedDate) {
        this.reportId = reportId;
        this.dateGenerated = generatedDate;
    }

    
    public abstract void generateReport();

    // --- getters  here ---
    public String getReportId() {
        return reportId;
    }

    public String getGeneratedDate() {
        return dateGenerated; // return the internal variable
    }
}