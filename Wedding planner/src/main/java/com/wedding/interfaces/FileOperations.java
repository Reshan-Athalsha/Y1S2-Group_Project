package com.wedding.interfaces;

import java.util.ArrayList;

/**
 * ============================================================
 * OOP PRINCIPLE: ABSTRACTION (Interface)
 * ============================================================
 * FileOperations defines a contract (blueprint) for all file
 * I/O operations across the system. Any class that implements
 * this interface MUST provide concrete implementations for
 * reading and writing data to text files.
 *
 * This enforces the SOLID Interface Segregation Principle (ISP)
 * and ensures every data-access class follows the same contract.
 * ============================================================
 */
public interface FileOperations {

    /**
     * Reads all lines from a given file path and returns
     * them as an ArrayList of Strings.
     *
     * @param filePath  Absolute or relative path to the .txt file
     * @return          ArrayList of String lines (each line = one record)
     */
    ArrayList<String> readAll(String filePath);

    /**
     * Writes all lines from the provided ArrayList back to
     * the specified file, overwriting its contents.
     *
     * @param filePath  Path to the .txt file
     * @param lines     ArrayList of String records to write
     */
    void writeAll(String filePath, ArrayList<String> lines);

    /**
     * Appends a single new line/record to the end of the file
     * without overwriting existing data.
     *
     * @param filePath  Path to the .txt file
     * @param line      The single record string to append
     */
    void appendLine(String filePath, String line);
}
