package com.rentalSystem.services;

import com.rentalSystem.models.Booking;
import java.time.LocalDate;
import java.util.*;

public class BookingService {
    private List<Booking> bookings;

    public BookingService() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        System.out.println("Booking created: " + booking.getBookingId());
    }

    public Booking getBooking(String bookingId) {
        return bookings.stream()
            .filter(b -> b.getBookingId().equals(bookingId))
            .findFirst()
            .orElse(null);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    public List<Booking> getPendingBookings() {
        return bookings.stream()
            .filter(b -> b.getStatus() == Booking.BookingStatus.PENDING)
            .toList();
    }

    public List<Booking> getConfirmedBookings() {
        return bookings.stream()
            .filter(b -> b.getStatus() == Booking.BookingStatus.CONFIRMED)
            .toList();
    }

    public boolean confirmBooking(String bookingId) {
        Booking booking = getBooking(bookingId);
        if (booking != null && booking.getStatus() == Booking.BookingStatus.PENDING) {
            booking.setStatus(Booking.BookingStatus.CONFIRMED);
            System.out.println("Booking confirmed: " + bookingId);
            return true;
        }
        return false;
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = getBooking(bookingId);
        if (booking != null && booking.getStatus() != Booking.BookingStatus.COMPLETED) {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
            System.out.println("Booking cancelled: " + bookingId);
            return true;
        }
        return false;
    }

    public boolean completeBooking(String bookingId) {
        Booking booking = getBooking(bookingId);
        if (booking != null) {
            booking.setStatus(Booking.BookingStatus.COMPLETED);
            System.out.println("Booking completed: " + bookingId);
            return true;
        }
        return false;
    }

    public List<Booking> getBookingsByCustomer(String customerId) {
        return bookings.stream()
            .filter(b -> b.getCustomerId().equals(customerId))
            .toList();
    }

    public List<Booking> getBookingsByVehicle(String vehicleId) {
        return bookings.stream()
            .filter(b -> b.getVehicleId().equals(vehicleId) && b.getStatus() != Booking.BookingStatus.CANCELLED)
            .toList();
    }

    public boolean isVehicleAvailable(String vehicleId, LocalDate pickupDate, LocalDate returnDate) {
        return bookings.stream()
            .filter(b -> b.getVehicleId().equals(vehicleId) && b.getStatus() != Booking.BookingStatus.CANCELLED)
            .noneMatch(b -> dateRangesOverlap(pickupDate, returnDate, b.getPickupDate(), b.getReturnDate()));
    }

    private boolean dateRangesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !end1.isBefore(start2) && !end2.isBefore(start1);
    }

    public int getTotalBookings() {
        return bookings.size();
    }
}
