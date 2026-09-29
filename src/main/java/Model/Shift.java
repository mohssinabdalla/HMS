package model;

public class Shift {
    private String shiftId;
    private String startTime;
    private String endTime;
    private String shiftType;

    public Shift(String shiftId, String startTime, String endTime, String shiftType) {
        this.shiftId = shiftId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
    }

    // Getters and Setters
    public String getShiftId() { 
        return shiftId; 
    }
    
    public void setShiftId(String shiftId) { 
        this.shiftId = shiftId; 
    }

    public String getStartTime() { 
        return startTime; 
    }
    
    public void setStartTime(String startTime) { 
        this.startTime = startTime; 
    }

    public String getEndTime() { 
        return endTime; 
    }
    
    public void setEndTime(String endTime) { 
        this.endTime = endTime; 
    }

    public String getShiftType() { 
        return shiftType; 
    }
    
    public void setShiftType(String shiftType) { 
        this.shiftType = shiftType; 
    }

    
    public String toFileString() {
        return shiftId + "," + startTime + "," + endTime + "," + shiftType;
    }
}
