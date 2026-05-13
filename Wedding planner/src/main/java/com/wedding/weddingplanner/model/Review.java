package com.wedding.weddingplanner.model;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 * 1. AGGREGATION / COMPOSITION: The Review class explicitly
 *    links two independent entities (a User and a Vendor) by 
 *    storing their unique IDs. This demonstrates how relational 
 *    logic is maintained in a purely Object-Oriented context.
 * 2. ENCAPSULATION: All internal states (rating, comment) are
 *    strictly private and secured behind public getter/setter methods.
 * ============================================================
 */
public class Review {
    
    private String reviewId;
    
    // AGGREGATION: Linking the User who wrote the review
    private String userId;
    
    // AGGREGATION: Linking the Vendor receiving the review
    private String vendorId;
    
    private int rating; // 1 to 5
    private String comment;
    private String date;

    public Review() {}

    public Review(String reviewId, String userId, String vendorId, int rating, String comment, String date) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.vendorId = vendorId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Serializes Review to a pipe-delimited string.
     * Required Format: reviewId|userId|vendorId|rating|comment|date
     */
    public String toFileString() {
        return reviewId + "|" + userId + "|" + vendorId + "|" + rating + "|" + comment + "|" + date;
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
