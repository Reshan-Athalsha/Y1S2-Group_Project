package com.weddingplanner.util;

import com.weddingplanner.model.User;
import com.weddingplanner.model.AdminUser;
import com.weddingplanner.model.RegularUser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * UserFileManager -- Dedicated file I/O utility for the User Management component.
 *
 * This class handles ALL four CRUD file operations for users.txt:
 *   CREATE : saveUser(filePath, user)
 *   READ   : getAllUsers(filePath), findUserById(filePath, id)
 *   UPDATE : updateUser(filePath, updatedUser)
 *   DELETE : deleteUser(filePath, id)
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
public class UserFileManager {

    private static final String DELIMITER = "\\|";

    private UserFileManager() { }

    /**
     * Saves a new User to the end of users.txt (APPEND mode).
     */
    public static void saveUser(String filePath, User user) {
        ensureFileExists(filePath);
        String record = user.toFileString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(record);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads ALL users from users.txt.
     */
    public static List<User> getAllUsers(String filePath) {
        List<User> users = new ArrayList<>();
        if (!Files.exists(Paths.get(filePath))) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                User user = lineToUser(line);
                if (user != null) users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Finds ONE user by ID.
     */
    public static User findUserById(String filePath, int id) {
        for (User user : getAllUsers(filePath)) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    /**
     * Updates an existing user in users.txt by replacing their line.
     */
    public static boolean updateUser(String filePath, User updatedUser) {
        List<String> lines = readRawLines(filePath);
        boolean found = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(DELIMITER);
            if (parts.length > 0) {
                try {
                    int lineId = Integer.parseInt(parts[0].trim());
                    if (lineId == updatedUser.getId()) {
                        lines.set(i, updatedUser.toFileString());
                        found = true;
                        break;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        if (found) writeAllLines(filePath, lines);
        return found;
    }

    /**
     * Deletes the user with the given ID.
     */
    public static boolean deleteUser(String filePath, int id) {
        List<String> allLines  = readRawLines(filePath);
        List<String> keptLines = new ArrayList<>();
        boolean found = false;

        for (String line : allLines) {
            String[] parts = line.split(DELIMITER);
            try {
                int lineId = Integer.parseInt(parts[0].trim());
                if (lineId == id) {
                    found = true;
                    continue;
                }
            } catch (NumberFormatException ignored) {}
            keptLines.add(line);
        }

        if (found) writeAllLines(filePath, keptLines);
        return found;
    }

    public static int getNextId(String filePath) {
        List<String> lines = readRawLines(filePath);
        int maxId = 0;
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            if (parts.length > 0) {
                try {
                    int lineId = Integer.parseInt(parts[0].trim());
                    if (lineId > maxId) maxId = lineId;
                } catch (NumberFormatException ignored) {}
            }
        }
        return maxId + 1;
    }

    private static User lineToUser(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length < 7) return null;

        try {
            int    id          = Integer.parseInt(parts[0].trim());
            String firstName   = parts[1].trim();
            String lastName    = parts[2].trim();
            String email       = parts[3].trim();
            String password    = parts[4].trim();
            String role        = parts[5].trim();
            String createdDate = parts[6].trim();

            if ("admin".equalsIgnoreCase(role)) {
                return new AdminUser(id, firstName, lastName, email, password, createdDate, "standard");
            } else {
                return new RegularUser(id, firstName, lastName, email, password, createdDate, "", "Not set");
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static List<String> readRawLines(String filePath) {
        List<String> lines = new ArrayList<>();
        if (!Files.exists(Paths.get(filePath))) return lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) lines.add(line);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return lines;
    }

    private static void writeAllLines(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static void ensureFileExists(String filePath) {
        try {
            java.nio.file.Path path = Paths.get(filePath);
            if (!Files.exists(path.getParent())) Files.createDirectories(path.getParent());
            if (!Files.exists(path)) Files.createFile(path);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
