package com.weddingplanner.model;

/**
 * Represents an Administrator user in the Wedding Planner system.
 *
 * OOP Concepts Demonstrated:
 *
 *   INHERITANCE:
 *     AdminUser extends User, which extends BaseEntity.
 *     This creates a 3-level inheritance chain:
 *       BaseEntity  <--  User  <--  AdminUser
 *     AdminUser automatically inherits ALL fields and methods from User
 *     (firstName, lastName, email, password, role) without re-writing them.
 *     WHY: We reuse the User's code to avoid duplication (DRY principle).
 *
 *   POLYMORPHISM:
 *     AdminUser OVERRIDES the getRoleDetails() abstract method from User.
 *     At runtime, Java decides WHICH version of getRoleDetails() to call
 *     based on the actual object type -- not the declared variable type.
 *     Example:
 *       User u = new AdminUser(...);
 *       u.getRoleDetails();  // calls AdminUser's version, not User's!
 *     This is called "runtime polymorphism" or "dynamic method dispatch".
 *
 *   ENCAPSULATION:
 *     The 'adminLevel' field is private. It is only accessible through
 *     getAdminLevel() and setAdminLevel() -- never directly from outside.
 *     WHY: Protects the internal state. External code cannot set invalid values.
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
// INHERITANCE: AdminUser IS-A User (extends User class)
public class AdminUser extends User {

    /**
     * Controls the level of administrative access.
     * "super"    = full system access (can do everything)
     * "standard" = limited admin access
     *
     * ENCAPSULATION: this field is private -- only accessible via getter/setter.
     */
    private String adminLevel;

    /**
     * No-argument constructor.
     */
    public AdminUser() {
        super();                        // calls User's no-arg constructor
        this.adminLevel = "standard";   // safe default
    }

    /**
     * Full constructor for creating an AdminUser with all required fields.
     *
     * INHERITANCE: 'super(...)' calls the parent User constructor.
     * This sets firstName, lastName, email, password, role, and createdDate
     * in the parent class WITHOUT us needing to write that code again here.
     */
    public AdminUser(int id, String firstName, String lastName,
                     String email, String password,
                     String createdDate, String adminLevel) {
        // INHERITANCE: passes common fields up the chain to User's constructor.
        // We hardcode "admin" as the role because AdminUser is always an admin.
        super(id, firstName, lastName, email, password, "admin", createdDate);
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    /**
     * POLYMORPHISM -- Provides the AdminUser-specific implementation of
     * the abstract getRoleDetails() method declared in the User class.
     *
     * @return a human-readable description of this admin's role and privileges
     */
    @Override
    public String getRoleDetails() {
        return "Administrator (" + adminLevel + " level) -- "
                + "Can manage all users, vendors, bookings, and system settings.";
    }

    /**
     * Convenience method: checks whether this admin has "super" level access.
     * @return true if this admin has super-level access
     */
    public boolean isSuperAdmin() {
        return "super".equalsIgnoreCase(adminLevel);
    }

    @Override
    public String toString() {
        return "AdminUser[id=" + getId()
                + ", name=" + getFullName()
                + ", level=" + adminLevel + "]";
    }
}
