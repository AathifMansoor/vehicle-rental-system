package com.rentalSystem.services;

import com.rentalSystem.models.Customer;
import java.util.*;

public class CustomerService {
    private List<Customer> customers;

    public CustomerService() {
        this.customers = new ArrayList<>();
    }

    public void registerCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("Customer registered: " + customer.getCustomerId());
    }

    public Customer getCustomer(String customerId) {
        return customers.stream()
            .filter(c -> c.getCustomerId().equals(customerId))
            .findFirst()
            .orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public boolean updateCustomer(String customerId, Customer updatedCustomer) {
        Customer customer = getCustomer(customerId);
        if (customer != null) {
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            customer.setAddress(updatedCustomer.getAddress());
            return true;
        }
        return false;
    }

    public boolean removeCustomer(String customerId) {
        return customers.removeIf(c -> c.getCustomerId().equals(customerId));
    }

    public void addBalance(String customerId, double amount) {
        Customer customer = getCustomer(customerId);
        if (customer != null) {
            customer.setAccountBalance(customer.getAccountBalance() + amount);
            System.out.println("Balance added for customer " + customerId + ": " + amount);
        }
    }

    public void deductBalance(String customerId, double amount) {
        Customer customer = getCustomer(customerId);
        if (customer != null) {
            customer.setAccountBalance(customer.getAccountBalance() - amount);
        }
    }

    public double getCustomerBalance(String customerId) {
        Customer customer = getCustomer(customerId);
        return customer != null ? customer.getAccountBalance() : 0;
    }

    public boolean verifyLicense(String customerId) {
        Customer customer = getCustomer(customerId);
        return customer != null && customer.getLicenseNumber() != null && !customer.getLicenseNumber().isEmpty();
    }

    public int getTotalCustomers() {
        return customers.size();
    }
}
