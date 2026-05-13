package com.rentalSystem.utils;

import java.util.UUID;

public class IDGenerator {
    public static String generateCustomerId() {
        return "CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateVehicleId() {
        return "VEH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateRentalId() {
        return "RENT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateBookingId() {
        return "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generatePaymentId() {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateTransactionReference() {
        return "TXN-" + System.currentTimeMillis();
    }
}
