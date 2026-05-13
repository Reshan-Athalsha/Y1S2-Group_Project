package com.wedding.weddingplanner.model;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 * 1. INHERITANCE: Uses 'extends' keyword to inherit all properties
 *    and methods from the parent abstract class 'User'.
 * 2. POLYMORPHISM: Overrides the abstract displayRoleDetails() method
 *    to provide specific behavior for a Client.
 * 3. ENCAPSULATION: Utilizes 'super()' to pass parameters to the
 *    parent class constructor.
 * ============================================================
 */
public class Client extends User {

    /**
     * Default Constructor
     */
    public Client() {
        super();
        this.setRole("CLIENT"); // Hardcoding role for this subclass
    }

    /**
     * Parameterized Constructor
     */
    public Client(String userId, String name, String email, String password) {
        // Calls the constructor of the parent class (User)
        super(userId, name, email, password, "CLIENT");
    }

    /**
     * OOP POLYMORPHISM: Overriding the abstract method from User class.
     */
    @Override
    public void displayRoleDetails() {
        System.out.println("Role Details: This user is a registered CLIENT planning their wedding.");
    }
}
