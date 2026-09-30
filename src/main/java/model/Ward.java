package model;

import exceptions.InvalidCapacityException;

public class Ward extends HospitalAsset {

    public Ward(String assetId, String location, String status, int capacity)
            throws InvalidCapacityException {
        super(assetId, location, status, capacity);
    }

    @Override
    public String getAssetType() {
        return "Ward";
    }

    // For a ward, capacity means number of beds
    @Override
    public String getDetails() {
        return "Ward - " + location + " | Beds: " + capacity + " | " + status;
    }
}
