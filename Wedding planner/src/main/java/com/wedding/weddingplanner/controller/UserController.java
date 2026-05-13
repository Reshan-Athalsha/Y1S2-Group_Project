package com.wedding.weddingplanner.controller;

import com.wedding.weddingplanner.model.Client;
import com.wedding.weddingplanner.model.User;
import com.wedding.util.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.UUID;

/**
 * ============================================================
 * UserController - Spring Boot Controller for Component 1
 * Handles User Registration, Login, Profile Viewing, Updating,
 * and Deletion using pure File I/O and ArrayList manipulation.
 * ============================================================
 */
@Controller
public class UserController {

    // ==========================================
    // 1. REGISTRATION
    // ==========================================
    
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
            
        // Basic Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "All fields are required.");
            return "register";
        }

        // Generate a random unique ID (e.g., USR-A1B2C)
        String userId = "USR-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        // OOP INHERITANCE/POLYMORPHISM: Storing a Client object in a User reference
        User newClient = new Client(userId, name, email, password);

        // Convert object to pipe-delimited string and append to file
        FileHandler.appendSingleLine(FileHandler.USERS_FILE, newClient.toFileString());

        // Redirect to login with success parameter
        return "redirect:/login?registered=true";
    }

    // ==========================================
    // 2. LOGIN & SESSION MANAGEMENT
    // ==========================================
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
            
        // Read all data from users.txt
        ArrayList<String> allUsers = FileHandler.readAllLines(FileHandler.USERS_FILE);
        
        for (String line : allUsers) {
            String[] parts = line.split("\\|");
            
            // Expected format: userId|name|email|password|role
            if (parts.length >= 5) {
                String storedEmail = parts[2];
                String storedPassword = parts[3];
                
                // Check credentials
                if (storedEmail.equalsIgnoreCase(email) && storedPassword.equals(password)) {
                    // Valid user -> Initialize HttpSession
                    session.setAttribute("userId", parts[0]);
                    session.setAttribute("userName", parts[1]);
                    session.setAttribute("userEmail", parts[2]);
                    session.setAttribute("userRole", parts[4]);
                    
                    return "redirect:/profile";
                }
            }
        }
        
        model.addAttribute("error", "Invalid email or password.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear session
        return "redirect:/login?logout=true";
    }

    // ==========================================
    // 3. PROFILE VIEW
    // ==========================================
    
    @GetMapping("/profile")
    public String showProfile(HttpSession session) {
        // Guard clause to prevent unauthenticated access
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        return "profile";
    }

    // ==========================================
    // 4. UPDATE PROFILE (ARRAYLIST MANIPULATION)
    // ==========================================
    
    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam("name") String newName,
            @RequestParam("password") String newPassword,
            HttpSession session,
            Model model) {
            
        String loggedUserId = (String) session.getAttribute("userId");
        if (loggedUserId == null) return "redirect:/login";

        // Read all existing records
        ArrayList<String> lines = FileHandler.readAllLines(FileHandler.USERS_FILE);
        ArrayList<String> updatedLines = new ArrayList<>();
        
        // Find and modify specific record
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 5 && parts[0].equals(loggedUserId)) {
                // If password field is empty, retain old password
                String finalPassword = newPassword.isEmpty() ? parts[3] : newPassword;
                
                // Reconstruct pipe-delimited string with updated values
                String updatedRecord = parts[0] + "|" + newName + "|" + parts[2] + "|" + finalPassword + "|" + parts[4];
                updatedLines.add(updatedRecord);
                
                // Update active session data
                session.setAttribute("userName", newName);
            } else {
                // Add un-modified records as they are
                updatedLines.add(line);
            }
        }
        
        // Overwrite the entire file with the updated ArrayList
        FileHandler.writeAllLines(FileHandler.USERS_FILE, updatedLines);
        
        model.addAttribute("success", "Profile updated successfully.");
        return "profile";
    }

    // ==========================================
    // 5. DELETE ACCOUNT (ARRAYLIST MANIPULATION)
    // ==========================================
    
    @PostMapping("/profile/delete")
    public String deleteProfile(HttpSession session) {
        String loggedUserId = (String) session.getAttribute("userId");
        if (loggedUserId == null) return "redirect:/login";

        // Read all existing records
        ArrayList<String> lines = FileHandler.readAllLines(FileHandler.USERS_FILE);
        ArrayList<String> updatedLines = new ArrayList<>();
        
        // Copy records, skipping the one to be deleted
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 5 && parts[0].equals(loggedUserId)) {
                continue; // Skip adding this record (Effectively deleting it)
            }
            updatedLines.add(line);
        }
        
        // Overwrite the file with the remaining records
        FileHandler.writeAllLines(FileHandler.USERS_FILE, updatedLines);
        
        // Destroy session and redirect
        session.invalidate();
        return "redirect:/register?deleted=true";
    }
}
