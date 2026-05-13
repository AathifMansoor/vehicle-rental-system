package com.rentalSystem.models;

public class Vehicle {
    private String vehicleId;
    private String brand;
    private String model;
    private int year;
    private double dailyRentalRate;
    private VehicleType type;
    private VehicleStatus status;
    private String licensePlate;
    private String color;
    private double mileage;

    public enum VehicleType {
        ECONOMY, COMPACT, SEDAN, SUV, LUXURY, VAN
    }

    public enum VehicleStatus {
        AVAILABLE, RENTED, MAINTENANCE, RETIRED
    }

    public Vehicle(String vehicleId, String brand, String model, int year, 
                   double dailyRentalRate, VehicleType type, String licensePlate, String color) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.dailyRentalRate = dailyRentalRate;
        this.type = type;
        this.status = VehicleStatus.AVAILABLE;
        this.licensePlate = licensePlate;
        this.color = color;
        this.mileage = 0;
    }

    // Getters and Setters
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getDailyRentalRate() { return dailyRentalRate; }
    public void setDailyRentalRate(double dailyRentalRate) { this.dailyRentalRate = dailyRentalRate; }

    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }

    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getMileage() { return mileage; }
    public void setMileage(double mileage) { this.mileage = mileage; }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", dailyRentalRate=" + dailyRentalRate +
                ", type=" + type +
                ", status=" + status +
                ", licensePlate='" + licensePlate + '\'' +
                '}';
    }
}
