package model;

import exceptions.InvalidCapacityException;

public class Lab extends HospitalAsset {

    public Lab(String assetId, String location, String status, int capacity)
            throws InvalidCapacityException {
        super(assetId, location, status, capacity);
    }

    @Override
    public String getAssetType() {
        return "Lab";
    }

    // For a lab, capacity means number of workstations
    @Override
    public String getDetails() {
        return "Lab - " + location + " | Workstations: " + capacity + " | " + status;
    }
}
