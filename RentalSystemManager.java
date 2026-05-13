package com.rentalSystem.managers;

import com.rentalSystem.models.*;
import com.rentalSystem.services.*;
import com.rentalSystem.utils.IDGenerator;
import java.time.LocalDate;
import java.util.List;

public class RentalSystemManager {
    private VehicleService vehicleService;
    private CustomerService customerService;
    private RentalService rentalService;
    private BookingService bookingService;
    private PaymentService paymentService;

    public RentalSystemManager() {
        this.vehicleService = new VehicleService();
        this.customerService = new CustomerService();
        this.rentalService = new RentalService();
        this.bookingService = new BookingService();
        this.paymentService = new PaymentService();
    }

    // =============== VEHICLE MANAGEMENT ===============
    public void addVehicle(String brand, String model, int year, double dailyRate,
                          Vehicle.VehicleType type, String licensePlate, String color) {
        String vehicleId = IDGenerator.generateVehicleId();
        Vehicle vehicle = new Vehicle(vehicleId, brand, model, year, dailyRate, type, licensePlate, color);
        vehicleService.addVehicle(vehicle);
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicleService.getAvailableVehicles();
    }

    public List<Vehicle> getVehiclesByType(Vehicle.VehicleType type) {
        return vehicleService.getVehiclesByType(type);
    }

    public Vehicle searchVehicle(String vehicleId) {
        return vehicleService.getVehicle(vehicleId);
    }

    // =============== CUSTOMER MANAGEMENT ===============
    public void registerCustomer(String firstName, String lastName, String email,
                                String phoneNumber, String licenseNumber, String address) {
        String customerId = IDGenerator.generateCustomerId();
        Customer customer = new Customer(customerId, firstName, lastName, email, phoneNumber, licenseNumber, address);
        customerService.registerCustomer(customer);
    }

    public Customer getCustomer(String customerId) {
        return customerService.getCustomer(customerId);
    }

    public boolean verifyCustomerLicense(String customerId) {
        return customerService.verifyLicense(customerId);
    }

    public void addCustomerBalance(String customerId, double amount) {
        customerService.addBalance(customerId, amount);
    }

    // =============== BOOKING MANAGEMENT ===============
    public boolean makeBooking(String customerId, String vehicleId, LocalDate pickupDate, LocalDate returnDate) {
        // Verify customer
        if (customerService.getCustomer(customerId) == null) {
            System.out.println("Customer not found!");
            return false;
        }

        // Verify vehicle
        if (vehicleService.getVehicle(vehicleId) == null) {
            System.out.println("Vehicle not found!");
            return false;
        }

        // Check availability
        if (!bookingService.isVehicleAvailable(vehicleId, pickupDate, returnDate)) {
            System.out.println("Vehicle not available for selected dates!");
            return false;
        }

        String bookingId = IDGenerator.generateBookingId();
        Booking booking = new Booking(bookingId, customerId, vehicleId, pickupDate, returnDate);
        bookingService.addBooking(booking);
        return true;
    }

    public boolean confirmBooking(String bookingId) {
        return bookingService.confirmBooking(bookingId);
    }

    public List<Booking> getPendingBookings() {
        return bookingService.getPendingBookings();
    }

    // =============== RENTAL MANAGEMENT ===============
    public boolean startRental(String bookingId) {
        Booking booking = bookingService.getBooking(bookingId);
        if (booking == null || booking.getStatus() != Booking.BookingStatus.CONFIRMED) {
            System.out.println("Booking not found or not confirmed!");
            return false;
        }

        // Update vehicle status
        vehicleService.updateVehicleStatus(booking.getVehicleId(), Vehicle.VehicleStatus.RENTED);

        // Create rental
        String rentalId = IDGenerator.generateRentalId();
        Rental rental = new Rental(rentalId, booking.getCustomerId(), booking.getVehicleId(),
                                   booking.getPickupDate(), booking.getReturnDate());
        rentalService.addRental(rental);

        // Update booking
        bookingService.completeBooking(bookingId);
        return true;
    }

    public boolean completeRental(String rentalId, LocalDate returnDate) {
        Rental rental = rentalService.getRental(rentalId);
        if (rental == null) {
            System.out.println("Rental not found!");
            return false;
        }

        // Update rental
        rentalService.completeRental(rentalId, returnDate);

        // Update vehicle status
        vehicleService.updateVehicleStatus(rental.getVehicleId(), Vehicle.VehicleStatus.AVAILABLE);

        // Calculate cost
        Vehicle vehicle = vehicleService.getVehicle(rental.getVehicleId());
        if (vehicle != null) {
            rentalService.calculateRentalCost(rentalId, vehicle.getDailyRentalRate());
        }

        return true;
    }

    public double getRentalCost(String rentalId) {
        Rental rental = rentalService.getRental(rentalId);
        return rental != null ? rental.getTotalCost() : 0;
    }

    public List<Rental> getCustomerRentals(String customerId) {
        return rentalService.getRentalsByCustomer(customerId);
    }

    // =============== PAYMENT MANAGEMENT ===============
    public void processPayment(String rentalId, double amount, Payment.PaymentMethod method) {
        String paymentId = IDGenerator.generatePaymentId();
        Payment payment = new Payment(paymentId, rentalId, amount, method);
        paymentService.processPayment(payment);
    }

    public boolean completePayment(String paymentId) {
        String transactionRef = IDGenerator.generateTransactionReference();
        return paymentService.completePayment(paymentId, transactionRef);
    }

    public boolean refundPayment(String paymentId) {
        return paymentService.refundPayment(paymentId, "Customer Request");
    }

    public List<Payment> getRentalPayments(String rentalId) {
        return paymentService.getPaymentsByRental(rentalId);
    }

    // =============== SYSTEM STATISTICS ===============
    public void displaySystemStatistics() {
        System.out.println("\n========== RENTAL SYSTEM STATISTICS ==========");
        System.out.println("Total Vehicles: " + vehicleService.getTotalVehicles());
        System.out.println("Available Vehicles: " + vehicleService.getAvailableVehicleCount());
        System.out.println("Total Customers: " + customerService.getTotalCustomers());
        System.out.println("Total Rentals: " + rentalService.getTotalRentals());
        System.out.println("Total Bookings: " + bookingService.getTotalBookings());
        System.out.println("Total Payments: " + paymentService.getTotalPayments());
        System.out.println("Total Revenue: $" + String.format("%.2f", rentalService.getRevenue()));
        System.out.println("============================================\n");
    }

    public void displayAvailableVehicles() {
        List<Vehicle> vehicles = getAvailableVehicles();
        System.out.println("\n========== AVAILABLE VEHICLES ==========");
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available!");
        } else {
            vehicles.forEach(v -> System.out.println(v));
        }
        System.out.println("======================================\n");
    }

    public void displayCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        System.out.println("\n========== REGISTERED CUSTOMERS ==========");
        if (customers.isEmpty()) {
            System.out.println("No customers registered!");
        } else {
            customers.forEach(c -> System.out.println(c));
        }
        System.out.println("=========================================\n");
    }

    public void displayRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        System.out.println("\n========== ALL RENTALS ==========");
        if (rentals.isEmpty()) {
            System.out.println("No rentals found!");
        } else {
            rentals.forEach(r -> System.out.println(r));
        }
        System.out.println("================================\n");
    }
}
