package com.wedding.weddingplanner.model;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 * 1. AGGREGATION: A Guest logically belongs to an Itinerary.
 *    By holding the 'itineraryId', the GuestList explicitly
 *    models this structural relationship.
 * 2. ENCAPSULATION: Object data is secured by private fields 
 *    and public accessor/mutator methods.
 * ============================================================
 */
public class GuestList {
    
    private String guestId;
    
    // AGGREGATION: Link to the specific Itinerary event
    private String itineraryId;
    
    private String guestName;
    private String rsvpStatus; // e.g., PENDING, ATTENDING, DECLINED
    private String dietaryRequirements;

    public GuestList() {}

    public GuestList(String guestId, String itineraryId, String guestName, String rsvpStatus, String dietaryRequirements) {
        this.guestId = guestId;
        this.itineraryId = itineraryId;
        this.guestName = guestName;
        this.rsvpStatus = rsvpStatus;
        this.dietaryRequirements = dietaryRequirements;
    }

    /**
     * Serializes GuestList to a pipe-delimited string.
     * Format: guestId|itineraryId|guestName|rsvpStatus|dietaryRequirements
     */
    public String toFileString() {
        return guestId + "|" + itineraryId + "|" + guestName + "|" + rsvpStatus + "|" + dietaryRequirements;
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================
    public String getGuestId() { return guestId; }
    public void setGuestId(String guestId) { this.guestId = guestId; }

    public String getItineraryId() { return itineraryId; }
    public void setItineraryId(String itineraryId) { this.itineraryId = itineraryId; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getRsvpStatus() { return rsvpStatus; }
    public void setRsvpStatus(String rsvpStatus) { this.rsvpStatus = rsvpStatus; }

    public String getDietaryRequirements() { return dietaryRequirements; }
    public void setDietaryRequirements(String dietaryRequirements) { this.dietaryRequirements = dietaryRequirements; }
}
