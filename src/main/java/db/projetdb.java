package db;

import org.example.projet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class projetdb {

    public static boolean insertprojet(projet p) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "INSERT INTO Projet VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, p.getId());
        ps.setString(2, p.getNom());
        ps.setDouble(3, p.getBudget());
        ps.setInt(4, p.getIdDepartement());
        int result = ps.executeUpdate();
        ps.close();
        con.close();
        return result > 0;
    }

    public static boolean deleteProjet(int id) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "DELETE FROM Projet WHERE id_projet = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int result = ps.executeUpdate();
        ps.close();
        con.close();
        return result > 0;
    }

    public static projet findById(int id) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "SELECT * FROM Projet WHERE id_projet = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        projet p = null;
        if (rs.next()) {
            p = new projet(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4)
            );
        }
        rs.close();
        ps.close();
        con.close();
        return p;
    }

    public static List<projet> getAllProjets() throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "SELECT * FROM Projet";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<projet> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new projet(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4)
            ));
        }
        rs.close();
        st.close();
        con.close();
        return list;
    }
}
