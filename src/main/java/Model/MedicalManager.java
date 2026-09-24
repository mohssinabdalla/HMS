package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if (newDept == null) return;
        
        File deptFile = new File("src/main/data/departments.txt");
        if (deptFile.getParentFile() != null) {
            deptFile.getParentFile().mkdirs(); // make sure data folder exists first
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(deptFile, true))) {
            bw.write(newDept.toFileString());
            bw.newLine();
            // System.out.println("debug: new dept saved");
        } catch (IOException ex) {
            System.out.println("file error when creating department: " + ex.getMessage());
        }
    }

    public void updateDepartment(Department deptInfo) {
        if (deptInfo == null) return;
        
        File deptFile = new File("src/main/data/departments.txt");
        if (!deptFile.exists()) {
            System.out.println("no department file found to update.");
            return;
        }

        List<String> tempData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(deptFile))) {
            String rowLine;
            while ((rowLine = br.readLine()) != null) {
                String[] row = rowLine.split(",");
                // check if this is the department we want to edit
                if (row.length > 0 && row[0].trim().equals(deptInfo.getDepartmentId())) {
                    tempData.add(deptInfo.toFileString()); 
                } else {
                    tempData.add(rowLine); // keep original data
                }
            }
        } catch (IOException ex) {
            System.out.println("error reading file: " + ex.getMessage());
        }

        // overwrite the file with new list
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(deptFile, false))) {
            for (String text : tempData) {
                bw.write(text);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("error rewriting file: " + ex.getMessage());
        }
    }

    public void designRoster(ShiftRoster targetRoster) {
        if (targetRoster == null) return;
        
        File rosterFile = new File("src/main/data/rosters.txt");
        if (rosterFile.getParentFile() != null) {
            rosterFile.getParentFile().mkdirs(); 
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rosterFile, true))) {
            bw.write(targetRoster.getRosterId() + "," + targetRoster.getWeekStartDate());
            bw.newLine();
        } catch (IOException ex) {
            System.out.println("could not save roster: " + ex.getMessage());
        }
    }

    public void modifyRoster(ShiftRoster rosterData) {
        if (rosterData == null) return;
        
        File rosterFile = new File("src/main/data/rosters.txt");
        if (!rosterFile.exists()) return;

        List<String> fileContent = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rosterFile))) {
            String lineData;
            while ((lineData = br.readLine()) != null) {
                String[] cols = lineData.split(",");
                // match the roster id to update
                if (cols.length > 0 && cols[0].trim().equals(rosterData.getRosterId())) {
                    fileContent.add(rosterData.getRosterId() + "," + rosterData.getWeekStartDate()); 
                } else {
                    fileContent.add(lineData); 
                }
            }
        } catch (IOException e) {
            System.out.println("read error: " + e.getMessage());
        }

        // save back
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rosterFile, false))) {
            for (String record : fileContent) {
                bw.write(record);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("write error: " + e.getMessage());
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
}