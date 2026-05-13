package com.wedding.exception;

/**
 * ============================================================
 * CUSTOM EXCEPTION 2: InvalidPaymentException
 * ============================================================
 * OOP PRINCIPLE: EXCEPTION HANDLING (Custom Exception)
 *
 * This exception is thrown when a payment operation fails due
 * to invalid data — for example:
 *   - Negative or zero payment amount
 *   - Unsupported payment method
 *   - Booking already paid
 *   - Amount mismatch against expected total
 *
 * Extends Exception (checked exception), so any method that
 * can throw it MUST declare it in its signature with `throws`.
 *
 * Usage example:
 *   if (amount <= 0) {
 *       throw new InvalidPaymentException("Amount must be > 0", amount);
 *   }
 * ============================================================
 */
public class InvalidPaymentException extends Exception {

    // OOP: ENCAPSULATION — private fields with getters
    private final double invalidAmount;
    private final String reason;

    /**
     * Constructor with message only.
     * OOP: METHOD OVERLOADING — first constructor signature.
     *
     * @param message  Description of the payment error
     */
    public InvalidPaymentException(String message) {
        super(message);
        this.invalidAmount = 0.0;
        this.reason = message;
    }

    /**
     * Overloaded constructor with message AND the invalid amount.
     * OOP: METHOD OVERLOADING — second constructor signature.
     *
     * @param message        Description of the error
     * @param invalidAmount  The amount that caused the failure
     */
    public InvalidPaymentException(String message, double invalidAmount) {
        super(message);
        this.invalidAmount = invalidAmount;
        this.reason = message;
    }

    /**
     * Overloaded constructor with full context.
     * OOP: METHOD OVERLOADING — third constructor signature.
     *
     * @param message        Description of the error
     * @param invalidAmount  The invalid amount
     * @param cause          The root cause Throwable (for chaining)
     */
    public InvalidPaymentException(String message, double invalidAmount, Throwable cause) {
        super(message, cause);
        this.invalidAmount = invalidAmount;
        this.reason = message;
    }

    // ---- Getters (OOP: Encapsulation) ----

    /**
     * @return The invalid payment amount that triggered this exception
     */
    public double getInvalidAmount() {
        return invalidAmount;
    }

    /**
     * @return Human-readable reason for the payment failure
     */
    public String getReason() {
        return reason;
    }

    /**
     * OOP: METHOD OVERRIDING — overrides toString() from Throwable.
     */
    @Override
    public String toString() {
        return "InvalidPaymentException: " + getMessage()
                + " [InvalidAmount: " + invalidAmount + "]";
    }
}
