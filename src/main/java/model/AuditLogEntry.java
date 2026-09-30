/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hp
 */
public class AuditLogEntry {

    private final String logId;
    private final String userId;
    private final String actionType;
    private final String targetEntity;
    private final String timestamp;
    private final String details;

    public AuditLogEntry(String logId, String userId, String actionType,
            String targetEntity, String timestamp, String details) {
        this.logId = logId;
        this.userId = userId;
        this.actionType = actionType;
        this.targetEntity = targetEntity;
        this.timestamp = timestamp;
        this.details = details;
    }

    public String getLogId() {
        return logId;
    }

    public String getUserId() {
        return userId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    /**
     * Parses one raw line into an entry. Limit 6 on split so a comma inside
     * "details" (the free-text field) doesn't get cut into extra columns.
     */
    public static AuditLogEntry fromFileString(String line) {
        String[] p = line.split(",", 6);
        if (p.length < 5) {
            throw new IllegalArgumentException("Malformed log line: " + line);
        }
        String details = p.length > 5 ? p[5] : "";
        return new AuditLogEntry(p[0], p[1], p[2], p[3], p[4], details);
    }
}
