package model;

public class Department {
    
    // core properties for hospital departments
    private String deptId;
    private String name;
    private String desc;

    public Department(String deptId, String name, String desc) {
        this.deptId = deptId;
        this.name = name;
        this.desc = desc;
    }

    // output format for saving into departments.txt
    public String toFileString() {
        return deptId + "," + name + "," + desc;
    }
    
    // --- getters and setters down here ---

    public String getDepartmentId() { 
        return deptId; 
    }
    
    public void setDepartmentId(String deptId) { 
        this.deptId = deptId; 
    }

    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }

    public String getDescription() { 
        return desc; 
    }
    
    public void setDescription(String desc) { 
        this.desc = desc; 
    }
}