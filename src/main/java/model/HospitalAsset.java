package model;

import exceptions.InvalidCapacityException;

/**
 * Abstract parent of every physical hospital asset. File format (assets.txt):
 * assetId,assetType,location,status,capacity
 */
public abstract class HospitalAsset {

    public static final String STATUS_AVAILABLE = "Available";
    public static final String STATUS_OCCUPIED = "Occupied";
    public static final String STATUS_MAINTENANCE = "Maintenance";

    protected String assetId;
    protected String location;
    protected String status;
    protected int capacity;

    protected HospitalAsset(String assetId, String location, String status, int capacity)
            throws InvalidCapacityException {
        setAssetId(assetId);
        setLocation(location);
        setStatus(status);
        setCapacity(capacity);
    }

    // ---------- Abstract: each subclass describes itself ----------
    public abstract String getAssetType();

    public abstract String getDetails();

    // ---------- Getters ----------
    public String getAssetId() {
        return assetId;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

    // ---------- Setters (validation lives here = encapsulation) ----------
    public void setAssetId(String assetId) {
        if (assetId == null || assetId.trim().isEmpty()) {
            throw new IllegalArgumentException("Asset ID cannot be empty.");
        }
        this.assetId = assetId.trim();
    }

    public void setLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
        if (location.contains(",")) {
            throw new IllegalArgumentException("Location cannot contain commas.");
        }
        this.location = location.trim();
    }

    public void setStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be empty.");
        }
        String s = status.trim();
        if (!s.equals(STATUS_AVAILABLE) && !s.equals(STATUS_OCCUPIED) && !s.equals(STATUS_MAINTENANCE)) {
            throw new IllegalArgumentException("Status must be Available, Occupied or Maintenance.");
        }
        this.status = s;
    }

    public void setCapacity(int capacity) throws InvalidCapacityException {
        if (capacity <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than 0.");
        }
        this.capacity = capacity;
    }

    // ---------- Behaviour ----------
    public boolean checkAvailability() {
        return STATUS_AVAILABLE.equals(status);
    }

    public void updateStatus(String newStatus) {
        setStatus(newStatus);
    }

    // ---------- File conversion ----------
    public String toFileString() {
        return String.join(",", assetId, getAssetType(), location, status, String.valueOf(capacity));
    }

    // Factory: picks the right subclass from the type text (same strings as the screen's combo box)
    public static HospitalAsset create(String type, String assetId, String location,
            String status, int capacity)
            throws InvalidCapacityException {
        if (type == null) {
            throw new IllegalArgumentException("Asset type cannot be empty.");
        }
        switch (type.trim()) {
            case "ConsultationRoom":
                return new ConsultationRoom(assetId, location, status, capacity);
            case "Ward":
                return new Ward(assetId, location, status, capacity);
            case "Lab":
                return new Lab(assetId, location, status, capacity);
            case "Imaging Room":
                return new ImagingRoom(assetId, location, status, capacity);
            default:
                throw new IllegalArgumentException("Unknown asset type: " + type);
        }
    }

    public static HospitalAsset fromFileString(String line) throws InvalidCapacityException {
        String[] p = line.split(",", -1);
        if (p.length < 5) {
            throw new IllegalArgumentException("Malformed asset record: " + line);
        }
        try {
            return create(p[1], p[0], p[2], p[3], Integer.parseInt(p[4].trim()));
        } catch (NumberFormatException e) {
            throw new InvalidCapacityException("Capacity must be a whole number.");
        }
    }

    @Override
    public String toString() {
        return getDetails();
    }
}
