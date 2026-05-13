package com.wedding.interfaces;

/**
 * ============================================================
 * OOP PRINCIPLE: ABSTRACTION (Interface) + POLYMORPHISM
 * ============================================================
 * Displayable defines a contract that forces all implementing
 * entity classes to provide their own version of displayDetails().
 *
 * This demonstrates POLYMORPHISM — each class (Client, Vendor,
 * Admin, Booking, etc.) will override displayDetails() with its
 * own unique implementation, while sharing the same interface type.
 * ============================================================
 */
public interface Displayable {

    /**
     * Returns a human-readable, formatted String representation
     * of the object's key fields.
     *
     * Each implementing class provides its own display logic,
     * demonstrating runtime polymorphism.
     *
     * @return  A formatted String of the object's details
     */
    String displayDetails();
}
