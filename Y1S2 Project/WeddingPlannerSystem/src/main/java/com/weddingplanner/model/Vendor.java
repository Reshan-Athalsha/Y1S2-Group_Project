package com.weddingplanner.model;

import com.weddingplanner.util.FileStorageUtil;

/**
 * Vendor entity — extends BaseEntity.
 *
 * File format (vendors.txt):
 *   id | businessName | category | contactEmail | phone | location | description | rating | createdDate
 *
 * Categories: "Venue", "Catering", "Photography", "Florist",
 *             "Music/DJ", "Decoration", "Bridal Wear", "Other"
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
public class Vendor extends BaseEntity {

    private String businessName;
    private String category;
    private String contactEmail;
    private String phone;
    private String location;
    private String description;
    private double rating;

    public Vendor() { super(); }

    public Vendor(int id, String businessName, String category,
                  String contactEmail, String phone, String location,
                  String description, double rating, String createdDate) {
        super(id, createdDate);
        this.businessName = businessName;
        this.category     = category;
        this.contactEmail = contactEmail;
        this.phone        = phone;
        this.location     = location;
        this.description  = description;
        this.rating       = rating;
    }

    // ─────────────── Getters & Setters ────────────────────────────
    public String getBusinessName()              { return businessName; }
    public void   setBusinessName(String name)   { this.businessName = name; }

    public String getCategory()                  { return category; }
    public void   setCategory(String category)   { this.category = category; }

    public String getContactEmail()              { return contactEmail; }
    public void   setContactEmail(String email)  { this.contactEmail = email; }

    public String getPhone()                     { return phone; }
    public void   setPhone(String phone)         { this.phone = phone; }

    public String getLocation()                  { return location; }
    public void   setLocation(String location)   { this.location = location; }

    public String getDescription()               { return description; }
    public void   setDescription(String desc)    { this.description = desc; }

    public double getRating()                    { return rating; }
    public void   setRating(double rating)       { this.rating = rating; }

    // ─────────────── File Serialization ───────────────────────────

    @Override
    public String toFileString() {
        return getId()
                + FileStorageUtil.SEPARATOR + businessName
                + FileStorageUtil.SEPARATOR + category
                + FileStorageUtil.SEPARATOR + contactEmail
                + FileStorageUtil.SEPARATOR + phone
                + FileStorageUtil.SEPARATOR + location
                + FileStorageUtil.SEPARATOR + description
                + FileStorageUtil.SEPARATOR + rating
                + FileStorageUtil.SEPARATOR + getCreatedDate();
    }

    @Override
    public void fromFileString(String line) {
        String[] p = line.split(FileStorageUtil.DELIMITER);
        if (p.length >= 9) {
            setId(Integer.parseInt(p[0].trim()));
            this.businessName = p[1].trim();
            this.category     = p[2].trim();
            this.contactEmail = p[3].trim();
            this.phone        = p[4].trim();
            this.location     = p[5].trim();
            this.description  = p[6].trim();
            this.rating       = Double.parseDouble(p[7].trim());
            setCreatedDate(p[8].trim());
        }
    }
}
