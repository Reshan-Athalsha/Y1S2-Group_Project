package com.wedding.weddingplanner.model;

import com.wedding.weddingplanner.interfaces.FileOperations;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 * 1. ABSTRACTION: Declared 'abstract', so it cannot be instantiated.
 *    Acts as a blueprint for concrete classes like Client.
 * 2. ENCAPSULATION: All attributes are private. Access is provided
 *    strictly via public getter and setter methods.
 * 3. INTERFACE IMPLEMENTATION: Implements FileOperations contract.
 * ============================================================
 */
public abstract class User implements FileOperations {
    
    // Encapsulated fields
    private String userId;
    private String name;
    private String email;
    private String password;
    private String role;

    /**
     * Default constructor (OOP Overloading)
     */
    public User() {}

    /**
     * Parameterized constructor (OOP Overloading)
     */
    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Abstract method enforcing Polymorphism.
     * Subclasses MUST provide their own implementation.
     */
    public abstract void displayRoleDetails();

    /**
     * Implements the FileOperations interface.
     * Serializes the object into a pipe-delimited string.
     */
    @Override
    public String toFileString() {
        // Required Format: userId|name|email|password|role
        return userId + "|" + name + "|" + email + "|" + password + "|" + role;
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
