package db;

import org.example.departement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class departementdb {

    public static boolean insertDepartement(departement d) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "INSERT INTO Departement VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, d.getId());
        ps.setString(2, d.getNom());
        ps.setInt(3, d.getIdResponsable());
        int result = ps.executeUpdate();
        ps.close();
        con.close();
        return result > 0;
    }

    public static boolean deleteDepartement(int id) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "DELETE FROM Departement WHERE id_departement = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int result = ps.executeUpdate();
        ps.close();
        con.close();
        return result > 0;
    }

    public static departement findById(int id) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "SELECT * FROM Departement WHERE id_departement = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        departement d = null;
        if (rs.next()) {
            d = new departement(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3)
            );
        }
        rs.close();
        ps.close();
        con.close();
        return d;
    }

    public static List<departement> getAllDepartements() throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "SELECT * FROM Departement";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<departement> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new departement(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3)
            ));
        }
        rs.close();
        st.close();
        con.close();
        return list;
    }
}
