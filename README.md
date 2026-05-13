# Vehicle Rental System

A comprehensive online vehicle rental system built with Java. This application manages vehicle inventory, customer registrations, bookings, rentals, and payments.

## Project Structure

```
VehicleRentalSystem/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── rentalSystem/
│                   ├── models/              # Data models
│                   │   ├── Vehicle.java
│                   │   ├── Customer.java
│                   │   ├── Rental.java
│                   │   ├── Payment.java
│                   │   └── Booking.java
│                   ├── services/            # Business logic services
│                   │   ├── VehicleService.java
│                   │   ├── CustomerService.java
│                   │   ├── RentalService.java
│                   │   ├── BookingService.java
│                   │   └── PaymentService.java
│                   ├── managers/            # System manager
│                   │   └── RentalSystemManager.java
│                   ├── utils/               # Utility classes
│                   │   └── IDGenerator.java
│                   └── VehicleRentalApplication.java  # Main entry point
```

## Features

### 1. Vehicle Management
- Add and manage vehicle inventory
- Track vehicle status (Available, Rented, Maintenance, Retired)
- Support for multiple vehicle types (Economy, Compact, Sedan, SUV, Luxury, Van)
- View available vehicles by type
- Track vehicle mileage

### 2. Customer Management
- Register new customers with license verification
- Store customer details (name, email, phone, address)
- Manage customer account balance
- Track customer rentals

### 3. Booking System
- Make vehicle bookings for specific dates
- Check vehicle availability
- Confirm or cancel bookings
- Prevent double-booking of vehicles

### 4. Rental Management
- Convert confirmed bookings into active rentals
- Calculate rental costs based on duration and daily rate
- Apply late fees for overdue returns
- Track rental history

### 5. Payment Processing
- Process payments with multiple methods (Credit Card, Debit Card, Net Banking, Cash, Wallet)
- Complete, refund, or fail payments
- Track payment status and transaction references
- Manage rental costs and additional fees

### 6. System Statistics
- View total vehicles and available count
- Track total customers and rentals
- Monitor bookings and payments
- Calculate total revenue

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Command line or IDE (IntelliJ IDEA, Eclipse, VS Code)

### Compilation

Navigate to the project root directory and compile:

```bash
javac -d bin src/main/java/com/rentalSystem/**/*.java
```

### Running the Application

```bash
java -cp bin com.rentalSystem.VehicleRentalApplication
```

## Usage

The application presents an interactive menu system with the following options:

1. **Vehicle Management** - Add vehicles, view available ones, search for vehicles
2. **Customer Management** - Register customers, view customer details
3. **Booking Management** - Create bookings, confirm bookings, view availability
4. **Rental Management** - Start rentals, complete rentals, view rental details
5. **Payment Management** - Process payments, complete transactions, handle refunds
6. **System Statistics** - View system overview and metrics

## Sample Operations

### Register a Customer
1. Select "Customer Management"
2. Choose "Register New Customer"
3. Enter customer details (name, email, phone, license number, address)

### Make a Booking
1. Select "Booking Management"
2. Choose "Make New Booking"
3. Enter customer ID and vehicle ID
4. Specify pickup and return dates
5. Confirm the booking

### Complete a Rental
1. Select "Rental Management"
2. Choose "Complete Rental"
3. Enter rental ID and return date
4. System calculates total cost including any late fees

### Process Payment
1. Select "Payment Management"
2. Choose "Process Payment"
3. Enter rental ID and amount
4. Select payment method
5. Complete the transaction

## Key Classes

### Models
- **Vehicle** - Represents a rental vehicle with type, status, and pricing
- **Customer** - Represents a customer with license and account information
- **Rental** - Tracks active and completed vehicle rentals
- **Payment** - Handles payment transactions and status
- **Booking** - Manages vehicle booking requests

### Services
- **VehicleService** - Vehicle inventory and status management
- **CustomerService** - Customer registration and account management
- **RentalService** - Rental lifecycle and cost calculation
- **BookingService** - Booking creation and availability checking
- **PaymentService** - Payment processing and tracking

### Manager
- **RentalSystemManager** - Orchestrates all services and business logic

## Features Included

✅ Object-Oriented Design with proper encapsulation
✅ Service-oriented architecture for business logic
✅ UUID-based unique ID generation
✅ Date and time management using Java Time API
✅ Collection management with generics
✅ Exception handling and validation
✅ Interactive command-line interface
✅ System statistics and reporting

## Future Enhancements

- Database integration (MySQL/PostgreSQL)
- REST API with Spring Boot
- Web UI with JSP/JSF
- Email notifications
- Advanced reporting and analytics
- Integration with payment gateways
- Mobile application
- Insurance management
- Maintenance scheduling

## License

This project is open source and available for educational purposes.

## Author

Vehicle Rental System v1.0
