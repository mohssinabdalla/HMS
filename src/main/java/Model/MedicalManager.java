package model;

import service.MedicalDataService;

public class MedicalManager extends User {

    private String managerId;

    public MedicalManager(String userId, String username, String password, String fullName,
                          String email, String phoneNumber, String managerId) {
        super(userId, username, password, fullName, email, phoneNumber, "MedicalManager");
        this.managerId = managerId;
    }

    @Override
    public boolean updateOwnProfile(String fullName, String email, String phoneNumber) {
        // simple update for manager profile details
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        return true;
    }

    public void createDepartment(Department newDept) {
        if (newDept != null) {
            MedicalDataService.saveDepartment(newDept);
        }
    }

    public void updateDepartment(Department deptInfo) {
        if (deptInfo != null) {
            MedicalDataService.updateDepartment(deptInfo);
        }
    }

    public void designRoster(ShiftRoster targetRoster) {
        if (targetRoster != null) {
            MedicalDataService.saveRoster(targetRoster);
        }
    }

    public void modifyRoster(ShiftRoster rosterData) {
        if (rosterData != null) {
            MedicalDataService.updateRoster(rosterData);
        }
    }

    public Report viewReport(String reportCategory) {
        // generate a random id for now
        String tempId = "REP" + (System.currentTimeMillis() % 10000);
        String todayDate = "2026-09-23";

        if ("HospitalMetrics".equalsIgnoreCase(reportCategory)) {
            HospitalMetricsReport metrics = new HospitalMetricsReport(tempId, todayDate);
            metrics.generateReport();
            return metrics;
        } else if ("RevenueSummary".equalsIgnoreCase(reportCategory)) {
            RevenueSummaryReport revenue = new RevenueSummaryReport(tempId, todayDate);
            revenue.generateReport();
            return revenue;
        }
        return null; // return nothing if category is wrong
    }

    @Override
    public String toFileString() {
        // combine parent user string with manager id
        return super.toFileString() + "," + managerId;
    }
    
    // --- Getters & Setters ---
    public String getManagerId() { 
        return managerId; 
    }
    
    public void setManagerId(String managerId) { 
        this.managerId = managerId; 
    }
    
    private String name = "Dr. Alex Smith";

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }    
}