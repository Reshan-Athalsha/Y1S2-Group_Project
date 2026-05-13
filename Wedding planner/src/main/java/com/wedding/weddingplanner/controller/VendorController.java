package com.wedding.weddingplanner.controller;

import com.wedding.weddingplanner.model.Vendor;
import com.wedding.util.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller for Vendor Management.
 * Handles HTTP requests for Vendor CRUD operations.
 */
@Controller
@RequestMapping("/vendors")
public class VendorController {

    // 1. VIEW ALL VENDORS
    @GetMapping
    public String viewVendors(Model model) {
        ArrayList<Vendor> vendors = getAllVendors();
        model.addAttribute("vendors", vendors);
        return "vendorDashboard";
    }

    // 2A. SHOW ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "addVendor";
    }

    // 2B. PROCESS ADD VENDOR
    @PostMapping("/add")
    public String addVendor(@ModelAttribute Vendor vendor) {
        try {
            // FILE I/O & EXCEPTIONS: Logic wrapped in try-catch
            vendor.setRole("VENDOR");
            if (vendor.getUserId() == null || vendor.getUserId().isEmpty()) {
                vendor.setUserId(FileHandler.generateId(FileHandler.VENDORS_FILE, "VND"));
            }
            FileHandler.appendSingleLine(FileHandler.VENDORS_FILE, vendor.toFileString());
        } catch (Exception e) {
            System.err.println("Exception occurred while adding vendor: " + e.getMessage());
        }
        return "redirect:/vendors";
    }

    // 3A. SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        ArrayList<Vendor> vendors = getAllVendors();
        for (Vendor v : vendors) {
            if (v.getUserId().equals(id)) {
                model.addAttribute("vendor", v);
                return "editVendor";
            }
        }
        return "redirect:/vendors";
    }

    // 3B. PROCESS EDIT VENDOR
    @PostMapping("/edit")
    public String updateVendor(@ModelAttribute Vendor updatedVendor) {
        try {
            // FILE I/O & EXCEPTIONS: Wrapped in try-catch block
            // COLLECTIONS: Read all lines into ArrayList<Vendor>, modify target, overwrite file
            ArrayList<Vendor> vendors = getAllVendors();
            for (int i = 0; i < vendors.size(); i++) {
                if (vendors.get(i).getUserId().equals(updatedVendor.getUserId())) {
                    updatedVendor.setRole("VENDOR");
                    vendors.set(i, updatedVendor); // Modify object in memory
                    break;
                }
            }
            saveAllVendors(vendors); // Overwrite the file
        } catch (Exception e) {
            System.err.println("Exception occurred while updating vendor: " + e.getMessage());
        }
        return "redirect:/vendors";
    }

    // 4. PROCESS DELETE VENDOR
    @GetMapping("/delete/{id}")
    public String deleteVendor(@PathVariable String id) {
        try {
            // FILE I/O & EXCEPTIONS: Wrapped in try-catch block
            // COLLECTIONS: Modify ArrayList<Vendor> in memory and overwrite
            ArrayList<Vendor> vendors = getAllVendors();
            vendors.removeIf(v -> v.getUserId().equals(id)); // Remove object in memory
            saveAllVendors(vendors); // Overwrite the file
        } catch (Exception e) {
            System.err.println("Exception occurred while deleting vendor: " + e.getMessage());
        }
        return "redirect:/vendors";
    }

    /**
     * Helper Method to load all vendors from the text file into an ArrayList.
     */
    public static ArrayList<Vendor> getAllVendors() {
        ArrayList<Vendor> vendors = new ArrayList<>();
        try {
            // FILE I/O & EXCEPTIONS
            ArrayList<String> lines = FileHandler.readAllLines(FileHandler.VENDORS_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 9) {
                    Vendor v = new Vendor(parts[0], parts[1], parts[2], parts[3], parts[4], 
                                          parts[5], parts[6], Double.parseDouble(parts[7]), parts[8]);
                    vendors.add(v);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception occurred while reading vendors: " + e.getMessage());
        }
        return vendors;
    }

    /**
     * Helper Method to write an ArrayList of Vendors back to the text file.
     */
    public static void saveAllVendors(ArrayList<Vendor> vendors) {
        try {
            // FILE I/O & EXCEPTIONS
            ArrayList<String> lines = new ArrayList<>();
            for (Vendor v : vendors) {
                lines.add(v.toFileString());
            }
            FileHandler.writeAllLines(FileHandler.VENDORS_FILE, lines);
        } catch (Exception e) {
            System.err.println("Exception occurred while writing vendors: " + e.getMessage());
        }
    }
}
