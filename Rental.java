package com.rentalSystem.models;

import java.time.LocalDate;

public class Rental {
    private String rentalId;
    private String customerId;
    private String vehicleId;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private RentalStatus status;
    private double totalCost;
    private LocalDate actualReturnDate;
    private double lateFee;

    public enum RentalStatus {
        ACTIVE, COMPLETED, CANCELLED
    }

    public Rental(String rentalId, String customerId, String vehicleId, 
                  LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.status = RentalStatus.ACTIVE;
        this.totalCost = 0;
        this.lateFee = 0;
    }

    // Getters and Setters
    public String getRentalId() { return rentalId; }
    public void setRentalId(String rentalId) { this.rentalId = rentalId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public LocalDate getRentalStartDate() { return rentalStartDate; }
    public void setRentalStartDate(LocalDate rentalStartDate) { this.rentalStartDate = rentalStartDate; }

    public LocalDate getRentalEndDate() { return rentalEndDate; }
    public void setRentalEndDate(LocalDate rentalEndDate) { this.rentalEndDate = rentalEndDate; }

    public RentalStatus getStatus() { return status; }
    public void setStatus(RentalStatus status) { this.status = status; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public LocalDate getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }

    public double getLateFee() { return lateFee; }
    public void setLateFee(double lateFee) { this.lateFee = lateFee; }

    public long getRentalDays() {
        return java.time.temporal.ChronoUnit.DAYS.between(rentalStartDate, rentalEndDate);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "rentalId='" + rentalId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", status=" + status +
                ", totalCost=" + totalCost +
                '}';
    }
}
