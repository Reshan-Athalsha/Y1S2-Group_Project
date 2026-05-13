package com.wedding.weddingplanner.controller;

import com.wedding.weddingplanner.model.Booking;
import com.wedding.weddingplanner.model.Vendor;
import com.wedding.util.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller for the Admin Dashboard & Cross-File Analytics.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"", "/dashboard"})
    public String adminDashboard(Model model) {
        try {
            // CROSS-FILE ANALYTICS & COLLECTIONS
            // 1. Fetch live data across multiple components and files
            ArrayList<Vendor> vendors = VendorController.getAllVendors();
            ArrayList<Booking> bookings = BookingController.getAllBookings();
            
            // 2. Fetch standard users manually via File I/O
            ArrayList<String[]> rawUsers = new ArrayList<>();
            ArrayList<String> userLines = FileHandler.readAllLines(FileHandler.USERS_FILE);
            for (String line : userLines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    rawUsers.add(parts); // Parsing into Array structure
                }
            }

            // 3. Analytics Calculation using Collections
            int totalUsers = rawUsers.size();
            int totalVendors = vendors.size();
            int totalBookings = bookings.size();
            int pendingBookings = 0;
            
            for (Booking b : bookings) {
                if ("PENDING".equalsIgnoreCase(b.getStatus())) {
                    pendingBookings++;
                }
            }

            // 4. Inject Analytics into the Thymeleaf Model
            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalVendors", totalVendors);
            model.addAttribute("totalBookings", totalBookings);
            model.addAttribute("pendingBookings", pendingBookings);

            // 5. Inject the raw collections for Master Lists
            model.addAttribute("users", rawUsers);
            model.addAttribute("vendors", vendors);

        } catch (Exception e) {
            System.err.println("Exception occurred loading admin dashboard: " + e.getMessage());
        }

        return "adminDashboard";
    }

    /**
     * ADMIN PRIVILEGES: Completely remove a User from the system text file.
     */
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable String id) {
        try {
            // FILE I/O & EXCEPTIONS
            ArrayList<String> lines = FileHandler.readAllLines(FileHandler.USERS_FILE);
            ArrayList<String> updatedLines = new ArrayList<>();
            
            // COLLECTIONS: Filter out the user to effectively "delete" them
            for (String line : lines) {
                // Check if the pipe-delimited string starts with the target ID
                if (!line.startsWith(id + "|")) {
                    updatedLines.add(line);
                }
            }
            
            // Write the mutated collection back to the users.txt file
            FileHandler.writeAllLines(FileHandler.USERS_FILE, updatedLines);
            
        } catch (Exception e) {
            System.err.println("Exception occurred deleting user: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}
