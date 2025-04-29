package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class page_requetes extends JFrame {

    private JTable resultTable;
    private JTextArea queryArea;

    public page_requetes() {
        setTitle("Requêtes SQL");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        queryArea = new JTextArea(5, 60);
        resultTable = new JTable();
        JButton executeButton = new JButton("Exécuter");

        executeButton.addActionListener(e -> executerRequete());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Entrez votre requête SQL"));
        topPanel.add(new JScrollPane(queryArea), BorderLayout.CENTER);
        topPanel.add(executeButton, BorderLayout.EAST);

        JScrollPane resultPane = new JScrollPane(resultTable);
        resultPane.setBorder(BorderFactory.createTitledBorder("Résultat"));

        add(topPanel, BorderLayout.NORTH);
        add(resultPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void executerRequete() {
        String query = queryArea.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une requête SQL.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "BDDAdmin", "TPAdmin");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();

            DefaultTableModel model = new DefaultTableModel();
            for (int i = 1; i <= columns; i++) {
                model.addColumn(meta.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

            resultTable.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ignored) {}
        new PageRequetes();
    }
}

