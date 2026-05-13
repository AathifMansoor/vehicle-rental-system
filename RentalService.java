package com.rentalSystem.services;

import com.rentalSystem.models.Rental;
import java.time.LocalDate;
import java.util.*;

public class RentalService {
    private List<Rental> rentals;
    private static final double LATE_FEE_PER_DAY = 50.0;

    public RentalService() {
        this.rentals = new ArrayList<>();
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
        System.out.println("Rental created: " + rental.getRentalId());
    }

    public Rental getRental(String rentalId) {
        return rentals.stream()
            .filter(r -> r.getRentalId().equals(rentalId))
            .findFirst()
            .orElse(null);
    }

    public List<Rental> getAllRentals() {
        return new ArrayList<>(rentals);
    }

    public List<Rental> getActiveRentals() {
        return rentals.stream()
            .filter(r -> r.getStatus() == Rental.RentalStatus.ACTIVE)
            .toList();
    }

    public List<Rental> getRentalsByCustomer(String customerId) {
        return rentals.stream()
            .filter(r -> r.getCustomerId().equals(customerId))
            .toList();
    }

    public void completeRental(String rentalId, LocalDate returnDate) {
        Rental rental = getRental(rentalId);
        if (rental != null) {
            rental.setActualReturnDate(returnDate);
            
            // Calculate late fee if applicable
            if (returnDate.isAfter(rental.getRentalEndDate())) {
                long lateDays = java.time.temporal.ChronoUnit.DAYS.between(
                    rental.getRentalEndDate(), returnDate);
                rental.setLateFee(lateDays * LATE_FEE_PER_DAY);
            }
            
            rental.setStatus(Rental.RentalStatus.COMPLETED);
            System.out.println("Rental completed: " + rentalId);
        }
    }

    public void cancelRental(String rentalId) {
        Rental rental = getRental(rentalId);
        if (rental != null) {
            rental.setStatus(Rental.RentalStatus.CANCELLED);
            System.out.println("Rental cancelled: " + rentalId);
        }
    }

    public double calculateRentalCost(String rentalId, double dailyRate) {
        Rental rental = getRental(rentalId);
        if (rental != null) {
            long days = rental.getRentalDays();
            double cost = days * dailyRate;
            rental.setTotalCost(cost + rental.getLateFee());
            return rental.getTotalCost();
        }
        return 0;
    }

    public List<Rental> getRentalsByVehicle(String vehicleId) {
        return rentals.stream()
            .filter(r -> r.getVehicleId().equals(vehicleId) && r.getStatus() == Rental.RentalStatus.COMPLETED)
            .toList();
    }

    public int getTotalRentals() {
        return rentals.size();
    }

    public double getRevenue() {
        return rentals.stream()
            .filter(r -> r.getStatus() == Rental.RentalStatus.COMPLETED)
            .mapToDouble(Rental::getTotalCost)
            .sum();
    }
}
