package com.weddingplanner.controller;

import com.weddingplanner.model.User;
import com.weddingplanner.util.FileStorageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample Servlet demonstrating how to build a controller.
 *
 * URL mapping: /users
 *   GET  /users           → list all users
 *   GET  /users?action=add → show the "add user" form
 *   POST /users           → create a new user
 *
 * ── TEAM NOTE ────────────────────────────────────────────────
 *   Use this as a TEMPLATE when you build VendorServlet,
 *   BookingServlet, ReviewServlet, etc.
 * ─────────────────────────────────────────────────────────────
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {

    /** Relative path inside the WAR to the data file. */
    private static final String DATA_FILE = "/WEB-INF/data/users.txt";

    /** Get the absolute path on disk for the data file. */
    private String getDataFilePath() {
        return getServletContext().getRealPath(DATA_FILE);
    }

    // ──────────────────── GET ────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            // Forward to the "add user" form
            request.getRequestDispatcher("/WEB-INF/views/user/user-form.jsp")
                   .forward(request, response);
            return;
        }

        // Default: list all users
        List<User> users = getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/user/user-list.jsp")
               .forward(request, response);
    }

    // ──────────────────── POST ───────────────────────────────

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String filePath = getDataFilePath();

        // Read form parameters
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String email     = request.getParameter("email");
        String password  = request.getParameter("password");
        String role      = request.getParameter("role");

        // Auto-generate ID and timestamp
        int nextId = FileStorageUtil.getNextId(filePath);
        String now = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Build entity and persist
        User user = new User(nextId, firstName, lastName,
                             email, password,
                             (role != null ? role : "customer"), now);

        FileStorageUtil.appendLine(filePath, user.toFileString());

        // Redirect back to the list (POST-Redirect-GET pattern)
        response.sendRedirect(request.getContextPath() + "/users");
    }

    // ──────────────────── Helper ─────────────────────────────

    /**
     * Reads every line from users.txt and converts to User objects.
     */
    private List<User> getAllUsers() {
        List<String> lines = FileStorageUtil.readAllLines(getDataFilePath());
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            User u = new User();
            u.fromFileString(line);
            users.add(u);
        }
        return users;
    }
}
