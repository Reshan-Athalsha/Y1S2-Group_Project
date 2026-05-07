package com.weddingplanner.controller;

import com.weddingplanner.model.AdminUser;
import com.weddingplanner.model.RegularUser;
import com.weddingplanner.model.User;
import com.weddingplanner.util.UserFileManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * UserServlet -- HTTP Controller for Component 01: User Management.
 *
 * URL Mapping: /users
 *
 * ROUTING TABLE:
 *   GET  /users                    --> list all users (default)
 *   GET  /users?action=add         --> show registration form
 *   GET  /users?action=edit&id=3   --> show edit form pre-filled
 *   GET  /users?action=delete&id=3 --> delete user 3, then redirect
 *   POST /users                    --> CREATE a new user
 *   POST /users?action=update      --> UPDATE an existing user
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private static final String DATA_FILE = "/WEB-INF/data/users.txt";
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String getDataFilePath() {
        return getServletContext().getRealPath(DATA_FILE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            showAddForm(request, response);
        } else if ("edit".equalsIgnoreCase(action)) {
            showEditForm(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            deleteUser(request, response);
        } else {
            listAllUsers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("update".equalsIgnoreCase(action)) {
            updateUser(request, response);
        } else {
            createUser(request, response);
        }
    }

    private void listAllUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = UserFileManager.getAllUsers(getDataFilePath());
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/user/user-list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user/user-form.jsp").forward(request, response);
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String email     = request.getParameter("email");
        String password  = request.getParameter("password");
        String role      = request.getParameter("role");

        String filePath = getDataFilePath();
        int    nextId   = UserFileManager.getNextId(filePath);
        String now      = LocalDateTime.now().format(DATE_FMT);

        // POLYMORPHISM: create the proper subclass based on role.
        User newUser;
        if ("admin".equalsIgnoreCase(role)) {
            newUser = new AdminUser(nextId, firstName, lastName, email, password, now, "standard");
        } else {
            newUser = new RegularUser(nextId, firstName, lastName, email, password, now, "", "Not set");
        }

        UserFileManager.saveUser(filePath, newUser);
        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/users");
            return;
        }

        try {
            int id = Integer.parseInt(idParam.trim());
            User user = UserFileManager.findUserById(getDataFilePath(), id);

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/users");
                return;
            }

            request.setAttribute("user", user);
            request.setAttribute("editMode", true);
            request.getRequestDispatcher("/WEB-INF/views/user/user-edit.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/users");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idParam   = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String email     = request.getParameter("email");
        String password  = request.getParameter("password");
        String role      = request.getParameter("role");

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/users");
            return;
        }

        try {
            int id = Integer.parseInt(idParam.trim());
            String filePath = getDataFilePath();
            User existingUser = UserFileManager.findUserById(filePath, id);

            if (existingUser == null) {
                response.sendRedirect(request.getContextPath() + "/users");
                return;
            }

            User updatedUser;
            if ("admin".equalsIgnoreCase(role)) {
                updatedUser = new AdminUser(id, firstName, lastName, email, password,
                                            existingUser.getCreatedDate(), "standard");
            } else {
                updatedUser = new RegularUser(id, firstName, lastName, email, password,
                                              existingUser.getCreatedDate(), "", "Not set");
            }

            UserFileManager.updateUser(filePath, updatedUser);
            response.sendRedirect(request.getContextPath() + "/users");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/users");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/users");
            return;
        }

        try {
            int id = Integer.parseInt(idParam.trim());
            UserFileManager.deleteUser(getDataFilePath(), id);
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/users");
        }
    }
}
