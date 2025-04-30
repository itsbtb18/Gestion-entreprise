package db;

import org.example.employe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class employedb {

    public static boolean insertemploye(employe e) throws SQLException {
        String sql = "INSERT INTO Employe VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getId());
            ps.setString(2, e.getNom());
            ps.setString(3, e.getPrenom());
            ps.setString(4, e.getPoste());
            ps.setDouble(5, e.getSalaire());
            ps.setInt(6, e.getIdDepartement());

            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    public static boolean supprimerEmploye(int id) throws SQLException {
        String sql = "DELETE FROM Employe WHERE id_employe = ?";
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static employe rechercherEmploye(int id) throws SQLException {
        String sql = "SELECT * FROM Employe WHERE id_employe = ?";
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new employe(
                        rs.getInt("id_employe"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("poste"),
                        rs.getDouble("salaire"),
                        rs.getInt("id_departement")
                );
            } else {
                return null;
            }
        }
    }

    public static List<employe> getAllEmployes() throws SQLException {
        String sql = "SELECT * FROM Employe";
        List<employe> list = new ArrayList<>();
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                employe e = new employe(
                        rs.getInt("id_employe"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("poste"),
                        rs.getDouble("salaire"),
                        rs.getInt("id_departement")
                );
                list.add(e);
            }
        }
        return list;
    }
}