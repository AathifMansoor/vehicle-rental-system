package com.rentalSystem;

import com.rentalSystem.managers.RentalSystemManager;
import com.rentalSystem.models.*;
import java.time.LocalDate;
import java.util.Scanner;

public class VehicleRentalApplication {
    private RentalSystemManager manager;
    private Scanner scanner;

    public VehicleRentalApplication() {
        this.manager = new RentalSystemManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        initializeSystem();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    vehicleMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    bookingMenu();
                    break;
                case 4:
                    rentalMenu();
                    break;
                case 5:
                    paymentMenu();
                    break;
                case 6:
                    manager.displaySystemStatistics();
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using Vehicle Rental System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private void initializeSystem() {
        System.out.println("Initializing Vehicle Rental System...\n");
        
        // Add sample vehicles
        manager.addVehicle("Toyota", "Camry", 2023, 50.0, Vehicle.VehicleType.SEDAN, "AB-123", "Silver");
        manager.addVehicle("Honda", "CR-V", 2023, 70.0, Vehicle.VehicleType.SUV, "CD-456", "Black");
        manager.addVehicle("BMW", "X5", 2024, 150.0, Vehicle.VehicleType.LUXURY, "EF-789", "White");
        manager.addVehicle("Tesla", "Model 3", 2024, 80.0, Vehicle.VehicleType.SEDAN, "GH-012", "Blue");
        manager.addVehicle("Ford", "Transit", 2022, 90.0, Vehicle.VehicleType.VAN, "IJ-345", "Red");
        
        System.out.println("System initialized with sample vehicles!\n");
    }

    private void displayMenu() {
        System.out.println("\n========== VEHICLE RENTAL SYSTEM MENU ==========");
        System.out.println("1. Vehicle Management");
        System.out.println("2. Customer Management");
        System.out.println("3. Booking Management");
        System.out.println("4. Rental Management");
        System.out.println("5. Payment Management");
        System.out.println("6. View System Statistics");
        System.out.println("7. Exit");
        System.out.println("===============================================");
    }

    private void vehicleMenu() {
        System.out.println("\n--- Vehicle Management ---");
        System.out.println("1. View Available Vehicles");
        System.out.println("2. Add New Vehicle");
        System.out.println("3. Search Vehicle");
        System.out.println("4. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                manager.displayAvailableVehicles();
                break;
            case 2:
                addNewVehicle();
                break;
            case 3:
                searchVehicle();
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void addNewVehicle() {
        System.out.println("\n--- Add New Vehicle ---");
        String brand = getStringInput("Enter brand: ");
        String model = getStringInput("Enter model: ");
        int year = getIntInput("Enter year: ");
        double dailyRate = getDoubleInput("Enter daily rental rate: ");
        String licensePlate = getStringInput("Enter license plate: ");
        String color = getStringInput("Enter color: ");
        
        System.out.println("Select vehicle type:");
        System.out.println("1. ECONOMY  2. COMPACT  3. SEDAN  4. SUV  5. LUXURY  6. VAN");
        int typeChoice = getIntInput("Enter choice: ");
        
        Vehicle.VehicleType[] types = Vehicle.VehicleType.values();
        if (typeChoice > 0 && typeChoice <= types.length) {
            manager.addVehicle(brand, model, year, dailyRate, types[typeChoice - 1], licensePlate, color);
            System.out.println("Vehicle added successfully!");
        }
    }

    private void searchVehicle() {
        String vehicleId = getStringInput("Enter vehicle ID: ");
        Vehicle vehicle = manager.searchVehicle(vehicleId);
        if (vehicle != null) {
            System.out.println(vehicle);
        } else {
            System.out.println("Vehicle not found!");
        }
    }

    private void customerMenu() {
        System.out.println("\n--- Customer Management ---");
        System.out.println("1. Register New Customer");
        System.out.println("2. View Customer Details");
        System.out.println("3. View All Customers");
        System.out.println("4. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                registerCustomer();
                break;
            case 2:
                viewCustomerDetails();
                break;
            case 3:
                manager.displayCustomers();
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void registerCustomer() {
        System.out.println("\n--- Register New Customer ---");
        String firstName = getStringInput("Enter first name: ");
        String lastName = getStringInput("Enter last name: ");
        String email = getStringInput("Enter email: ");
        String phone = getStringInput("Enter phone number: ");
        String licenseNumber = getStringInput("Enter license number: ");
        String address = getStringInput("Enter address: ");
        
        manager.registerCustomer(firstName, lastName, email, phone, licenseNumber, address);
        System.out.println("Customer registered successfully!");
    }

    private void viewCustomerDetails() {
        String customerId = getStringInput("Enter customer ID: ");
        Customer customer = manager.getCustomer(customerId);
        if (customer != null) {
            System.out.println(customer);
            System.out.println("Balance: $" + customer.getAccountBalance());
        } else {
            System.out.println("Customer not found!");
        }
    }

    private void bookingMenu() {
        System.out.println("\n--- Booking Management ---");
        System.out.println("1. Make New Booking");
        System.out.println("2. View Available Vehicles");
        System.out.println("3. View Pending Bookings");
        System.out.println("4. Confirm Booking");
        System.out.println("5. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                makeNewBooking();
                break;
            case 2:
                manager.displayAvailableVehicles();
                break;
            case 3:
                viewPendingBookings();
                break;
            case 4:
                confirmBooking();
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void makeNewBooking() {
        System.out.println("\n--- Make New Booking ---");
        String customerId = getStringInput("Enter customer ID: ");
        String vehicleId = getStringInput("Enter vehicle ID: ");
        
        System.out.println("Enter pickup date (YYYY-MM-DD): ");
        LocalDate pickupDate = LocalDate.parse(getStringInput(""));
        
        System.out.println("Enter return date (YYYY-MM-DD): ");
        LocalDate returnDate = LocalDate.parse(getStringInput(""));
        
        if (manager.makeBooking(customerId, vehicleId, pickupDate, returnDate)) {
            System.out.println("Booking created successfully!");
        } else {
            System.out.println("Failed to create booking!");
        }
    }

    private void viewPendingBookings() {
        System.out.println("\nPending Bookings:");
        manager.getPendingBookings().forEach(System.out::println);
    }

    private void confirmBooking() {
        String bookingId = getStringInput("Enter booking ID to confirm: ");
        if (manager.confirmBooking(bookingId)) {
            System.out.println("Booking confirmed successfully!");
        } else {
            System.out.println("Failed to confirm booking!");
        }
    }

    private void rentalMenu() {
        System.out.println("\n--- Rental Management ---");
        System.out.println("1. Start Rental");
        System.out.println("2. Complete Rental");
        System.out.println("3. View Rental Details");
        System.out.println("4. View All Rentals");
        System.out.println("5. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                startRental();
                break;
            case 2:
                completeRental();
                break;
            case 3:
                viewRentalDetails();
                break;
            case 4:
                manager.displayRentals();
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void startRental() {
        String bookingId = getStringInput("Enter booking ID: ");
        if (manager.startRental(bookingId)) {
            System.out.println("Rental started successfully!");
        } else {
            System.out.println("Failed to start rental!");
        }
    }

    private void completeRental() {
        String rentalId = getStringInput("Enter rental ID: ");
        System.out.println("Enter return date (YYYY-MM-DD): ");
        LocalDate returnDate = LocalDate.parse(getStringInput(""));
        
        if (manager.completeRental(rentalId, returnDate)) {
            double cost = manager.getRentalCost(rentalId);
            System.out.println("Rental completed! Total cost: $" + String.format("%.2f", cost));
        } else {
            System.out.println("Failed to complete rental!");
        }
    }

    private void viewRentalDetails() {
        String rentalId = getStringInput("Enter rental ID: ");
        // Implementation for viewing rental details
        System.out.println("Rental details feature coming soon!");
    }

    private void paymentMenu() {
        System.out.println("\n--- Payment Management ---");
        System.out.println("1. Process Payment");
        System.out.println("2. Complete Payment");
        System.out.println("3. Refund Payment");
        System.out.println("4. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                processPayment();
                break;
            case 2:
                completePayment();
                break;
            case 3:
                refundPayment();
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void processPayment() {
        System.out.println("\n--- Process Payment ---");
        String rentalId = getStringInput("Enter rental ID: ");
        double amount = getDoubleInput("Enter payment amount: ");
        
        System.out.println("Select payment method:");
        System.out.println("1. CREDIT_CARD  2. DEBIT_CARD  3. NET_BANKING  4. CASH  5. WALLET");
        int methodChoice = getIntInput("Enter choice: ");
        
        Payment.PaymentMethod[] methods = Payment.PaymentMethod.values();
        if (methodChoice > 0 && methodChoice <= methods.length) {
            manager.processPayment(rentalId, amount, methods[methodChoice - 1]);
            System.out.println("Payment processed successfully!");
        }
    }

    private void completePayment() {
        String paymentId = getStringInput("Enter payment ID: ");
        if (manager.completePayment(paymentId)) {
            System.out.println("Payment completed successfully!");
        } else {
            System.out.println("Failed to complete payment!");
        }
    }

    private void refundPayment() {
        String paymentId = getStringInput("Enter payment ID: ");
        if (manager.refundPayment(paymentId)) {
            System.out.println("Payment refunded successfully!");
        } else {
            System.out.println("Failed to refund payment!");
        }
    }

    // Helper methods
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.nextLine();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.nextLine();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        VehicleRentalApplication app = new VehicleRentalApplication();
        app.start();
    }
}
