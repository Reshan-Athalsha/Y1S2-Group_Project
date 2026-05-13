package com.wedding.weddingplanner.model;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 * 1. COMPOSITION / AGGREGATION: A Payment object structurally 
 *    contains a 'bookingId' reference. This demonstrates how objects
 *    logically relate to one another (a Payment "belongs to" a Booking).
 * 2. ENCAPSULATION: Private attributes managed via public accessor
 *    and mutator methods.
 * ============================================================
 */
public class Payment {
    
    private String paymentId;
    
    // COMPOSITION: Logically links this payment to a specific Booking
    private String bookingId;
    
    private double amount;
    private String paymentMethod; // e.g., CREDIT_CARD, PAYPAL
    private String status;        // e.g., COMPLETED, FAILED

    /** Default constructor */
    public Payment() {}

    /** Parameterized constructor */
    public Payment(String paymentId, String bookingId, double amount, String paymentMethod, String status) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    /**
     * Formats object data as a pipe-delimited string for text file storage.
     * Required Format: paymentId|bookingId|amount|paymentMethod|status
     */
    public String toFileString() {
        return paymentId + "|" + bookingId + "|" + amount + "|" + paymentMethod + "|" + status;
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
