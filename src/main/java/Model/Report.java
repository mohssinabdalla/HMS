package model;

public abstract class Report {
    
    // common details for any report
    protected String reportId;
    protected String dateGenerated;

    public Report(String reportId, String generatedDate) {
        this.reportId = reportId;
        this.dateGenerated = generatedDate;
    }

    // child classes have to write their own print logic
    public abstract void generateReport();

    // --- getters down here ---
    public String getReportId() {
        return reportId;
    }

    public String getGeneratedDate() {
        return dateGenerated; // return the internal variable
    }
}