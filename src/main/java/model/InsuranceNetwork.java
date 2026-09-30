package model;

import exceptions.InvalidCoverageException;

/**
 * Represents an insurance network accepted by the hospital.
 * File format (insurance_networks.txt):
 *   networkId,providerName,coveragePercent,status
 */
public class InsuranceNetwork {

    private String networkId;
    private String providerName;
    private double coveragePercent;
    private String status;

    public InsuranceNetwork(String networkId, String providerName,
                            double coveragePercent, String status)
            throws InvalidCoverageException {
        setNetworkId(networkId);
        setProviderName(providerName);
        setCoveragePercent(coveragePercent);
        setStatus(status);
    }

    
    public String getNetworkId()       { return networkId; }
    public String getProviderName()    { return providerName; }
    public double getCoveragePercent() { return coveragePercent; }
    public String getStatus()          { return status; }

    public void setNetworkId(String networkId) {
        if (networkId == null || networkId.trim().isEmpty()) {
            throw new IllegalArgumentException("Network ID cannot be empty.");
        }
        this.networkId = networkId.trim();
    }

    public void setProviderName(String providerName) {
        if (providerName == null || providerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Provider name cannot be empty.");
        }
        if (providerName.contains(",")) {
            throw new IllegalArgumentException("Provider name cannot contain commas.");
        }
        this.providerName = providerName.trim();
    }

    public void setCoveragePercent(double coveragePercent) throws InvalidCoverageException {
        if (coveragePercent < 0 || coveragePercent > 100) {
            throw new InvalidCoverageException("Coverage % must be between 0 and 100.");
        }
        this.coveragePercent = coveragePercent;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty.");
        }
        this.status = status.trim();
    }

    public String toFileString() {
        return String.join(",", networkId, providerName,
                String.valueOf(coveragePercent), status);
    }

    public static InsuranceNetwork fromFileString(String line) throws InvalidCoverageException {
        String[] p = line.split(",", -1);
        if (p.length < 4) {
            throw new IllegalArgumentException("Malformed insurance network record: " + line);
        }
        try {
            return new InsuranceNetwork(p[0], p[1], Double.parseDouble(p[2]), p[3]);
        } catch (NumberFormatException e) {
            throw new InvalidCoverageException("Coverage % must be a valid number.");
        }
    }

    @Override
    public String toString() {
        return providerName + " (" + coveragePercent + "%, " + status + ")";
    }
}
