package com.wedding.util;

import com.wedding.interfaces.FileOperations;

import java.io.*;
import java.util.ArrayList;

/**
 * ============================================================
 * OOP PRINCIPLES DEMONSTRATED:
 *  1. ABSTRACTION    — implements FileOperations interface
 *  2. STATIC KEYWORD — all methods are static utility methods,
 *                      no instantiation needed (Utility class)
 *  3. EXCEPTION HANDLING — every file operation uses
 *                      try-catch-finally blocks
 *  4. SINGLE RESPONSIBILITY (SRP) — this class ONLY handles
 *                      raw file I/O; no business logic here
 * ============================================================
 *
 * FileHandler is the CENTRAL utility class for all text-file
 * read/write operations in the Wedding Planner system.
 *
 * All data is stored as pipe-delimited lines in .txt files:
 *   field1|field2|field3|...
 *
 * This class is used by every component:
 *   - Component 1 (users.txt)
 *   - Component 2 (vendors.txt)
 *   - Component 3 (bookings.txt, payments.txt)
 *   - Component 4 (plans.txt, guests.txt)
 *   - Component 5 (reviews.txt)
 *   - Component 6 (Admin reads all files)
 *
 * @author  Team Wedding Planner
 * @version 1.0
 */
public class FileHandler implements FileOperations {

    // =========================================================
    // OOP: STATIC keyword — shared constants across ALL instances
    // These paths are relative to the webapp root (set by
    // each servlet using ServletContext.getRealPath())
    // =========================================================

    /** Delimiter used to separate fields within a record line */
    public static final String DELIMITER = "|";

    /** Regex-safe version of the delimiter for String.split() */
    public static final String DELIMITER_REGEX = "\\|";

    /** File name constants — used by all servlets/components */
    public static final String USERS_FILE    = "data/users.txt";
    public static final String VENDORS_FILE  = "data/vendors.txt";
    public static final String BOOKINGS_FILE = "data/bookings.txt";
    public static final String PAYMENTS_FILE = "data/payments.txt";
    public static final String PLANS_FILE    = "data/plans.txt";
    public static final String GUESTS_FILE   = "data/guests.txt";
    public static final String REVIEWS_FILE  = "data/reviews.txt";

    // =========================================================
    // OOP: STATIC — shared counter for tracking I/O operations
    // Useful for debugging and audit logging
    // =========================================================
    private static int totalReadOperations  = 0;
    private static int totalWriteOperations = 0;

    // =========================================================
    // INSTANCE used for interface compliance (implements)
    // but all real work is done via the static methods below
    // =========================================================

    /**
     * Instance method — implements FileOperations interface contract.
     * Delegates to the static readAllLines() method.
     */
    @Override
    public ArrayList<String> readAll(String filePath) {
        return readAllLines(filePath);
    }

    /**
     * Instance method — implements FileOperations interface contract.
     * Delegates to the static writeAllLines() method.
     */
    @Override
    public void writeAll(String filePath, ArrayList<String> lines) {
        writeAllLines(filePath, lines);
    }

    /**
     * Instance method — implements FileOperations interface contract.
     * Delegates to the static appendLine() static method.
     */
    @Override
    public void appendLine(String filePath, String line) {
        appendSingleLine(filePath, line);
    }

    // =========================================================
    // STATIC UTILITY METHODS — the real workhorses
    // =========================================================

