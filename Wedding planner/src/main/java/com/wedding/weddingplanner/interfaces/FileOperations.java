package com.wedding.weddingplanner.interfaces;

/**
 * ============================================================
 * OOP PRINCIPLE: ABSTRACTION (INTERFACE)
 * ============================================================
 * Defines a contract for any class that needs to be serialized 
 * into a text file format. Classes implementing this MUST provide 
 * their own implementation of the toFileString() method.
 */
public interface FileOperations {
    
    /**
     * Converts the object into a pipe-delimited string format
     * suitable for saving into users.txt.
     * 
     * @return String representation of the object.
     */
    String toFileString();
}
