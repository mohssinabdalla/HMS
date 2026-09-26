package model;

public class RevenueSummaryReport extends Report {

    public RevenueSummaryReport(String reportId, String generatedDate) {
        super(reportId, generatedDate);
    }

    @Override
    public void generateReport() {
        // show all the money related stuff here
        System.out.println("=== Revenue Summary Report ===");
        System.out.println("Report ID: " + this.reportId);
        System.out.println("Generated Date: " + this.dateGenerated);
        System.out.println("Summary: Total Consultation Fees, Insurance Claims Processed, Net Revenue.");
    }
    
    public String getReportDetails() {
        return """
               === Revenue Summary Report ===
               Report ID: %s
               Generated Date: %s
               
               Summary: Total Consultation Fees, Insurance Claims, Monthly Revenue.
               """.formatted(this.reportId, this.dateGenerated);
    }
}