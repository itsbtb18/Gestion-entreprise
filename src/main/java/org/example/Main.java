package org.example;

import UI.Page_principale;
import db.DBInitializer;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set look and feel
            UIManager.setLookAndFeel(new FlatLightLaf());

            // Initialize database
            DBInitializer.initializeDatabase();

            // Start application
            SwingUtilities.invokeLater(Page_principale::new);
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}