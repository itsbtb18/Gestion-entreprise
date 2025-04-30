package db;

import org.example.travail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class travaildb {

    public static boolean insertTravail(travail t) throws SQLException {
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO Travail VALUES (?, ?, ?)")) {

            ps.setInt(1, t.getIdEmploye());
            ps.setInt(2, t.getIdProjet());
            ps.setString(3, t.getDateAffectation().toString()); // Save date as string for SQLite
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    public static boolean deleteTravail(int idEmploye, int idProjet) throws SQLException {
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM Travail WHERE id_employe = ? AND id_projet = ?")) {

            ps.setInt(1, idEmploye);
            ps.setInt(2, idProjet);
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    public static travail findByIds(int idEmploye, int idProjet) throws SQLException {
        try (Connection con = dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Travail WHERE id_employe = ? AND id_projet = ?")) {

            ps.setInt(1, idEmploye);
            ps.setInt(2, idProjet);
            ResultSet rs = ps.executeQuery();
            travail t = null;
            if (rs.next()) {
                t = new travail(
                        rs.getInt("id_employe"),
                        rs.getInt("id_projet"),
                        Date.valueOf(rs.getString("date_affectation"))
                );
            }
            return t;
        }
    }

    public static List<travail> getAllTravaux() throws SQLException {
        List<travail> list = new ArrayList<>();
        try (Connection con = dbconnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Travail")) {

            while (rs.next()) {
                list.add(new travail(
                        rs.getInt("id_employe"),
                        rs.getInt("id_projet"),
                        Date.valueOf(rs.getString("date_affectation"))
                ));
            }
            return list;
        }
    }
}
