package model;

import exceptions.InvalidAmountException;
import exceptions.InvalidDateException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a base consultation rate for a department and doctor level.
 * File format (consultation_rates.txt):
 *   rateId,departmentId,doctorLevel,baseAmount,effectiveDate(yyyy-MM-dd)
 */
public class ConsultationRate {

    private String rateId;
    private String departmentId;
    private String doctorLevel;
    private double baseAmount;
    private LocalDate effectiveDate;

    public ConsultationRate(String rateId, String departmentId, String doctorLevel,
                            double baseAmount, LocalDate effectiveDate)
            throws InvalidAmountException {
        setRateId(rateId);
        setDepartmentId(departmentId);
        setDoctorLevel(doctorLevel);
        setBaseAmount(baseAmount);
        setEffectiveDate(effectiveDate);
    }

    // ---------- Getters ----------
    public String getRateId()           { return rateId; }
    public String getDepartmentId()     { return departmentId; }
    public String getDoctorLevel()      { return doctorLevel; }
    public double getBaseAmount()       { return baseAmount; }
    public LocalDate getEffectiveDate() { return effectiveDate; }

    // ---------- Setters (validation lives here = encapsulation) ----------
    public void setRateId(String rateId) {
        if (rateId == null || rateId.trim().isEmpty()) {
            throw new IllegalArgumentException("Rate ID cannot be empty.");
        }
        this.rateId = rateId.trim();
    }

    public void setDepartmentId(String departmentId) {
        if (departmentId == null || departmentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Department ID cannot be empty.");
        }
        if (departmentId.contains(",")) {
            throw new IllegalArgumentException("Department ID cannot contain commas.");
        }
        this.departmentId = departmentId.trim();
    }

    public void setDoctorLevel(String doctorLevel) {
        if (doctorLevel == null || doctorLevel.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor level cannot be empty.");
        }
        this.doctorLevel = doctorLevel.trim();
    }

    public void setBaseAmount(double baseAmount) throws InvalidAmountException {
        if (baseAmount <= 0) {
            throw new InvalidAmountException("Base amount must be greater than 0.");
        }
        this.baseAmount = baseAmount;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        if (effectiveDate == null) {
            throw new IllegalArgumentException("Effective date cannot be empty.");
        }
        this.effectiveDate = effectiveDate;
    }

    // ---------- Helpers ----------
    // Turns "2026-09-30" text into a LocalDate, or throws a readable error
    public static LocalDate parseDate(String text) throws InvalidDateException {
        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Effective date must be in yyyy-MM-dd format (e.g. 2026-09-30).");
        }
    }

    public String toFileString() {
        return String.join(",", rateId, departmentId, doctorLevel,
                String.valueOf(baseAmount), effectiveDate.toString());
    }

    public static ConsultationRate fromFileString(String line)
            throws InvalidAmountException, InvalidDateException {
        String[] p = line.split(",", -1);
        if (p.length < 5) {
            throw new IllegalArgumentException("Malformed consultation rate record: " + line);
        }
        try {
            return new ConsultationRate(p[0], p[1], p[2],
                    Double.parseDouble(p[3]), parseDate(p[4]));
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Base amount must be a valid number.");
        }
    }

    @Override
    public String toString() {
        return departmentId + " / " + doctorLevel + " : " + baseAmount;
    }
}
