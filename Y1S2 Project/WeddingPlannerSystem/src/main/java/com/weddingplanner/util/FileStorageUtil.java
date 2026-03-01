package com.weddingplanner.util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║            FileStorageUtil — Shared File I/O Utility            ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  All team members should use this class to read / write data.   ║
 * ║  Data files live in:  WEB-INF/data/*.txt                       ║
 * ║                                                                 ║
 * ║  CONVENTION:                                                    ║
 * ║    Each line in a .txt file = one record.                       ║
 * ║    Fields inside a line are separated by  |  (pipe).            ║
 * ║    Example line:  1|John|Doe|john@mail.com|password123          ║
 * ╚══════════════════════════════════════════════════════════════════╝
 *
 * HOW TO USE (copy this into your group chat):
 * ─────────────────────────────────────────────
 * 1. Get the absolute path to your data file inside your Servlet:
 *
 *        String path = getServletContext().getRealPath("/WEB-INF/data/users.txt");
 *
 * 2. READ all lines:
 *        List<String> lines = FileStorageUtil.readAllLines(path);
 *
 * 3. APPEND a new record:
 *        FileStorageUtil.appendLine(path, "4|Jane|Smith|jane@mail.com|pass");
 *
 * 4. OVERWRITE the whole file (for updates / deletes):
 *        FileStorageUtil.writeAllLines(path, updatedLinesList);
 *
 * 5. GET NEXT ID (auto-increment helper):
 *        int nextId = FileStorageUtil.getNextId(path);
 *
 * @author  Team — Wedding Planner System
 * @version 1.0
 */
public final class FileStorageUtil {

    /** Delimiter used between fields in every .txt record. */
    public static final String DELIMITER = "\\|";       // for splitting
    public static final String SEPARATOR = "|";          // for joining

    // Prevent instantiation — all methods are static.
    private FileStorageUtil() { }

    // ───────────────────────── READ ──────────────────────────

    /**
     * Reads every line from the given file and returns them as a List.
     * Returns an empty list if the file does not exist yet.
     *
     * @param filePath absolute path to the .txt file
     * @return list of lines (never null)
     */
    public static List<String> readAllLines(String filePath) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            return lines;           // file not created yet — return empty
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("[FileStorageUtil] Error reading file: " + filePath);
            e.printStackTrace();
        }
        return lines;
    }

    // ───────────────────────── WRITE ─────────────────────────

    /**
     * Overwrites the file with the given list of lines.
     * Creates the file (and parent directories) if they don't exist.
     * Use this for UPDATE and DELETE operations.
     *
     * @param filePath absolute path to the .txt file
     * @param lines    complete list of lines to write
     */
    public static void writeAllLines(String filePath, List<String> lines) {
        ensureFileExists(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("[FileStorageUtil] Error writing file: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Appends a single line to the end of the file.
     * Creates the file if it doesn't exist.
     * Use this for CREATE / INSERT operations.
     *
     * @param filePath absolute path to the .txt file
     * @param line     the record to append
     */
    public static void appendLine(String filePath, String line) {
        ensureFileExists(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("[FileStorageUtil] Error appending to file: " + filePath);
            e.printStackTrace();
        }
    }

    // ───────────────────────── HELPERS ────────────────────────

    /**
     * Returns the next auto-incremented integer ID by reading the last
     * record's first field (assumed to be the numeric ID).
     * Returns 1 if the file is empty or doesn't exist.
     *
     * @param filePath absolute path to the .txt file
     * @return next available ID
     */
    public static int getNextId(String filePath) {
        List<String> lines = readAllLines(filePath);
        if (lines.isEmpty()) {
            return 1;
        }
        // Scan for the highest existing ID (handles out-of-order or deleted rows)
        int maxId = 0;
        for (String line : lines) {
            try {
                String[] parts = line.split(DELIMITER);
                int id = Integer.parseInt(parts[0].trim());
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException ignored) {
                // skip malformed lines
            }
        }
        return maxId + 1;
    }

    /**
     * Searches the file for a line whose first field (ID) matches.
     *
     * @param filePath absolute path to the .txt file
     * @param id       the ID to look for
     * @return the matching line, or null if not found
     */
    public static String findById(String filePath, int id) {
        List<String> lines = readAllLines(filePath);
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            if (parts.length > 0) {
                try {
                    if (Integer.parseInt(parts[0].trim()) == id) {
                        return line;
                    }
                } catch (NumberFormatException ignored) { }
            }
        }
        return null;
    }

    /**
     * Deletes the line whose first field (ID) matches the given id.
     *
     * @param filePath absolute path to the .txt file
     * @param id       the ID of the record to remove
     * @return true if a line was removed, false otherwise
     */
    public static boolean deleteById(String filePath, int id) {
        List<String> lines = readAllLines(filePath);
        List<String> updated = new ArrayList<>();
        boolean removed = false;
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            try {
                if (Integer.parseInt(parts[0].trim()) == id) {
                    removed = true;
                    continue;       // skip this line → effectively delete it
                }
            } catch (NumberFormatException ignored) { }
            updated.add(line);
        }
        if (removed) {
            writeAllLines(filePath, updated);
        }
        return removed;
    }

    /**
     * Replaces (updates) the line whose first field matches the given id.
     *
     * @param filePath    absolute path to the .txt file
     * @param id          the ID of the record to update
     * @param newLine     the replacement line
     * @return true if the record was found and replaced
     */
    public static boolean updateById(String filePath, int id, String newLine) {
        List<String> lines = readAllLines(filePath);
        boolean updated = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(DELIMITER);
            try {
                if (Integer.parseInt(parts[0].trim()) == id) {
                    lines.set(i, newLine);
                    updated = true;
                    break;
                }
            } catch (NumberFormatException ignored) { }
        }
        if (updated) {
            writeAllLines(filePath, lines);
        }
        return updated;
    }

    // ───────────────────────── INTERNAL ───────────────────────

    /**
     * Creates the file and any missing parent directories.
     */
    private static void ensureFileExists(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("[FileStorageUtil] Error creating file: " + filePath);
            e.printStackTrace();
        }
    }
}
