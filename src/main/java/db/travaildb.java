package db;

import org.example.travail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class travaildb {

    public static boolean insertTravail(travail t) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "INSERT INTO Travail VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, t.getIdEmploye());
        ps.setInt(2, t.getIdProjet());
        ps.setDate(3, t.getDateAffectation());
        int result = ps.executeUpdate();
        ps.close();
        con.close();
        return result > 0;
    }

    public static boolean deleteTravail(int idEmploye, int idProjet) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "DELETE FROM Travail WHERE id_employe = ? AND id_projet = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idEmploye);
        ps.setInt(2, idProjet);
        int result = ps.executeUpdate();
        ps.close();
        con.close();
        return result > 0;
    }

    public static travail findByIds(int idEmploye, int idProjet) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "SELECT * FROM Travail WHERE id_employe = ? AND id_projet = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idEmploye);
        ps.setInt(2, idProjet);
        ResultSet rs = ps.executeQuery();
        travail t = null;
        if (rs.next()) {
            t = new travail(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getDate(3)
            );
        }
        rs.close();
        ps.close();
        con.close();
        return t;
    }

    public static List<travail> getAllTravaux() throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "SELECT * FROM Travail";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<travail> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new travail(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getDate(3)
            ));
        }
        rs.close();
        st.close();
        con.close();
        return list;
    }
}

