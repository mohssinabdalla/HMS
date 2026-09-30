package model;

import exceptions.InvalidDateException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Links a doctor to a medical manager.
 * File format (assignments.txt):
 *   assignmentId,doctorId,managerId,assignedDate(yyyy-MM-dd),status
 * A doctor has at most one "Active" assignment; older ones are kept as "Ended" (history).
 */
public class DoctorAssignment {

    public static final String STATUS_ACTIVE = "Active";
    public static final String STATUS_ENDED  = "Ended"; 

    private String assignmentId;
    private String doctorId;
    private String managerId;
    private LocalDate assignedDate;
    private String status;

    public DoctorAssignment(String assignmentId, String doctorId, String managerId,
                            LocalDate assignedDate, String status) {
        setAssignmentId(assignmentId);
        setDoctorId(doctorId);
        setManagerId(managerId);
        setAssignedDate(assignedDate);
        setStatus(status);
    }

    // A brand-new assignment: assigned today, Active
    public DoctorAssignment(String assignmentId, String doctorId, String managerId) {
        this(assignmentId, doctorId, managerId, LocalDate.now(), STATUS_ACTIVE);
    }

    // ---------- Getters ----------
    public String getAssignmentId()   { return assignmentId; }
    public String getDoctorId()       { return doctorId; }
    public String getManagerId()      { return managerId; }
    public LocalDate getAssignedDate(){ return assignedDate; }
    public String getStatus()         { return status; }

    // ---------- Setters (validation lives here = encapsulation) ----------
    public void setAssignmentId(String assignmentId) {
        if (assignmentId == null || assignmentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Assignment ID cannot be empty.");
        }
        this.assignmentId = assignmentId.trim();
    }

    public void setDoctorId(String doctorId) {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor ID cannot be empty.");
        }
        this.doctorId = doctorId.trim();
    }

    public void setManagerId(String managerId) {
        if (managerId == null || managerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Manager ID cannot be empty.");
        }
        this.managerId = managerId.trim();
    }

    public void setAssignedDate(LocalDate assignedDate) {
        if (assignedDate == null) {
            throw new IllegalArgumentException("Assigned date cannot be empty.");
        }
        this.assignedDate = assignedDate;
    }

    public void setStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be empty.");
        }
        String s = status.trim();
        if (!s.equals(STATUS_ACTIVE) && !s.equals(STATUS_ENDED)) {
            throw new IllegalArgumentException("Status must be Active or Ended.");
        }
        this.status = s;
    }

    // ---------- Behaviour ----------
    public boolean isActive() {
        return STATUS_ACTIVE.equals(status);
    }

    public void end() {
        this.status = STATUS_ENDED;
    }

    // ---------- File conversion ----------
    public String toFileString() {
        return String.join(",", assignmentId, doctorId, managerId,
                assignedDate.toString(), status);
    }

    public static DoctorAssignment fromFileString(String line) throws InvalidDateException {
        String[] p = line.split(",", -1);
        if (p.length < 5) {
            throw new IllegalArgumentException("Malformed assignment record: " + line);
        }
        try {
            return new DoctorAssignment(p[0], p[1], p[2], LocalDate.parse(p[3].trim()), p[4]);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Assigned date must be in yyyy-MM-dd format.");
        }
    }

    @Override
    public String toString() {
        return doctorId + " -> " + managerId + " (" + status + ")";
    }
}
