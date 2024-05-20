package com.example.gym;

import java.time.LocalDate;

public class Payment {
    private int paymentID;
    private String customerID;
    private int amount;
    private LocalDate paymentDate;

    public Payment(int paymentID, String customerID, int amount, LocalDate paymentDate) {
        this.paymentID = paymentID;
        this.customerID = customerID;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public Payment(String customerID, int amount, LocalDate paymentDate) {
        this.customerID = customerID;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public int getPaymentID() { return paymentID; }
    public void setPaymentID(int paymentID) { this.paymentID = paymentID; }

    public String getCustomerID() { return customerID; }
    public void setCustomerID(String customerID) { this.customerID = customerID; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
}
