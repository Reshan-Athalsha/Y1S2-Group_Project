package com.wedding.weddingplanner.controller;

import com.wedding.weddingplanner.model.Payment;
import com.wedding.weddingplanner.model.Booking;
import com.wedding.util.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller for Payment Management.
 */
@Controller
@RequestMapping("/payments")
public class PaymentController {

    // Show the checkout form for a specific booking
    @GetMapping("/checkout/{bookingId}")
    public String showCheckoutForm(@PathVariable String bookingId, Model model) {
        Payment payment = new Payment();
        // COMPOSITION: Map the specific Booking ID to the Payment object
        payment.setBookingId(bookingId);
        
        model.addAttribute("payment", payment);
        return "checkoutForm";
    }

    // Process a payment
    @PostMapping("/process")
    public String processPayment(@ModelAttribute Payment payment) {
        try {
            // FILE I/O & EXCEPTIONS: Logic wrapped in try-catch
            payment.setStatus("COMPLETED");
            if (payment.getPaymentId() == null || payment.getPaymentId().isEmpty()) {
                payment.setPaymentId(FileHandler.generateId(FileHandler.PAYMENTS_FILE, "PAY"));
            }
            
            // Step 1: Save the new Payment record
            FileHandler.appendSingleLine(FileHandler.PAYMENTS_FILE, payment.toFileString());
            
            // Step 2: Update the linked Booking's Status to 'CONFIRMED'
            // COLLECTIONS: Modifying an ArrayList in memory and overwriting the text file
            ArrayList<Booking> bookings = BookingController.getAllBookings();
            for (int i = 0; i < bookings.size(); i++) {
                if (bookings.get(i).getBookingId().equals(payment.getBookingId())) {
                    bookings.get(i).setStatus("CONFIRMED"); // Update status
                    break;
                }
            }
            BookingController.saveAllBookings(bookings); // Overwrite the bookings file

        } catch (Exception e) {
            System.err.println("Exception occurred while processing payment: " + e.getMessage());
        }
        return "redirect:/bookings";
    }
}
