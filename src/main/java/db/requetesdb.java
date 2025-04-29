package db;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class requetesdb {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "BDDAdmin", "TPAdmin");
    }

    public static DefaultTableModel requete1_employesParProjet(int idProjet) throws SQLException {
        String sql = """
            SELECT e.nom, e.prénom
            FROM Employé e
            JOIN Travail t ON e.id_employé = t.id_employé
            WHERE t.id_projet = ?
        """;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProjet);
            ResultSet rs = ps.executeQuery();
            return buildTableModel(rs);
        }
    }

    public static DefaultTableModel requete2_employesSansProjet() throws SQLException {
        String sql = """
            SELECT nom, prénom
            FROM Employé
            WHERE id_employé NOT IN (SELECT id_employé FROM Travail)
        """;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            return buildTableModel(rs);
        }
    }

    public static DefaultTableModel requete3_projetsSansAffectation() throws SQLException {
        String sql = """
            SELECT nom_projet
            FROM Projet
            WHERE id_projet NOT IN (SELECT id_projet FROM Travail)
        """;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            return buildTableModel(rs);
        }
    }

    public static DefaultTableModel requete4_employesProjetUnique(String nomProjet) throws SQLException {
        String sql = """
            SELECT e.nom, e.prénom
            FROM Employé e
            JOIN Travail t ON e.id_employé = t.id_employé
            JOIN Projet p ON t.id_projet = p.id_projet
            WHERE p.nom_projet = ?
            GROUP BY e.id_employé, e.nom, e.prénom
            HAVING COUNT(*) = 1
        """;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomProjet);
            ResultSet rs = ps.executeQuery();
            return buildTableModel(rs);
        }
    }

    public static DefaultTableModel requete5_nbProjetsParEmploye() throws SQLException {
        String sql = """
            SELECT e.nom, e.prénom, COUNT(t.id_projet) AS nb_projets
            FROM Employé e
            LEFT JOIN Travail t ON e.id_employé = t.id_employé
            GROUP BY e.nom, e.prénom
        """;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            return buildTableModel(rs);
        }
    }

    // Méthode utilitaire pour construire un modèle de tableau
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();
        DefaultTableModel model = new DefaultTableModel();

        // Colonnes
        for (int i = 1; i <= colCount; i++) {
            model.addColumn(meta.getColumnName(i));
        }

        // Lignes
        while (rs.next()) {
            Object[] row = new Object[colCount];
            for (int i = 1; i <= colCount; i++) {
                row[i - 1] = rs.getObject(i);
            }
            model.addRow(row);
        }

        return model;
    }
}
