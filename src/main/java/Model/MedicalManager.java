package model;

import service.MedicalDataService;
import exceptions.DepartmentExceptions.DuplicateDepartmentException;
import exceptions.DepartmentExceptions.DepartmentNotFoundException;
import exceptions.RosterExceptions.ShiftConflictException;
import exceptions.RosterExceptions.InvalidShiftTimeException;
import exceptions.ReportExceptions.ReportGenerationException;

public class MedicalManager extends User {

    private String managerId;
    private String name = "Dr. Chen";
    
    public MedicalManager(String userId, String username, String password, String fullName,
                          String email, String phoneNumber, String managerId) {
        super(userId, username, password, fullName, email, phoneNumber, "MedicalManager");
        this.managerId = managerId;
    }
    
    public MedicalManager(String userId, String username, String password, String fullName, 
                          String email, String phoneNumber) {
        super(userId, username, password, fullName, email, phoneNumber, "MedicalManager");
        this.managerId = userId;
    }

    @Override
    public boolean updateOwnProfile(String fullName, String email, String phoneNumber) {
        // simple update for manager profile details
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        return true;
    }
    
    @Override
    public void loadExtraFields(String[] extraFields) {
        // Implemented to satisfy abstract method in User
    }
    
    public void createDepartment(Department newDept) throws DuplicateDepartmentException {
        if (newDept == null) {
            return;
        }

        if (MedicalDataService.departmentExists(newDept.getDepartmentId())) {
            throw new DuplicateDepartmentException("Department ID '" + newDept.getDepartmentId() + "' already exists!");
        }

        MedicalDataService.saveDepartment(newDept);
    }

    public void updateDepartment(Department deptInfo) throws DepartmentNotFoundException {
        if (deptInfo != null) {
            MedicalDataService.updateDepartment(deptInfo);
        }
    }

    public void designRoster(ShiftRoster targetRoster) throws ShiftConflictException, InvalidShiftTimeException {
        if (targetRoster != null) {
            MedicalDataService.saveRoster(targetRoster);
        }
    }

    public void modifyRoster(ShiftRoster rosterData) throws ShiftConflictException, InvalidShiftTimeException {
        if (rosterData != null) {
            MedicalDataService.updateRoster(rosterData);
        }
    }
    
    public Report viewReport(String reportCategory) throws ReportGenerationException {
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

        throw new ReportGenerationException("Failed to generate report: Unknown category '" + reportCategory + "'");
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "," + managerId;
    }

    // --- Getters & Setters ---
    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
    
    //STUBS FOR TEAMMATES TO IMPLEMENT(Patient & Admin)
    
    public int getTotalPatientAdmissions() {
        return 0;
    }

    public double getOccupancyRate() {
        return 0.0;
    }

    public int getActiveShiftCount() {
        return controller.MedicalManager.ShiftRosterDialog.getActiveShiftCount();
    }

    public double getTotalConsultationFees() {
        return 0.00;
    }

    public double getInsuranceClaims() {
        return 0.00;
    }

    public double getMonthlyRevenue() {
        return 0.00;
    }
    
}