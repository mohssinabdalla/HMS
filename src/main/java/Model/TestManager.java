package model;

public class TestManager {
    public static void main(String[] args) {
        System.out.println("--- Starting Medical Manager System Test ---");

        // 1. Create a Manager Instance
        MedicalManager manager = new MedicalManager(
            "U001", "manager_john", "pass123", 
            "Dr. John Smith", "john@hospital.com", "0123456789", "MM001"
        );

        // 2. Test Department Creation
        Department newDept = new Department("DP004", "Neurology", "Brain & Nervous System");
        manager.createDepartment(newDept);
        System.out.println("[SUCCESS] Department created and written to file.");

        // 3. Test Roster Creation
        ShiftRoster roster = new ShiftRoster("R003", "2026-10-05");
        manager.designRoster(roster);
        System.out.println("[SUCCESS] Roster created and written to file.");

        // 4. Test Report Viewing
        System.out.println("\n--- Testing Hospital Metrics Report ---");
        manager.viewReport("HospitalMetrics");

        System.out.println("\n--- Testing Revenue Summary Report ---");
        manager.viewReport("RevenueSummary");

        System.out.println("\n--- All Tests Completed Successfully ---");
    }
}