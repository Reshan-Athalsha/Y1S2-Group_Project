package com.wedding.weddingplanner.model;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 * 1. INHERITANCE: Extending the User abstract class to inherit base attributes.
 * 2. POLYMORPHISM: Overriding displayRoleDetails() and toFileString()
 *    to provide Vendor-specific behavior.
 * ============================================================
 */
public class Vendor extends User {
    
    private String businessName;
    private String category;
    private double price;
    private String description;

    /**
     * Default constructor (OOP Overloading)
     */
    public Vendor() {
        super();
    }

    /**
     * Parameterized constructor (OOP Overloading)
     */
    public Vendor(String userId, String name, String email, String password, String role, 
                  String businessName, String category, double price, String description) {
        // INHERITANCE: Calling the superclass (User) constructor
        super(userId, name, email, password, role);
        this.businessName = businessName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    /**
     * POLYMORPHISM: Overriding the abstract method from User.
     * Provides a specialized implementation for Vendor.
     */
    @Override
    public void displayRoleDetails() {
        System.out.println("Role: VENDOR. Business Name: " + businessName + ", Category: " + category);
    }

    /**
     * POLYMORPHISM: Overriding the FileOperations method from User.
     * Incorporates the Vendor-specific fields.
     */
    @Override
    public String toFileString() {
        // Format: userId|name|email|password|role|businessName|category|price|description
        return super.toFileString() + "|" + businessName + "|" + category + "|" + price + "|" + description;
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
