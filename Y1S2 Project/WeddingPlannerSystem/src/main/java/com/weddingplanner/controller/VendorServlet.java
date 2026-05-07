package com.weddingplanner.controller;

// ============================================================================
//  VendorServlet.java -- HTTP Controller for Component 02
//  Component 02 -- Vendor Management
//  Author: Team -- Wedding Planner System
// ============================================================================

import com.weddingplanner.model.Vendor;
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
 * VendorServlet -- HTTP Controller for Component 02: Vendor Management.
 *
 * URL Mapping: /vendors
 */
@WebServlet("/vendors")
public class VendorServlet extends HttpServlet {

    private static final String DATA_FILE = "/WEB-INF/data/vendors.txt";

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String getDataFilePath() {
        return getServletContext().getRealPath(DATA_FILE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/WEB-INF/views/vendor/vendor-form.jsp")
                   .forward(request, response);

        } else if ("edit".equalsIgnoreCase(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam.trim());
                    String line = FileStorageUtil.findById(getDataFilePath(), id);
                    if (line != null) {
                        Vendor vendor = new Vendor();
                        vendor.fromFileString(line);
                        request.setAttribute("vendor", vendor);
                        request.getRequestDispatcher("/WEB-INF/views/vendor/vendor-edit.jsp")
                               .forward(request, response);
                        return;
                    }
                } catch (NumberFormatException ignored) { }
            }
            response.sendRedirect(request.getContextPath() + "/vendors");

        } else if ("delete".equalsIgnoreCase(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam.trim());
                    FileStorageUtil.deleteById(getDataFilePath(), id);
                } catch (NumberFormatException ignored) { }
            }
            response.sendRedirect(request.getContextPath() + "/vendors");

        } else {
            List<Vendor> vendors = getAllVendors();
            request.setAttribute("vendors", vendors);
            request.getRequestDispatcher("/WEB-INF/views/vendor/vendor-list.jsp")
                   .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");
        String filePath = getDataFilePath();

        if ("update".equalsIgnoreCase(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr.trim());
                    String line = FileStorageUtil.findById(filePath, id);
                    if (line != null) {
                        Vendor vendor = new Vendor();
                        vendor.fromFileString(line);

                        // Update fields
                        vendor.setBusinessName(request.getParameter("businessName"));
                        vendor.setCategory(request.getParameter("category"));
                        vendor.setContactEmail(request.getParameter("contactEmail"));
                        vendor.setPhone(request.getParameter("phone"));
                        vendor.setLocation(request.getParameter("location"));
                        vendor.setDescription(request.getParameter("description"));
                        
                        String ratingStr = request.getParameter("rating");
                        vendor.setRating(parseDoubleSafe(ratingStr, 0.0));

                        FileStorageUtil.updateById(filePath, id, vendor.toFileString());
                    }
                } catch (NumberFormatException ignored) { }
            }
        } else {
            // CREATE new vendor
            int nextId = FileStorageUtil.getNextId(filePath);
            String now = LocalDateTime.now().format(DATE_FMT);

            String ratingStr = request.getParameter("rating");
            double rating = parseDoubleSafe(ratingStr, 0.0);

            Vendor vendor = new Vendor(
                    nextId,
                    request.getParameter("businessName"),
                    request.getParameter("category"),
                    request.getParameter("contactEmail"),
                    request.getParameter("phone"),
                    request.getParameter("location"),
                    request.getParameter("description"),
                    rating,
                    now
            );

            FileStorageUtil.appendLine(filePath, vendor.toFileString());
        }

        response.sendRedirect(request.getContextPath() + "/vendors");
    }

    private List<Vendor> getAllVendors() {
        List<String> lines = FileStorageUtil.readAllLines(getDataFilePath());
        List<Vendor> vendors = new ArrayList<>();
        for (String line : lines) {
            Vendor v = new Vendor();
            v.fromFileString(line);
            vendors.add(v);
        }
        return vendors;
    }

    private double parseDoubleSafe(String value, double defaultValue) {
        if (value == null || value.trim().isEmpty()) return defaultValue;
        try { return Double.parseDouble(value.trim()); }
        catch (NumberFormatException e) { return defaultValue; }
    }
}
