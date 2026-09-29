package model;

import java.util.HashMap;
import java.util.Map;

public class ShiftRoster {
    
    private String rosterId;
    private String startDate; 
    
    // maps a doctor to their assigned shift
    private Map<Doctor, Shift> shiftMap;

    public ShiftRoster(String rosterId, String weekStartDate) {
        this.rosterId = rosterId;
        this.startDate = weekStartDate;
        this.shiftMap = new HashMap<>();
    }

    // links a doctor to a shift in map
    public void assignDoctor(Doctor doc, Shift shift) {
        if (doc != null && shift != null) {
            shiftMap.put(doc, shift);
            
        }
    }

    // --- getters and setters down here ---

    public String getRosterId() { 
        return rosterId; 
    }
    
    public void setRosterId(String rosterId) { 
        this.rosterId = rosterId; 
    }

    public String getWeekStartDate() { 
        return startDate; 
    }
    
    public void setWeekStartDate(String weekStartDate) { 
        this.startDate = weekStartDate; 
    }

    public Map<Doctor, Shift> getSchedule() { 
        return shiftMap; 
    }
}