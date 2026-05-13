package com.wedding.weddingplanner.controller;

import com.wedding.weddingplanner.model.Review;
import com.wedding.weddingplanner.model.Vendor;
import com.wedding.util.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller for Reviews and Rating Management.
 */
@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @GetMapping
    public String viewReviews(Model model) {
        ArrayList<Review> reviews = getAllReviews();
        model.addAttribute("reviews", reviews);
        return "vendorRatings";
    }

    @GetMapping("/add")
    public String showReviewForm(Model model) {
        model.addAttribute("review", new Review());
        
        // Fetch Vendors for the dropdown utilizing the VendorController logic
        model.addAttribute("vendors", VendorController.getAllVendors());
        
        // Fetch Users manually from USERS_FILE for the dropdown
        ArrayList<String> userIds = new ArrayList<>();
        try {
            // FILE I/O & EXCEPTIONS
            ArrayList<String> userLines = FileHandler.readAllLines(FileHandler.USERS_FILE);
            for (String line : userLines) {
                String[] parts = line.split("\\|");
                if (parts.length > 0) {
                    userIds.add(parts[0]); // Extracting the user ID
                }
            }
        } catch (Exception e) {
            System.err.println("Exception occurred reading users: " + e.getMessage());
        }
        model.addAttribute("userIds", userIds);

        return "reviewForm";
    }

    @PostMapping("/add")
    public String addReview(@ModelAttribute Review review) {
        try {
            // FILE I/O & EXCEPTIONS
            if (review.getReviewId() == null || review.getReviewId().isEmpty()) {
                review.setReviewId(FileHandler.generateId(FileHandler.REVIEWS_FILE, "REV"));
            }
            // Auto-assign the current date if blank
            if (review.getDate() == null || review.getDate().isEmpty()) {
                review.setDate(java.time.LocalDate.now().toString());
            }
            FileHandler.appendSingleLine(FileHandler.REVIEWS_FILE, review.toFileString());
        } catch (Exception e) {
            System.err.println("Exception occurred while adding review: " + e.getMessage());
        }
        return "redirect:/reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id) {
        try {
            // COLLECTIONS: Modify ArrayList in memory and overwrite
            ArrayList<Review> reviews = getAllReviews();
            reviews.removeIf(r -> r.getReviewId().equals(id));
            saveAllReviews(reviews);
        } catch (Exception e) {
            System.err.println("Exception occurred while deleting review: " + e.getMessage());
        }
        return "redirect:/reviews";
    }

    /**
     * Helper Method: Loads all reviews into an ArrayList.
     */
    public static ArrayList<Review> getAllReviews() {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            // FILE I/O & EXCEPTIONS
            ArrayList<String> lines = FileHandler.readAllLines(FileHandler.REVIEWS_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    reviews.add(new Review(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], parts[5]));
                }
            }
        } catch (Exception e) {
            System.err.println("Exception occurred reading reviews: " + e.getMessage());
        }
        return reviews;
    }

    /**
     * Helper Method: Overwrites the text file with the updated ArrayList.
     */
    public static void saveAllReviews(ArrayList<Review> reviews) {
        try {
            // FILE I/O & EXCEPTIONS
            ArrayList<String> lines = new ArrayList<>();
            for (Review r : reviews) {
                lines.add(r.toFileString());
            }
            FileHandler.writeAllLines(FileHandler.REVIEWS_FILE, lines);
        } catch (Exception e) {
            System.err.println("Exception occurred writing reviews: " + e.getMessage());
        }
    }
}
