package com.wedding.exception;

/**
 * ============================================================
 * CUSTOM EXCEPTION 1: UserNotFoundException
 * ============================================================
 * OOP PRINCIPLE: EXCEPTION HANDLING (Custom Exception)
 *
 * This exception is thrown when a user lookup (by ID, email,
 * or username) fails to find a matching record in users.txt.
 *
 * Extends RuntimeException so it does NOT need to be declared
 * in method signatures (unchecked exception), making the code
 * cleaner while still being catchable when needed.
 *
 * Usage example:
 *   if (user == null) {
 *       throw new UserNotFoundException("User with email " + email + " not found.");
 *   }
 * ============================================================
 */
public class UserNotFoundException extends RuntimeException {

    // OOP: ENCAPSULATION — private field with getter
    private final String searchKey;

    /**
     * Constructor with a custom message.
     * Demonstrates METHOD OVERLOADING (multiple constructors).
     *
     * @param message  Description of what was not found
     */
    public UserNotFoundException(String message) {
        super(message);
        this.searchKey = "UNKNOWN";
    }

    /**
     * Overloaded constructor with message AND the search key
     * that failed (e.g., the email address or user ID).
     *
     * OOP: METHOD OVERLOADING — same constructor name,
     * different parameter list.
     *
     * @param message    Description of the error
     * @param searchKey  The key/value that was searched for
     */
    public UserNotFoundException(String message, String searchKey) {
        super(message);
        this.searchKey = searchKey;
    }

    /**
     * Returns the search key that caused this exception.
     *
     * @return  The failed search key (email, ID, etc.)
     */
    public String getSearchKey() {
        return searchKey;
    }

    /**
     * Returns a full descriptive message including the search key.
     *
     * OOP: METHOD OVERRIDING — overrides toString() from Throwable.
     */
    @Override
    public String toString() {
        return "UserNotFoundException: " + getMessage() + " [SearchKey: " + searchKey + "]";
    }
}
