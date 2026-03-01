package com.weddingplanner.model;

import com.weddingplanner.util.FileStorageUtil;

/**
 * User entity — demonstrates Inheritance (extends BaseEntity)
 * and Encapsulation (private fields with getters/setters).
 *
 * File format (users.txt):
 *   id | firstName | lastName | email | password | role | createdDate
 *
 * Roles: "customer" or "admin"
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
public class User extends BaseEntity {

    // ─────────────── Private Fields ───────────────────────────────
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;        // "customer" | "admin"

    // ─────────────── Constructors ─────────────────────────────────
    public User() {
        super();
    }

    public User(int id, String firstName, String lastName,
                String email, String password, String role,
                String createdDate) {
        super(id, createdDate);
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.password  = password;
        this.role      = role;
    }

    // ─────────────── Getters & Setters ────────────────────────────
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // ─────────────── File Serialization ───────────────────────────

    /**
     * Converts this User into a pipe-delimited line.
     * Format: id|firstName|lastName|email|password|role|createdDate
     */
    @Override
    public String toFileString() {
        return getId()
                + FileStorageUtil.SEPARATOR + firstName
                + FileStorageUtil.SEPARATOR + lastName
                + FileStorageUtil.SEPARATOR + email
                + FileStorageUtil.SEPARATOR + password
                + FileStorageUtil.SEPARATOR + role
                + FileStorageUtil.SEPARATOR + getCreatedDate();
    }

    /**
     * Populates fields from a pipe-delimited line.
     */
    @Override
    public void fromFileString(String line) {
        String[] parts = line.split(FileStorageUtil.DELIMITER);
        if (parts.length >= 7) {
            setId(Integer.parseInt(parts[0].trim()));
            this.firstName   = parts[1].trim();
            this.lastName    = parts[2].trim();
            this.email       = parts[3].trim();
            this.password    = parts[4].trim();
            this.role        = parts[5].trim();
            setCreatedDate(parts[6].trim());
        }
    }

    @Override
    public String toString() {
        return "User[id=" + getId() + ", name=" + getFullName()
                + ", email=" + email + ", role=" + role + "]";
    }
}
