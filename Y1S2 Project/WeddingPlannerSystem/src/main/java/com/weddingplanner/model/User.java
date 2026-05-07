package com.weddingplanner.model;

import com.weddingplanner.util.FileStorageUtil;

/**
 * Abstract base class for all user types in the Wedding Planner system.
 *
 * OOP Concepts Demonstrated:
 *
 *   ENCAPSULATION:
 *     All fields (firstName, lastName, email, password, role) are PRIVATE.
 *     They can only be read or changed through public getter/setter methods.
 *     WHY: This hides internal data and prevents external code from assigning
 *     invalid values (e.g., a null email). It is the "data hiding" principle.
 *
 *   INHERITANCE:
 *     User extends BaseEntity -- inheriting the common 'id' and 'createdDate'
 *     fields and the abstract file serialization methods.
 *     AdminUser and RegularUser extend User in turn.
 *     WHY: Avoids duplicating the same fields across every user class.
 *
 *   ABSTRACTION:
 *     getRoleDetails() is declared abstract here. This forces EVERY subclass
 *     (AdminUser, RegularUser) to provide its OWN implementation.
 *     WHY: We define WHAT must exist (the contract/interface) without saying
 *     HOW. The abstract method is a rule every child class must follow.
 *
 *   POLYMORPHISM:
 *     Because getRoleDetails() is overridden in each subclass, calling it on
 *     a User reference produces different output depending on the actual object:
 *       User u1 = new AdminUser(...);
 *       User u2 = new RegularUser(...);
 *       u1.getRoleDetails();  // calls AdminUser's version at runtime
 *       u2.getRoleDetails();  // calls RegularUser's version at runtime
 *     This is "runtime polymorphism" -- the JVM picks the right method.
 *
 * File format for users.txt:
 *   id | firstName | lastName | email | password | role | createdDate
 * Roles: "customer" or "admin"
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
// ABSTRACTION: 'abstract' means you cannot do: new User(...)
// You MUST instantiate a concrete subclass: new AdminUser(...) or new RegularUser(...)
// INHERITANCE: User extends BaseEntity -- inherits id and createdDate
public abstract class User extends BaseEntity {

    // -------------------------------------------------------------------------
    // Private Fields (Encapsulation)
    // -------------------------------------------------------------------------

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;    // "customer" | "admin"

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /** No-argument constructor -- required for fromFileString() deserialization. */
    public User() {
        super();
    }

    /**
     * Full constructor -- called by AdminUser and RegularUser via super(...).
     */
    public User(int id, String firstName, String lastName,
                String email, String password, String role,
                String createdDate) {
        super(id, createdDate);        // INHERITANCE: pass id and date to BaseEntity
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.password  = password;
        this.role      = role;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters (Encapsulation)
    // -------------------------------------------------------------------------

    public String getFirstName()              { return firstName; }
    public void   setFirstName(String name)   { this.firstName = name; }

    public String getLastName()               { return lastName; }
    public void   setLastName(String name)    { this.lastName = name; }

    public String getEmail()                  { return email; }
    public void   setEmail(String email)      { this.email = email; }

    public String getPassword()               { return password; }
    public void   setPassword(String pw)      { this.password = pw; }

    public String getRole()                   { return role; }
    public void   setRole(String role)        { this.role = role; }

    /** Convenience method: returns first name + space + last name. */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // -------------------------------------------------------------------------
    // Abstract Method (Abstraction + Polymorphism)
    // -------------------------------------------------------------------------

    /**
     * ABSTRACTION: Declares WHAT every User subclass must be able to do.
     * Does NOT say HOW -- that is left to AdminUser and RegularUser.
     *
     * POLYMORPHISM: When overridden in subclasses, calling this method on a
     * User reference will produce different output depending on whether the
     * actual object is an AdminUser or a RegularUser.
     * The JVM picks the correct version at runtime (not at compile time).
     *
     * Example showing runtime polymorphism:
     *   User u1 = new AdminUser(...);
     *   User u2 = new RegularUser(...);
     *   System.out.println(u1.getRoleDetails()); // AdminUser's implementation
     *   System.out.println(u2.getRoleDetails()); // RegularUser's implementation
     *
     * @return a role-specific description of this user's capabilities
     */
    // ABSTRACTION: abstract method -- subclasses MUST implement this or they
    // will also become abstract and cannot be instantiated.
    public abstract String getRoleDetails();

    // -------------------------------------------------------------------------
    // File Serialization (implements BaseEntity's abstract methods)
    // -------------------------------------------------------------------------

    /**
     * Converts this User into a pipe-delimited String for storage in users.txt.
     * Format: id|firstName|lastName|email|password|role|createdDate
     *
     * ABSTRACTION: This implements the abstract toFileString() from BaseEntity.
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
     * Populates this User's fields from a pipe-delimited line read from users.txt.
     * Format: id|firstName|lastName|email|password|role|createdDate
     *
     * ABSTRACTION: This implements the abstract fromFileString() from BaseEntity.
     */
    @Override
    public void fromFileString(String line) {
        String[] parts = line.split(FileStorageUtil.DELIMITER);
        if (parts.length >= 7) {
            setId(Integer.parseInt(parts[0].trim()));
            this.firstName = parts[1].trim();
            this.lastName  = parts[2].trim();
            this.email     = parts[3].trim();
            this.password  = parts[4].trim();
            this.role      = parts[5].trim();
            setCreatedDate(parts[6].trim());
        }
    }

    @Override
    public String toString() {
        return "User[id=" + getId() + ", name=" + getFullName()
                + ", email=" + email + ", role=" + role + "]";
    }
}
