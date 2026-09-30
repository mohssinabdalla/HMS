package model;

import exceptions.InvalidCapacityException;

public class ImagingRoom extends HospitalAsset {

    public ImagingRoom(String assetId, String location, String status, int capacity)
            throws InvalidCapacityException {
        super(assetId, location, status, capacity);
    }

    // Must match the text in the screen's asset type combo box ("Imaging Room" with a space)
    @Override
    public String getAssetType() {
        return "Imaging Room";
    }

    @Override
    public String getDetails() {
        return "Imaging Room - " + location + " | Capacity: " + capacity + " | " + status;
    }
}
