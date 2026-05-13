package com.wedding.weddingplanner.model;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 * 1. INHERITANCE: 'extends User'. This directly models an IS-A 
 *    relationship, allowing Admin to inherit core properties while
 *    representing a highly privileged system user.
 * 2. POLYMORPHISM: The abstract `displayRoleDetails()` method from 
 *    the parent User class is overridden to provide Admin-specific 
 *    output behavior at runtime.
 * 3. ENCAPSULATION: 'adminLevel' is kept securely private.
 * ============================================================
 */
public class Admin extends User {

    // ENCAPSULATION: Specialized property unique to Admin
    private String adminLevel; // e.g., SUPER, MODERATOR

    /** 
     * Default Constructor 
     * INHERITANCE: Invokes the parameterless super() constructor.
     */
    public Admin() {
        super();
        this.adminLevel = "SUPER";
    }

    /** 
     * Parameterized Constructor
     * INHERITANCE: Uses super(...) to dynamically construct the 
     * underlying User parent object with exact parameters.
     */
    public Admin(String userId, String name, String email, String password, String adminLevel) {
        super(userId, name, email, password, "ADMIN");
        this.adminLevel = adminLevel;
    }

    /**
     * POLYMORPHISM: Overriding the abstract method inherited from 
     * the User class to customize the detail display for Admins.
     */
    @Override
    public void displayRoleDetails() {
        System.out.println("Role: ADMIN | Privilege Level: " + adminLevel);
    }

    /**
     * Serializes Admin to a pipe-delimited string for text file storage.
     * Required Format: userId|name|email|password|role|adminLevel
     */
    @Override
    public String toFileString() {
        return getUserId() + "|" + getName() + "|" + getEmail() + "|" + getPassword() + "|" + getRole() + "|" + adminLevel;
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================
    public String getAdminLevel() { return adminLevel; }
    public void setAdminLevel(String adminLevel) { this.adminLevel = adminLevel; }
}
