package com.wedding.weddingplanner.controller;

import com.wedding.weddingplanner.model.Itinerary;
import com.wedding.util.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller for Itinerary Management.
 */
@Controller
@RequestMapping("/itinerary")
public class ItineraryController {

    @GetMapping
    public String viewItineraries(Model model) {
        ArrayList<Itinerary> itineraries = getAllItineraries();
        model.addAttribute("itineraries", itineraries);
        return "itineraryBuilder";
    }

    @PostMapping("/add")
    public String addItinerary(@ModelAttribute Itinerary itinerary) {
        try {
            // FILE I/O & EXCEPTIONS: Wrapped in try-catch block
            if (itinerary.getItineraryId() == null || itinerary.getItineraryId().isEmpty()) {
                itinerary.setItineraryId(FileHandler.generateId(FileHandler.PLANS_FILE, "ITN"));
            }
            FileHandler.appendSingleLine(FileHandler.PLANS_FILE, itinerary.toFileString());
        } catch (Exception e) {
            System.err.println("Exception occurred while adding itinerary: " + e.getMessage());
        }
        return "redirect:/itinerary";
    }

    @GetMapping("/delete/{id}")
    public String deleteItinerary(@PathVariable String id) {
        try {
            // COLLECTIONS: Modify ArrayList<Itinerary> in memory and overwrite
            ArrayList<Itinerary> itineraries = getAllItineraries();
            itineraries.removeIf(i -> i.getItineraryId().equals(id));
            saveAllItineraries(itineraries);
        } catch (Exception e) {
            System.err.println("Exception occurred while deleting itinerary: " + e.getMessage());
        }
        return "redirect:/itinerary";
    }

    /**
     * Helper Method: Loads all itineraries into an ArrayList.
     */
    public static ArrayList<Itinerary> getAllItineraries() {
        ArrayList<Itinerary> itineraries = new ArrayList<>();
        try {
            ArrayList<String> lines = FileHandler.readAllLines(FileHandler.PLANS_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    itineraries.add(new Itinerary(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } catch (Exception e) {
            System.err.println("Exception occurred reading itineraries: " + e.getMessage());
        }
        return itineraries;
    }

    /**
     * Helper Method: Overwrites the text file with the updated ArrayList.
     */
    public static void saveAllItineraries(ArrayList<Itinerary> itineraries) {
        try {
            ArrayList<String> lines = new ArrayList<>();
            for (Itinerary i : itineraries) {
                lines.add(i.toFileString());
            }
            FileHandler.writeAllLines(FileHandler.PLANS_FILE, lines);
        } catch (Exception e) {
            System.err.println("Exception occurred writing itineraries: " + e.getMessage());
        }
    }
}
