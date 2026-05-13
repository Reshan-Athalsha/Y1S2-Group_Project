package com.wedding.util;

import jakarta.servlet.http.HttpSession;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 *  1. STATIC KEYWORD — all methods are static; no instantiation
 *  2. SINGLE RESPONSIBILITY (SRP) — only manages session data
 * ============================================================
 *
 * SessionUtil provides helper methods to store and retrieve
 * commonly used attributes from the HTTP session.
 *
 * Used across all servlets to get the currently logged-in user.
 */
public class SessionUtil {

    // OOP: STATIC constants — shared session attribute keys
    public static final String SESSION_USER_ID    = "loggedUserId";
    public static final String SESSION_USER_NAME  = "loggedUserName";
    public static final String SESSION_USER_EMAIL = "loggedUserEmail";
    public static final String SESSION_USER_ROLE  = "loggedUserRole";

    // Prevent instantiation — utility class only
    private SessionUtil() {}

    /** Stores user info in the session after successful login */
    public static void setUserSession(HttpSession session,
                                      String userId,
                                      String userName,
                                      String email,
                                      String role) {
        session.setAttribute(SESSION_USER_ID,    userId);
        session.setAttribute(SESSION_USER_NAME,  userName);
        session.setAttribute(SESSION_USER_EMAIL, email);
        session.setAttribute(SESSION_USER_ROLE,  role);
    }

    /** Returns the logged-in user's ID from the session */
    public static String getLoggedUserId(HttpSession session) {
        Object id = session.getAttribute(SESSION_USER_ID);
        return (id != null) ? id.toString() : null;
    }

    /** Returns the logged-in user's role ("CLIENT", "VENDOR", "ADMIN") */
    public static String getLoggedUserRole(HttpSession session) {
        Object role = session.getAttribute(SESSION_USER_ROLE);
        return (role != null) ? role.toString() : null;
    }

    /** Checks whether a user is currently logged in */
    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(SESSION_USER_ID) != null;
    }

    /** Checks if the logged-in user is an admin */
    public static boolean isAdmin(HttpSession session) {
        return "ADMIN".equalsIgnoreCase(getLoggedUserRole(session));
    }

    /** Invalidates the session (logout) */
    public static void clearSession(HttpSession session) {
        session.invalidate();
    }
}
