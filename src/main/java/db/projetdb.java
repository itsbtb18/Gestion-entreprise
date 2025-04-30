package db;

import org.example.projet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class projetdb {

    public static boolean insertprojet(projet p) throws SQLException {
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO Projet VALUES (?, ?, ?, ?)")) {

            ps.setInt(1, p.getId());
            ps.setString(2, p.getNom());
            ps.setDouble(3, p.getBudget());
            ps.setInt(4, p.getIdDepartement());
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    public static boolean deleteProjet(int id) throws SQLException {
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM Projet WHERE id_projet = ?")) {

            ps.setInt(1, id);
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    public static projet findById(int id) throws SQLException {
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Projet WHERE id_projet = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            projet p = null;
            if (rs.next()) {
                p = new projet(
                        rs.getInt("id_projet"),
                        rs.getString("nom_projet"),
                        rs.getDouble("budget"),
                        rs.getInt("id_departement")
                );
            }
            return p;
        }
    }

    public static List<projet> getAllProjets() throws SQLException {
        List<projet> list = new ArrayList<>();
        try (Connection con = dbconnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Projet")) {

            while (rs.next()) {
                list.add(new projet(
                        rs.getInt("id_projet"),
                        rs.getString("nom_projet"),
                        rs.getDouble("budget"),
                        rs.getInt("id_departement")
                ));
            }
            return list;
        }
    }
}