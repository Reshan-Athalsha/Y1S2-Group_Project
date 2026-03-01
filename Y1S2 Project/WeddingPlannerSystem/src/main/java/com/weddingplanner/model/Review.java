package com.weddingplanner.model;

import com.weddingplanner.util.FileStorageUtil;

/**
 * Review entity — extends BaseEntity.
 *
 * File format (reviews.txt):
 *   id | userId | vendorId | rating | comment | createdDate
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
public class Review extends BaseEntity {

    private int userId;
    private int vendorId;
    private int rating;          // 1-5 stars
    private String comment;

    public Review() { super(); }

    public Review(int id, int userId, int vendorId, int rating,
                  String comment, String createdDate) {
        super(id, createdDate);
        this.userId   = userId;
        this.vendorId = vendorId;
        this.rating   = rating;
        this.comment  = comment;
    }

    // ─────────────── Getters & Setters ────────────────────────────
    public int    getUserId()                   { return userId; }
    public void   setUserId(int userId)         { this.userId = userId; }

    public int    getVendorId()                 { return vendorId; }
    public void   setVendorId(int vendorId)     { this.vendorId = vendorId; }

    public int    getRating()                   { return rating; }
    public void   setRating(int rating)         { this.rating = rating; }

    public String getComment()                  { return comment; }
    public void   setComment(String comment)    { this.comment = comment; }

    // ─────────────── File Serialization ───────────────────────────

    @Override
    public String toFileString() {
        return getId()
                + FileStorageUtil.SEPARATOR + userId
                + FileStorageUtil.SEPARATOR + vendorId
                + FileStorageUtil.SEPARATOR + rating
                + FileStorageUtil.SEPARATOR + comment
                + FileStorageUtil.SEPARATOR + getCreatedDate();
    }

    @Override
    public void fromFileString(String line) {
        String[] p = line.split(FileStorageUtil.DELIMITER);
        if (p.length >= 6) {
            setId(Integer.parseInt(p[0].trim()));
            this.userId   = Integer.parseInt(p[1].trim());
            this.vendorId = Integer.parseInt(p[2].trim());
            this.rating   = Integer.parseInt(p[3].trim());
            this.comment  = p[4].trim();
            setCreatedDate(p[5].trim());
        }
    }
}
