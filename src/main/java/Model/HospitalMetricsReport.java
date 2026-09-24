package model;

public class HospitalMetricsReport extends Report {

    public HospitalMetricsReport(String reportId, String generatedDate) {
        super(reportId, generatedDate);
    }

    @Override
    public void generateReport() {
        // print out the hospital stats to the console
        System.out.println("=== Hospital Metrics Report ===");
        System.out.println("Report ID: " + this.reportId);
        System.out.println("Generated Date: " + this.dateGenerated);
        System.out.println("Metrics: Total Patient Admissions, Occupancy Rate, Active Shift Count.");
        
        // System.out.println("debug: metrics generated");
    }
}