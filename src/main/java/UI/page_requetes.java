package UI;

import db.requetesdb;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class page_requetes extends JFrame {

    private JTable resultTable;
    private JTextArea queryArea;

    public page_requetes() {
        setTitle("Exécution de Requêtes SQL - Projet BD1");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Zone de saisie manuelle
        queryArea = new JTextArea(3, 60);
        JButton btnExecuterManuel = new JButton("Exécuter la requête");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Saisir une requête SQL personnalisée"));
        topPanel.add(new JScrollPane(queryArea), BorderLayout.CENTER);
        topPanel.add(btnExecuterManuel, BorderLayout.EAST);

        // --- Zone des résultats
        resultTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Résultats"));

        // --- Boutons prédéfinis
        JPanel requetesPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        requetesPanel.setBorder(BorderFactory.createTitledBorder("Requêtes prédéfinies"));

        JButton btnReq1 = new JButton("1. Employés affectés à un projet");
        JButton btnReq2 = new JButton("2. Employés sans projet");
        JButton btnReq3 = new JButton("3. Projets sans employés");
        JButton btnReq4 = new JButton("4. Employés affectés uniquement à un projet donné");
        JButton btnReq5 = new JButton("5. Nombre de projets par employé");

        requetesPanel.add(btnReq1);
        requetesPanel.add(btnReq2);
        requetesPanel.add(btnReq3);
        requetesPanel.add(btnReq4);
        requetesPanel.add(btnReq5);

        // --- Layout principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(requetesPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
        setVisible(true);

        // === Événements ===

        btnExecuterManuel.addActionListener(e -> executerRequeteLibre());

        btnReq1.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "ID du projet ?");
            if (input != null && input.matches("\\d+")) {
                try {
                    DefaultTableModel model = requetesdb.requete1_employesParProjet(Integer.parseInt(input));
                    resultTable.setModel(model);
                } catch (SQLException ex) {
                    showError(ex);
                }
            }
        });

        btnReq2.addActionListener(e -> {
            try {
                DefaultTableModel model = requetesdb.requete2_employesSansProjet();
                resultTable.setModel(model);
            } catch (SQLException ex) {
                showError(ex);
            }
        });

        btnReq3.addActionListener(e -> {
            try {
                DefaultTableModel model = requetesdb.requete3_projetsSansAffectation();
                resultTable.setModel(model);
            } catch (SQLException ex) {
                showError(ex);
            }
        });

        btnReq4.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Nom exact du projet ?");
            if (input != null && !input.isBlank()) {
                try {
                    DefaultTableModel model = requetesdb.requete4_employesProjetUnique(input.trim());
                    resultTable.setModel(model);
                } catch (SQLException ex) {
                    showError(ex);
                }
            }
        });

        btnReq5.addActionListener(e -> {
            try {
                DefaultTableModel model = requetesdb.requete5_nbProjetsParEmploye();
                resultTable.setModel(model);
            } catch (SQLException ex) {
                showError(ex);
            }
        });
    }

    // === Requête libre ===
    private void executerRequeteLibre() {
        String query = queryArea.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une requête.");
            return;
        }

        try (var conn = requetesdb.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(query)) {

            resultTable.setModel(requetesdb.buildTableModel(rs));

        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void showError(SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erreur SQL : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(page_requetes::new);
    }
}

