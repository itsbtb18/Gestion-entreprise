package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class DBInitializer {

    public static void initializeDatabase() {
        try {
            // Check if database directory exists, create if not
            Files.createDirectories(Paths.get("src", "data"));

            // Create tables if they don't exist
            createTables();

            System.out.println("Database initialized successfully.");
        } catch (IOException | SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTables() throws SQLException {
        String createDepartementSQL = """
            CREATE TABLE IF NOT EXISTS Departement (
                id_departement INTEGER PRIMARY KEY,
                nom_departement TEXT,
                id_responsable INTEGER
            )
        """;

        String createEmployeSQL = """
            CREATE TABLE IF NOT EXISTS Employe (
                id_employe INTEGER PRIMARY KEY,
                nom TEXT,
                prenom TEXT,
                poste TEXT,
                salaire REAL,
                id_departement INTEGER,
                FOREIGN KEY (id_departement) REFERENCES Departement(id_departement)
            )
        """;

        String createProjetSQL = """
            CREATE TABLE IF NOT EXISTS Projet (
                id_projet INTEGER PRIMARY KEY,
                nom_projet TEXT,
                budget REAL,
                id_departement INTEGER,
                FOREIGN KEY (id_departement) REFERENCES Departement(id_departement)
            )
        """;

        String createTravailSQL = """
            CREATE TABLE IF NOT EXISTS Travail (
                id_employe INTEGER,
                id_projet INTEGER,
                date_affectation TEXT,
                PRIMARY KEY (id_employe, id_projet),
                FOREIGN KEY (id_employe) REFERENCES Employe(id_employe),
                FOREIGN KEY (id_projet) REFERENCES Projet(id_projet)
            )
        """;

        try (Connection conn = dbconnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createDepartementSQL);
            stmt.execute(createEmployeSQL);
            stmt.execute(createProjetSQL);
            stmt.execute(createTravailSQL);
        }
    }
}