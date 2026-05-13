package com.rentalSystem.services;

import com.rentalSystem.models.Payment;
import java.util.*;

public class PaymentService {
    private List<Payment> payments;

    public PaymentService() {
        this.payments = new ArrayList<>();
    }

    public void processPayment(Payment payment) {
        payments.add(payment);
        System.out.println("Payment processed: " + payment.getPaymentId());
    }

    public Payment getPayment(String paymentId) {
        return payments.stream()
            .filter(p -> p.getPaymentId().equals(paymentId))
            .findFirst()
            .orElse(null);
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments);
    }

    public boolean completePayment(String paymentId, String transactionReference) {
        Payment payment = getPayment(paymentId);
        if (payment != null) {
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
            payment.setTransactionReference(transactionReference);
            System.out.println("Payment completed: " + paymentId);
            return true;
        }
        return false;
    }

    public boolean refundPayment(String paymentId, String reason) {
        Payment payment = getPayment(paymentId);
        if (payment != null && payment.getStatus() == Payment.PaymentStatus.COMPLETED) {
            payment.setStatus(Payment.PaymentStatus.REFUNDED);
            System.out.println("Payment refunded: " + paymentId + " - Reason: " + reason);
            return true;
        }
        return false;
    }

    public boolean failPayment(String paymentId, String reason) {
        Payment payment = getPayment(paymentId);
        if (payment != null) {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            System.out.println("Payment failed: " + paymentId + " - Reason: " + reason);
            return true;
        }
        return false;
    }

    public List<Payment> getPaymentsByRental(String rentalId) {
        return payments.stream()
            .filter(p -> p.getRentalId().equals(rentalId))
            .toList();
    }

    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return payments.stream()
            .filter(p -> p.getStatus() == status)
            .toList();
    }

    public double getTotalPaymentAmount() {
        return payments.stream()
            .filter(p -> p.getStatus() == Payment.PaymentStatus.COMPLETED)
            .mapToDouble(Payment::getAmount)
            .sum();
    }

    public int getTotalPayments() {
        return payments.size();
    }
}
