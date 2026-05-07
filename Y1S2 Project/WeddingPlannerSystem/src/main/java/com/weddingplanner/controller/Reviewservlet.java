package com.weddingplanner.controller;

// ============================================================================
//  Reviewservlet.java -- Placeholder Controller for Component 05
//  Component 05 -- Reviews and Rating System
//  Author: Team -- Wedding Planner System
// ============================================================================

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Placeholder servlet for Component 05: Reviews and Rating System.
 * Full CRUD implementation will be added in a subsequent step.
 *
 * URL Mapping: /reviews
 *
 * @author  Team -- Wedding Planner System
 * @version 1.0 (placeholder)
 */
@WebServlet("/reviews")
public class Reviewservlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // Forward to the review placeholder page
        req.getRequestDispatcher("/WEB-INF/views/review/review-list.jsp")
           .forward(req, res);
    }
}
