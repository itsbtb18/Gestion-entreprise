package db;

import org.example.employe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class employedb {

    public static boolean insertemploye(employe e) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "INSERT INTO Employe VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, e.getId());
        ps.setString(2, e.getNom());
        ps.setString(3, e.getPrenom());
        ps.setString(4, e.getPoste());
        ps.setDouble(5, e.getSalaire());
        ps.setInt(6, e.getIdDepartement());
        int result = ps.executeUpdate();
        ps.close();
        con.close();
        return result > 0;
    }

    public static boolean deleteEmploye(int id) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "DELETE FROM Employe WHERE id_employe = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int result = ps.executeUpdate();
        ps.close();
        con.close();
        return result > 0;
    }

    public static employe findById(int id) throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "SELECT * FROM Employe WHERE id_employe = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        employe e = null;
        if (rs.next()) {
            e = new employe(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDouble(5),
                    rs.getInt(6)
            );
        }
        rs.close();
        ps.close();
        con.close();
        return e;
    }

    public static List<employe> getAllEmployes() throws SQLException {
        Connection con = dbconnection.getConnection();
        String sql = "SELECT * FROM Employe";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<employe> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new employe(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDouble(5),
                    rs.getInt(6)
            ));
        }
        rs.close();
        st.close();
        con.close();
        return list;
    }
}
