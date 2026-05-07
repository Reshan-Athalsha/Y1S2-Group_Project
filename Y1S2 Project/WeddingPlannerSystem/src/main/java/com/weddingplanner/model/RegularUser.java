package com.weddingplanner.model;

/**
 * Represents a regular (customer) user in the Wedding Planner system.
 * Typically a couple or individual planning their wedding.
 *
 * OOP Concepts Demonstrated:
 *
 *   INHERITANCE:
 *     RegularUser extends User (which extends BaseEntity).
 *     Inherits all User properties without duplicating any code.
 *     WHY: A RegularUser IS-A User -- the "is-a" relationship is the
 *     key criterion for using inheritance over composition.
 *
 *   POLYMORPHISM:
 *     Overrides getRoleDetails() with customer-specific information.
 *     Same method name, completely different behaviour -- that is polymorphism.
 *     WHY: Allows the system to call user.getRoleDetails() on ANY user object
 *     and get a meaningful, role-appropriate result automatically.
 *
 *   ENCAPSULATION:
 *     'phoneNumber' and 'weddingDate' are private fields. Accessible only
 *     through getters/setters. This gives us control over validation.
 *     WHY: If fields are public, any code can assign nonsense values like
 *     empty strings or null. Setters can validate before accepting a value.
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
// INHERITANCE: RegularUser IS-A User (extends User class)
public class RegularUser extends User {

    private String phoneNumber;
    private String weddingDate;

    /**
     * No-argument constructor.
     */
    public RegularUser() {
        super();                        // calls User's no-arg constructor
        this.phoneNumber = "";
        this.weddingDate = "Not set";
    }

    /**
     * Full constructor for creating a RegularUser with all required fields.
     */
    public RegularUser(int id, String firstName, String lastName,
                       String email, String password,
                       String createdDate, String phoneNumber, String weddingDate) {
        // INHERITANCE: 'super(...)' invokes User's full constructor.
        super(id, firstName, lastName, email, password, "customer", createdDate);
        this.phoneNumber = (phoneNumber != null) ? phoneNumber : "";
        this.weddingDate = (weddingDate != null) ? weddingDate : "Not set";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        // Trim whitespace before storing -- basic validation via the setter
        this.phoneNumber = (phoneNumber != null) ? phoneNumber.trim() : "";
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = (weddingDate != null) ? weddingDate.trim() : "Not set";
    }

    /**
     * POLYMORPHISM -- Provides the RegularUser-specific implementation of
     * the abstract getRoleDetails() method declared in User.
     */
    @Override
    public String getRoleDetails() {
        return "Customer -- Can browse vendors, make bookings, "
                + "and submit reviews. Wedding date: " + weddingDate;
    }

    public boolean hasWeddingDate() {
        return weddingDate != null
                && !weddingDate.isEmpty()
                && !weddingDate.equalsIgnoreCase("Not set");
    }

    @Override
    public String toString() {
        return "RegularUser[id=" + getId()
                + ", name=" + getFullName()
                + ", weddingDate=" + weddingDate + "]";
    }
}
