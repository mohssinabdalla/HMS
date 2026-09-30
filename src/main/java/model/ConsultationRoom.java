package model;

import exceptions.InvalidCapacityException;

public class ConsultationRoom extends HospitalAsset {

    public ConsultationRoom(String assetId, String location, String status, int capacity)
            throws InvalidCapacityException {
        super(assetId, location, status, capacity);
    }

    @Override
    public String getAssetType() {
        return "ConsultationRoom";
    }

    @Override
    public String getDetails() {
        return "Consultation Room - " + location + " | Capacity: " + capacity + " | " + status;
    }
}
