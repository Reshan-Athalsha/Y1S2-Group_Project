package com.weddingplanner.model;

import java.io.Serializable;

/**
 * Abstract base class for every entity in the Wedding Planner system.
 *
 * ── OOP Concepts demonstrated ──────────────────────────
 *   • Encapsulation  → private fields + public getters/setters
 *   • Abstraction     → toFileString() & fromFileString() are abstract
 *   • Inheritance     → User, Vendor, Booking, Review all extend this
 *
 * Every entity is stored as a single pipe-delimited line in a .txt file.
 * Subclasses must implement the two abstract methods to convert
 * between object state and file-line representation.
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
public abstract class BaseEntity implements Serializable {

    // ─────────────── Private Fields (Encapsulation) ───────────────
    private int id;
    private String createdDate;   // stored as "yyyy-MM-dd HH:mm:ss"

    // ─────────────── Constructors ─────────────────────────────────
    protected BaseEntity() { }

    protected BaseEntity(int id, String createdDate) {
        this.id = id;
        this.createdDate = createdDate;
    }

    // ─────────────── Getters & Setters ────────────────────────────
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    // ─────────────── Abstract Methods (Abstraction) ──────────────

    /**
     * Converts this entity into a pipe-delimited String for file storage.
     * Example return: "1|John|Doe|john@mail.com|password|2026-03-01 10:00:00"
     *
     * @return file-ready string representation
     */
    public abstract String toFileString();

    /**
     * Populates this entity's fields from a pipe-delimited line
     * read from a .txt file.
     *
     * @param line a single line from the data file
     */
    public abstract void fromFileString(String line);

    // ─────────────── Common Utility ──────────────────────────────

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id=" + id + "]";
    }
}