    /**
     * Reads ALL lines from a text file and returns them as
     * an ArrayList<String>. Each line represents one record.
     *
     * OOP: EXCEPTION HANDLING — try-catch-finally block used.
     * The 'finally' block executes whether or not an exception
     * occurs, ensuring proper resource management is logged.
     *
     * @param filePath  Absolute path to the .txt file
     * @return          ArrayList of all non-empty lines in the file
     */
    public static ArrayList<String> readAllLines(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = null;

        try {
            // Ensure the file exists before reading
            ensureFileExists(filePath);

            reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip blank lines and comment lines (starting with #)
                if (!line.isEmpty() && !line.startsWith("#")) {
                    lines.add(line);
                }
            }

            // OOP: STATIC — increment shared operation counter
            totalReadOperations++;

        } catch (FileNotFoundException e) {
            // File does not exist — return empty list (not a fatal error)
            System.err.println("[FileHandler] File not found: " + filePath);
        } catch (IOException e) {
            // I/O error during reading
            System.err.println("[FileHandler] IOException reading file: "
                    + filePath + " — " + e.getMessage());
        } finally {
            // OOP: FINALLY BLOCK — always executed; close the reader
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException closeEx) {
                    System.err.println("[FileHandler] Failed to close reader: "
                            + closeEx.getMessage());
                }
            }
        }

        return lines;
    }

    /**
     * Writes ALL lines in the provided ArrayList to the specified
     * file, OVERWRITING any previous content.
     *
     * Used for UPDATE and DELETE operations (rewrite the entire file
     * with the modified ArrayList).
     *
     * OOP: EXCEPTION HANDLING — try-catch-finally block.
     *
     * @param filePath  Absolute path to the .txt file
     * @param lines     ArrayList of record strings to write
     */
    public static void writeAllLines(String filePath, ArrayList<String> lines) {
        BufferedWriter writer = null;

        try {
            ensureFileExists(filePath);

            // FileWriter(path, false) → overwrite mode (not append)
            writer = new BufferedWriter(new FileWriter(filePath, false));

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }

            // OOP: STATIC — increment shared operation counter
            totalWriteOperations++;

        } catch (IOException e) {
            System.err.println("[FileHandler] IOException writing to file: "
                    + filePath + " — " + e.getMessage());
        } finally {
            // OOP: FINALLY BLOCK — always close the writer
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException closeEx) {
                    System.err.println("[FileHandler] Failed to close writer: "
                            + closeEx.getMessage());
                }
            }
        }
    }

    /**
     * Appends a SINGLE line to the end of the file WITHOUT
     * overwriting existing data.
     *
     * Used for CREATE operations (adding a new record).
     *
     * OOP: EXCEPTION HANDLING — try-catch-finally block.
     *
     * @param filePath  Absolute path to the .txt file
     * @param line      The new record string to append
     */
    public static void appendSingleLine(String filePath, String line) {
        BufferedWriter writer = null;

        try {
            ensureFileExists(filePath);

            // FileWriter(path, true) → APPEND mode
            writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(line);
            writer.newLine();

            // OOP: STATIC — increment shared operation counter
            totalWriteOperations++;

        } catch (IOException e) {
            System.err.println("[FileHandler] IOException appending to file: "
                    + filePath + " — " + e.getMessage());
        } finally {
            // OOP: FINALLY BLOCK
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException closeEx) {
                    System.err.println("[FileHandler] Failed to close writer: "
                            + closeEx.getMessage());
                }
            }
        }
    }

    /**
     * Checks whether a specific record (line) exists in the file
     * by looking for a given search term within each line.
     *
     * OOP: METHOD OVERLOADING — two versions of this method.
     *   Version 1: search in any field (this method)
     *   Version 2: search in a specific field index (below)
     *
     * @param filePath    Path to the .txt file
     * @param searchTerm  The string to search for in any field
     * @return            true if found, false otherwise
     */
    public static boolean recordExists(String filePath, String searchTerm) {
        ArrayList<String> lines = readAllLines(filePath);
        for (String line : lines) {
            if (line.contains(searchTerm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * OOP: METHOD OVERLOADING — version 2 of recordExists().
     * Searches only in a specific pipe-delimited field index.
     *
     * Example: recordExists("users.txt", "john@mail.com", 2)
     *   → checks only field index 2 (email) of each record.
     *
     * @param filePath    Path to the .txt file
     * @param searchTerm  The value to match
     * @param fieldIndex  Zero-based index of the field to check
     * @return            true if found, false otherwise
     */
    public static boolean recordExists(String filePath, String searchTerm, int fieldIndex) {
        ArrayList<String> lines = readAllLines(filePath);
        for (String line : lines) {
            String[] fields = line.split(DELIMITER_REGEX);
            if (fields.length > fieldIndex
                    && fields[fieldIndex].equalsIgnoreCase(searchTerm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and returns a single record line that contains
     * the searchTerm in the specified field index.
     *
     * @param filePath    Path to the .txt file
     * @param searchTerm  Value to match
     * @param fieldIndex  Field column to match against
     * @return            The matching record String, or null if not found
     */
    public static String findRecord(String filePath, String searchTerm, int fieldIndex) {
        ArrayList<String> lines = readAllLines(filePath);
        for (String line : lines) {
            String[] fields = line.split(DELIMITER_REGEX);
            if (fields.length > fieldIndex
                    && fields[fieldIndex].equalsIgnoreCase(searchTerm)) {
                return line;
            }
        }
        return null; // not found
    }

    /**
     * Deletes a record from the file by removing the line
     * that contains the given searchTerm in the given field index.
     *
     * Steps:
     *   1. Read all lines into ArrayList
     *   2. Remove matching line
     *   3. Write the updated ArrayList back to the file
     *
     * @param filePath    Path to the .txt file
     * @param searchTerm  The unique identifier of the record to delete
     * @param fieldIndex  Field index to match the searchTerm against
     * @return            true if a record was deleted, false if not found
     */
    public static boolean deleteRecord(String filePath, String searchTerm, int fieldIndex) {
        ArrayList<String> lines = readAllLines(filePath);
        boolean deleted = false;

        ArrayList<String> updated = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(DELIMITER_REGEX);
            if (fields.length > fieldIndex
                    && fields[fieldIndex].equalsIgnoreCase(searchTerm)) {
                deleted = true; // skip this line (delete it)
            } else {
                updated.add(line);
            }
        }

        if (deleted) {
            writeAllLines(filePath, updated);
        }
        return deleted;
    }

    /**
     * Updates a specific record in the file.
     * Replaces the line where field[fieldIndex] == searchTerm
     * with the provided newLine string.
     *
     * @param filePath    Path to the .txt file
     * @param searchTerm  The unique ID or key of the record to update
     * @param fieldIndex  Field index to match
     * @param newLine     The replacement record string
     * @return            true if a record was updated, false if not found
     */
    public static boolean updateRecord(String filePath, String searchTerm,
                                       int fieldIndex, String newLine) {
        ArrayList<String> lines = readAllLines(filePath);
        boolean updated = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(DELIMITER_REGEX);
            if (fields.length > fieldIndex
                    && fields[fieldIndex].equalsIgnoreCase(searchTerm)) {
                lines.set(i, newLine);
                updated = true;
                break; // update first match only
            }
        }

        if (updated) {
            writeAllLines(filePath, lines);
        }
        return updated;
    }

    /**
     * Generates a unique numeric ID by counting existing records
     * in a file and adding 1. Ensures IDs are always sequential.
     *
     * OOP: STATIC — utility method, no instance needed.
     *
     * @param filePath  Path to the .txt file to count records in
     * @param prefix    Short prefix string (e.g., "USR", "VND", "BKG")
     * @return          A new unique ID string like "USR-4" or "BKG-12"
     */
    public static String generateId(String filePath, String prefix) {
        int count = readAllLines(filePath).size() + 1;
        return prefix + "-" + count;
    }

    /**
     * Ensures a file (and its parent directories) exist.
     * Creates them if they do not yet exist.
     *
     * OOP: STATIC — utility method.
     * OOP: EXCEPTION HANDLING — throws IOException on failure.
     *
     * @param filePath  Path to the file to ensure exists
     * @throws IOException  If directories or file cannot be created
     */
    public static void ensureFileExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            // Create all parent directories if needed
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            file.createNewFile();
            System.out.println("[FileHandler] Created new data file: " + filePath);
        }
    }

    // =========================================================
    // OOP: STATIC — Getters for shared operation counters
    // These are class-level (not instance-level) statistics
    // =========================================================

    /**
     * @return  Total number of read operations performed since app start
     */
    public static int getTotalReadOperations() {
        return totalReadOperations;
    }

    /**
     * @return  Total number of write operations performed since app start
     */
    public static int getTotalWriteOperations() {
        return totalWriteOperations;
    }

    /**
     * Resets the static I/O counters. Useful for testing.
     * OOP: STATIC method operating on static fields.
     */
    public static void resetCounters() {
        totalReadOperations  = 0;
        totalWriteOperations = 0;
    }
}
