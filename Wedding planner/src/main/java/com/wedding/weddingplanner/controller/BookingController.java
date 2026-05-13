package com.wedding.weddingplanner.controller;

import com.wedding.weddingplanner.model.Booking;
import com.wedding.util.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller for Booking Management.
 */
@Controller
@RequestMapping("/bookings")
public class BookingController {

    // View all bookings
    @GetMapping
    public String viewBookings(Model model) {
        ArrayList<Booking> bookings = getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookingHistory";
    }

    // Create a new booking
    @PostMapping("/new")
    public String createBooking(@ModelAttribute Booking booking) {
        try {
            // FILE I/O & EXCEPTIONS: Encased in try-catch to handle IO errors
            booking.setStatus("PENDING");
            if (booking.getBookingId() == null || booking.getBookingId().isEmpty()) {
                booking.setBookingId(FileHandler.generateId(FileHandler.BOOKINGS_FILE, "BKG"));
            }
            FileHandler.appendSingleLine(FileHandler.BOOKINGS_FILE, booking.toFileString());
        } catch (Exception e) {
            System.err.println("Exception occurred while creating booking: " + e.getMessage());
        }
        return "redirect:/bookings";
    }

    // Cancel an existing booking
    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable String id) {
        try {
            // COLLECTIONS: Read the full file into an ArrayList, modify target, overwrite file
            ArrayList<Booking> bookings = getAllBookings();
            for (int i = 0; i < bookings.size(); i++) {
                if (bookings.get(i).getBookingId().equals(id)) {
                    bookings.get(i).setStatus("CANCELLED"); // Modify in memory
                    break;
                }
            }
            saveAllBookings(bookings); // Overwrite the file
        } catch (Exception e) {
            System.err.println("Exception occurred while cancelling booking: " + e.getMessage());
        }
        return "redirect:/bookings";
    }

    /**
     * Helper Method: Reads pipe-delimited strings from file and maps them to Booking objects.
     */
    public static ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> bookings = new ArrayList<>();
        try {
            // FILE I/O & EXCEPTIONS
            ArrayList<String> lines = FileHandler.readAllLines(FileHandler.BOOKINGS_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    Booking b = new Booking(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    bookings.add(b);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception occurred while reading bookings: " + e.getMessage());
        }
        return bookings;
    }

    /**
     * Helper Method: Writes the ArrayList of Booking objects back to the file.
     */
    public static void saveAllBookings(ArrayList<Booking> bookings) {
        try {
            // FILE I/O & EXCEPTIONS
            ArrayList<String> lines = new ArrayList<>();
            for (Booking b : bookings) {
                lines.add(b.toFileString());
            }
            FileHandler.writeAllLines(FileHandler.BOOKINGS_FILE, lines);
        } catch (Exception e) {
            System.err.println("Exception occurred while writing bookings: " + e.getMessage());
        }
    }
}
