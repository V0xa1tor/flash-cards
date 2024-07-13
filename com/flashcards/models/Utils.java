package com.flashcards.models;

import java.io.File;

/**
 * Utilitary class.
 * Define util methods like to get extension of a file,
 * and touch a directory.
 * 
 * @author Vítor Menezes Oliveira
 */
public class Utils {
    
    /**
     * Gets files extension, based on file name.
     * 
     * @param fileName the file name
     * @return the extension
     */
    public static String getExtension(String fileName) {
        String ext = null;
        int i = fileName.lastIndexOf('.');
        
        if (i > 0 && i < fileName.length() - 1) {
            ext = fileName.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /**
     * Gets file extension, based on file.
     * 
     * @param f the file
     * @return the extension
     * @see {@link File}
     */
    public static String getExtension(File f) {
        return getExtension(f.getName());
    }

    /**
     * Makes specified directory, if it doesn't exists.
     * If it already exists, do nothing.
     * 
     * @param path the directory path
     * @return the directory as a {@link File} object
     */
    public static File touchDirectory(String path) {
        File directory = new File(path);
        directory.mkdir();
        return directory;
    }

}