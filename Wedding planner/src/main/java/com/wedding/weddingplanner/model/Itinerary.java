package com.wedding.weddingplanner.model;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 * 1. AGGREGATION: The Itinerary aggregates under a 'userId'.
 *    A User has an Itinerary (has-a relationship), but Itineraries 
 *    exist semi-independently.
 * 2. ENCAPSULATION: All fields are private, with public getters 
 *    and setters to safely manipulate object state.
 * ============================================================
 */
public class Itinerary {
    
    private String itineraryId;
    
    // AGGREGATION: Link to the User planning this event
    private String userId;
    
    private String eventName;
    private String eventDate;
    private String time;
    private String description;

    public Itinerary() {}

    public Itinerary(String itineraryId, String userId, String eventName, String eventDate, String time, String description) {
        this.itineraryId = itineraryId;
        this.userId = userId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.time = time;
        this.description = description;
    }

    /**
     * Serializes Itinerary to a pipe-delimited string.
     * Format: itineraryId|userId|eventName|eventDate|time|description
     */
    public String toFileString() {
        return itineraryId + "|" + userId + "|" + eventName + "|" + eventDate + "|" + time + "|" + description;
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================
    public String getItineraryId() { return itineraryId; }
    public void setItineraryId(String itineraryId) { this.itineraryId = itineraryId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
