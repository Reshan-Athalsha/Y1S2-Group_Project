package com.weddingplanner.controller;

// ============================================================================
//  BookingServlet.java -- HTTP Controller for Component 03
//  Component 03 -- Booking & Payment Management
//  Author: Team -- Wedding Planner System
// ============================================================================

import com.weddingplanner.model.Booking;
import com.weddingplanner.util.FileStorageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingServlet -- HTTP Controller for Component 03: Booking Management.
 *
 * URL Mapping: /bookings
 *
 * ROUTING TABLE (controlled by the 'action' query parameter):
 *   GET  /bookings                     --> list all bookings (default)
 *   GET  /bookings?action=add          --> show the "create booking" form
 *   GET  /bookings?action=delete&id=X  --> delete booking X, then redirect
 *   POST /bookings                     --> CREATE a new booking (form submit)
 *
 * OOP CONCEPT -- This is the CONTROLLER in the MVC pattern:
 *   Model      = Booking.java (the data)
 *   View       = booking-list.jsp / booking-form.jsp (the display)
 *   Controller = THIS class (decides what data to load and which JSP to show)
 *
 * @author  Team -- Wedding Planner System
 * @version 1.0
 */
@WebServlet("/bookings")           // <-- maps ALL requests to /bookings here
public class BookingServlet extends HttpServlet {

    // Path to the data file, relative to the WAR root (src/main/webapp)
    private static final String DATA_FILE = "/WEB-INF/data/bookings.txt";

    // Date format used when recording the booking creation time
    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Converts the relative data path to the absolute filesystem path.
     * Jetty resolves this to the physical file location on disk at runtime.
     */
    private String getDataFilePath() {
        return getServletContext().getRealPath(DATA_FILE);
    }

    // =========================================================================
    //   doGet -- handles GET /bookings
    // =========================================================================

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            // Show the blank booking creation form
            request.getRequestDispatcher("/WEB-INF/views/booking/booking-form.jsp")
                   .forward(request, response);

        } else if ("delete".equalsIgnoreCase(action)) {
            // DELETE the booking with the given id, then redirect to the list
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam.trim());
                    FileStorageUtil.deleteById(getDataFilePath(), id);
                } catch (NumberFormatException ignored) { }
            }
            response.sendRedirect(request.getContextPath() + "/bookings");

        } else {
            // Default: READ all bookings and display the list
            List<Booking> bookings = getAllBookings();
            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("/WEB-INF/views/booking/booking-list.jsp")
                   .forward(request, response);
        }
    }

    // =========================================================================
    //   doPost -- handles POST /bookings (form submission to CREATE a booking)
    // =========================================================================

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String filePath = getDataFilePath();

        // Read form fields submitted by booking-form.jsp
        String userIdStr    = request.getParameter("userId");
        String vendorIdStr  = request.getParameter("vendorId");
        String eventDate    = request.getParameter("eventDate");
        String eventType    = request.getParameter("eventType");
        String guestCountStr= request.getParameter("guestCount");
        String notes        = request.getParameter("notes");

        // Parse numeric fields safely with fallback defaults
        int userId     = parseIntSafe(userIdStr,     0);
        int vendorId   = parseIntSafe(vendorIdStr,   0);
        int guestCount = parseIntSafe(guestCountStr, 0);

        // Auto-generate a unique ID and record the current timestamp
        int    nextId = FileStorageUtil.getNextId(filePath);
        String now    = LocalDateTime.now().format(DATE_FMT);

        // Build the Booking object (ENCAPSULATION: constructor sets private fields)
        Booking booking = new Booking(
                nextId, userId, vendorId,
                eventDate  != null ? eventDate  : "",
                eventType  != null ? eventType  : "",
                guestCount,
                "Pending",          // new bookings always start as Pending
                notes      != null ? notes      : "",
                now
        );

        // Persist to bookings.txt (append one pipe-delimited line)
        FileStorageUtil.appendLine(filePath, booking.toFileString());

        // POST-Redirect-GET: redirect so that browser refresh won't re-submit
        response.sendRedirect(request.getContextPath() + "/bookings");
    }

    // =========================================================================
    //   Private Helpers
    // =========================================================================

    /**
     * Reads all lines from bookings.txt and converts each into a Booking object.
     * Returns an empty list if the file does not exist yet.
     */
    private List<Booking> getAllBookings() {
        List<String>  lines    = FileStorageUtil.readAllLines(getDataFilePath());
        List<Booking> bookings = new ArrayList<>();
        for (String line : lines) {
            Booking b = new Booking();
            b.fromFileString(line);   // ABSTRACTION: fromFileString() contract
            bookings.add(b);
        }
        return bookings;
    }

    /**
     * Parses a String to int safely; returns defaultValue on failure.
     */
    private int parseIntSafe(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) return defaultValue;
        try { return Integer.parseInt(value.trim()); }
        catch (NumberFormatException e) { return defaultValue; }
    }
}
