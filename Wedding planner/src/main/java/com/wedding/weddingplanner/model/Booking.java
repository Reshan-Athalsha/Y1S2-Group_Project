package com.wedding.weddingplanner.model;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 * 1. ENCAPSULATION: All fields are marked 'private'. Access is
 *    controlled strictly via public getters and setters to 
 *    protect data integrity and hide the internal representation.
 * 2. DATA ABSTRACTION: Exposes a clean toFileString() method to 
 *    serialize the object without exposing internal formatting logic.
 * ============================================================
 */
public class Booking {
    
    private String bookingId;
    private String clientId;
    private String vendorId;
    private String date;
    private String status; // e.g., PENDING, CONFIRMED, CANCELLED

    /** Default constructor */
    public Booking() {}

    /** Parameterized constructor */
    public Booking(String bookingId, String clientId, String vendorId, String date, String status) {
        this.bookingId = bookingId;
        this.clientId = clientId;
        this.vendorId = vendorId;
        this.date = date;
        this.status = status;
    }

    /**
     * Formats object data as a pipe-delimited string for text file storage.
     * Required Format: bookingId|clientId|vendorId|date|status
     */
    public String toFileString() {
        return bookingId + "|" + clientId + "|" + vendorId + "|" + date + "|" + status;
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
