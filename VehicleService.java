package com.rentalSystem.services;

import com.rentalSystem.models.Vehicle;
import java.util.*;

public class VehicleService {
    private List<Vehicle> vehicles;

    public VehicleService() {
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.println("Vehicle added: " + vehicle.getVehicleId());
    }

    public Vehicle getVehicle(String vehicleId) {
        return vehicles.stream()
            .filter(v -> v.getVehicleId().equals(vehicleId))
            .findFirst()
            .orElse(null);
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicles.stream()
            .filter(v -> v.getStatus() == Vehicle.VehicleStatus.AVAILABLE)
            .toList();
    }

    public List<Vehicle> getVehiclesByType(Vehicle.VehicleType type) {
        return vehicles.stream()
            .filter(v -> v.getType() == type && v.getStatus() == Vehicle.VehicleStatus.AVAILABLE)
            .toList();
    }

    public boolean updateVehicleStatus(String vehicleId, Vehicle.VehicleStatus status) {
        Vehicle vehicle = getVehicle(vehicleId);
        if (vehicle != null) {
            vehicle.setStatus(status);
            return true;
        }
        return false;
    }

    public boolean removeVehicle(String vehicleId) {
        return vehicles.removeIf(v -> v.getVehicleId().equals(vehicleId));
    }

    public void updateVehicleMileage(String vehicleId, double mileage) {
        Vehicle vehicle = getVehicle(vehicleId);
        if (vehicle != null) {
            vehicle.setMileage(mileage);
        }
    }

    public int getTotalVehicles() {
        return vehicles.size();
    }

    public int getAvailableVehicleCount() {
        return (int) vehicles.stream()
            .filter(v -> v.getStatus() == Vehicle.VehicleStatus.AVAILABLE)
            .count();
    }
}
