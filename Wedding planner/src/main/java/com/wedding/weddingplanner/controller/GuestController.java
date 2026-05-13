package com.wedding.weddingplanner.controller;

import com.wedding.weddingplanner.model.GuestList;
import com.wedding.util.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller for Guest List Management.
 */
@Controller
@RequestMapping("/guests")
public class GuestController {

    @GetMapping
    public String viewGuests(Model model) {
        ArrayList<GuestList> guests = getAllGuests();
        model.addAttribute("guests", guests);
        
        // Pass itineraries to the view for the Aggregation dropdown
        model.addAttribute("itineraries", ItineraryController.getAllItineraries());
        return "guestManager";
    }

    @PostMapping("/add")
    public String addGuest(@ModelAttribute GuestList guest) {
        try {
            // FILE I/O & EXCEPTIONS
            guest.setRsvpStatus("PENDING");
            if (guest.getGuestId() == null || guest.getGuestId().isEmpty()) {
                guest.setGuestId(FileHandler.generateId(FileHandler.GUESTS_FILE, "GST"));
            }
            FileHandler.appendSingleLine(FileHandler.GUESTS_FILE, guest.toFileString());
        } catch (Exception e) {
            System.err.println("Exception occurred while adding guest: " + e.getMessage());
        }
        return "redirect:/guests";
    }

    @PostMapping("/updateRsvp")
    public String updateRsvp(@RequestParam String guestId, @RequestParam String rsvpStatus) {
        try {
            // COLLECTIONS: Read ArrayList, mutate specific object state, and overwrite
            ArrayList<GuestList> guests = getAllGuests();
            for (int i = 0; i < guests.size(); i++) {
                if (guests.get(i).getGuestId().equals(guestId)) {
                    guests.get(i).setRsvpStatus(rsvpStatus); // Mutate RSVP
                    break;
                }
            }
            saveAllGuests(guests);
        } catch (Exception e) {
            System.err.println("Exception occurred updating RSVP: " + e.getMessage());
        }
        return "redirect:/guests";
    }

    /**
     * Helper Method: Loads all guests into an ArrayList.
     */
    public static ArrayList<GuestList> getAllGuests() {
        ArrayList<GuestList> guests = new ArrayList<>();
        try {
            ArrayList<String> lines = FileHandler.readAllLines(FileHandler.GUESTS_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    guests.add(new GuestList(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
        } catch (Exception e) {
            System.err.println("Exception occurred reading guests: " + e.getMessage());
        }
        return guests;
    }

    /**
     * Helper Method: Overwrites the text file with the updated ArrayList.
     */
    public static void saveAllGuests(ArrayList<GuestList> guests) {
        try {
            ArrayList<String> lines = new ArrayList<>();
            for (GuestList g : guests) {
                lines.add(g.toFileString());
            }
            FileHandler.writeAllLines(FileHandler.GUESTS_FILE, lines);
        } catch (Exception e) {
            System.err.println("Exception occurred writing guests: " + e.getMessage());
        }
    }
}
