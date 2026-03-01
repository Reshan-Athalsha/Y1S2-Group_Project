package com.weddingplanner.model;

import com.weddingplanner.util.FileStorageUtil;

/**
 * Booking entity — extends BaseEntity.
 *
 * File format (bookings.txt):
 *   id | userId | vendorId | eventDate | eventType | guestCount | status | notes | createdDate
 *
 * Status: "Pending", "Confirmed", "Cancelled", "Completed"
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
public class Booking extends BaseEntity {

    private int userId;
    private int vendorId;
    private String eventDate;
    private String eventType;
    private int guestCount;
    private String status;
    private String notes;

    public Booking() { super(); }

    public Booking(int id, int userId, int vendorId, String eventDate,
                   String eventType, int guestCount, String status,
                   String notes, String createdDate) {
        super(id, createdDate);
        this.userId     = userId;
        this.vendorId   = vendorId;
        this.eventDate  = eventDate;
        this.eventType  = eventType;
        this.guestCount = guestCount;
        this.status     = status;
        this.notes      = notes;
    }

    // ─────────────── Getters & Setters ────────────────────────────
    public int    getUserId()                     { return userId; }
    public void   setUserId(int userId)           { this.userId = userId; }

    public int    getVendorId()                   { return vendorId; }
    public void   setVendorId(int vendorId)       { this.vendorId = vendorId; }

    public String getEventDate()                  { return eventDate; }
    public void   setEventDate(String eventDate)  { this.eventDate = eventDate; }

    public String getEventType()                  { return eventType; }
    public void   setEventType(String eventType)  { this.eventType = eventType; }

    public int    getGuestCount()                 { return guestCount; }
    public void   setGuestCount(int guestCount)   { this.guestCount = guestCount; }

    public String getStatus()                     { return status; }
    public void   setStatus(String status)        { this.status = status; }

    public String getNotes()                      { return notes; }
    public void   setNotes(String notes)          { this.notes = notes; }

    // ─────────────── File Serialization ───────────────────────────

    @Override
    public String toFileString() {
        return getId()
                + FileStorageUtil.SEPARATOR + userId
                + FileStorageUtil.SEPARATOR + vendorId
                + FileStorageUtil.SEPARATOR + eventDate
                + FileStorageUtil.SEPARATOR + eventType
                + FileStorageUtil.SEPARATOR + guestCount
                + FileStorageUtil.SEPARATOR + status
                + FileStorageUtil.SEPARATOR + notes
                + FileStorageUtil.SEPARATOR + getCreatedDate();
    }

    @Override
    public void fromFileString(String line) {
        String[] p = line.split(FileStorageUtil.DELIMITER);
        if (p.length >= 9) {
            setId(Integer.parseInt(p[0].trim()));
            this.userId     = Integer.parseInt(p[1].trim());
            this.vendorId   = Integer.parseInt(p[2].trim());
            this.eventDate  = p[3].trim();
            this.eventType  = p[4].trim();
            this.guestCount = Integer.parseInt(p[5].trim());
            this.status     = p[6].trim();
            this.notes      = p[7].trim();
            setCreatedDate(p[8].trim());
        }
    }
}
