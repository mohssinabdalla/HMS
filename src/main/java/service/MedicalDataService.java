package service;

import model.Department;
import model.ShiftRoster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicalDataService {

    private static final String DEPT_FILE = "src/main/data/departments.txt";
    private static final String ROSTER_FILE = "src/main/data/rosters.txt";

    public static void saveDepartment(Department newDept) {
        File deptFile = new File(DEPT_FILE);
        ensureDirectoryExists(deptFile);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(deptFile, true))) {
            bw.write(newDept.toFileString());
            bw.newLine();
        } catch (IOException ex) {
            System.out.println("File error when creating department: " + ex.getMessage());
        }
    }

    public static void updateDepartment(Department deptInfo) {
        File deptFile = new File(DEPT_FILE);
        if (!deptFile.exists()) return;

        List<String> tempData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(deptFile))) {
            String rowLine;
            while ((rowLine = br.readLine()) != null) {
                String[] row = rowLine.split(",");
                if (row.length > 0 && row[0].trim().equals(deptInfo.getDepartmentId())) {
                    tempData.add(deptInfo.toFileString()); 
                } else {
                    tempData.add(rowLine); 
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading file: " + ex.getMessage());
        }

        rewriteFile(deptFile, tempData);
    }

    public static void saveRoster(ShiftRoster targetRoster) {
        File rosterFile = new File(ROSTER_FILE);
        ensureDirectoryExists(rosterFile);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rosterFile, true))) {
            bw.write(targetRoster.getRosterId() + "," + targetRoster.getWeekStartDate());
            bw.newLine();
        } catch (IOException ex) {
            System.out.println("Could not save roster: " + ex.getMessage());
        }
    }

    public static void updateRoster(ShiftRoster rosterData) {
        File rosterFile = new File(ROSTER_FILE);
        if (!rosterFile.exists()) return;

        List<String> fileContent = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rosterFile))) {
            String lineData;
            while ((lineData = br.readLine()) != null) {
                String[] cols = lineData.split(",");
                if (cols.length > 0 && cols[0].trim().equals(rosterData.getRosterId())) {
                    fileContent.add(rosterData.getRosterId() + "," + rosterData.getWeekStartDate()); 
                } else {
                    fileContent.add(lineData); 
                }
            }
        } catch (IOException e) {
            System.out.println("Read error: " + e.getMessage());
        }

        rewriteFile(rosterFile, fileContent);
    }

    // Helper method to keep code DRY (Don't Repeat Yourself)
    private static void ensureDirectoryExists(File file) {
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs(); 
        }
    }

    // Helper method for rewriting files
    private static void rewriteFile(File file, List<String> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (String text : data) {
                bw.write(text);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error rewriting file: " + ex.getMessage());
        }
    }
}