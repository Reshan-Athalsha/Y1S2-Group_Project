package com.wedding.weddingplanner.model;

import com.wedding.interfaces.Displayable;

/**
 * ============================================================
 * ServiceCategory — Component 2: Vendor Management (Chathum)
 * ============================================================
 * OOP PRINCIPLES:
 *  - ENCAPSULATION  : All fields private with public getters/setters.
 *  - STATIC KEYWORD : predefinedCategories() is a static utility
 *                     method returning available service types.
 *  - ABSTRACTION    : Implements Displayable interface.
 *  - METHOD OVERLOADING: Two constructors.
 * ============================================================
 *
 * Represents a wedding service category (e.g. Photography,
 * Catering). Each Vendor is associated with one ServiceCategory.
 *
 * @author  Chathum
 * @version 1.0
 */
public class ServiceCategory implements Displayable {

    // OOP: ENCAPSULATION — private fields
    private String categoryId;
    private String categoryName;
    private String description;
    private String iconEmoji;

    // OOP: STATIC — fixed list of available categories
    private static final String[] CATEGORY_NAMES = {
        "Photography", "Videography", "Catering", "Florals",
        "Decoration", "Music & DJ", "Jewellery", "Bridal Wear",
        "Hair & Makeup", "Venue", "Transport", "Cake & Desserts"
    };

    // =========================================================
    // CONSTRUCTORS — OOP: METHOD OVERLOADING
    // =========================================================

    /** Default no-arg constructor */
    public ServiceCategory() {}

    /** Full parameterised constructor */
    public ServiceCategory(String categoryId, String categoryName,
                           String description, String iconEmoji) {
        this.categoryId   = categoryId;
        this.categoryName = categoryName;
        this.description  = description;
        this.iconEmoji    = iconEmoji;
    }

    // =========================================================
    // OOP: STATIC — utility method; no instance needed
    // =========================================================

    /**
     * Returns the full array of predefined service category names.
     * Used to populate dropdowns in JSPs.
     *
     * @return  String array of category names
     */
    public static String[] predefinedCategories() {
        return CATEGORY_NAMES;
    }

    /**
     * Checks if the given name is a valid predefined category.
     * OOP: STATIC utility method.
     *
     * @param name  Category name to validate
     * @return      true if valid, false otherwise
     */
    public static boolean isValidCategory(String name) {
        for (String cat : CATEGORY_NAMES) {
            if (cat.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    // OOP: POLYMORPHISM — implements Displayable.displayDetails()
    @Override
    public String displayDetails() {
        return iconEmoji + " " + categoryName + " — " + description;
    }

    // OOP: ENCAPSULATION — Getters and Setters
    public String getCategoryId()   { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getDescription()  { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIconEmoji()    { return iconEmoji; }
    public void setIconEmoji(String iconEmoji) { this.iconEmoji = iconEmoji; }

    @Override
    public String toString() {
        return "ServiceCategory{id='" + categoryId + "', name='" + categoryName + "'}";
    }
}
