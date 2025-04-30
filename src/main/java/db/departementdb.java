package db;

import org.example.departement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class departementdb {

    public static boolean insertDepartement(departement d) throws SQLException {
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO Departement VALUES (?, ?, ?)")) {

            ps.setInt(1, d.getId());
            ps.setString(2, d.getNom());
            ps.setInt(3, d.getIdResponsable());
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    public static boolean deleteDepartement(int id) throws SQLException {
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM Departement WHERE id_departement = ?")) {

            ps.setInt(1, id);
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    public static departement findById(int id) throws SQLException {
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Departement WHERE id_departement = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            departement d = null;
            if (rs.next()) {
                d = new departement(
                        rs.getInt("id_departement"),
                        rs.getString("nom_departement"),
                        rs.getInt("id_responsable")
                );
            }
            return d;
        }
    }

    public static List<departement> getAllDepartements() throws SQLException {
        List<departement> list = new ArrayList<>();
        try (Connection con = dbconnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Departement")) {

            while (rs.next()) {
                list.add(new departement(
                        rs.getInt("id_departement"),
                        rs.getString("nom_departement"),
                        rs.getInt("id_responsable")
                ));
            }
            return list;
        }
    }
}