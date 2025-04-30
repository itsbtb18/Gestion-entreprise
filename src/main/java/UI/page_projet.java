package UI;

import org.example.projet;
import db.projetdb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class page_projet extends JFrame {
    private final JTextField idField = new JTextField(15);
    private final JTextField nomField = new JTextField(15);
    private final JTextField budgetField = new JTextField(15);
    private final JTextField departementField = new JTextField(15);
    private final JTable table = new JTable();

    public page_projet() {
        setTitle("Gestion des Projets");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Formulaire Projet"));

        formPanel.add(creerLigne("ID Projet :", idField));
        formPanel.add(creerLigne("Nom du projet :", nomField));
        formPanel.add(creerLigne("Budget :", budgetField));
        formPanel.add(creerLigne("ID Département :", departementField));

        JButton insertButton = new JButton("Insérer");
        JButton deleteButton = new JButton("Supprimer");
        JButton searchButton = new JButton("Rechercher");
        JButton refreshButton = new JButton("Actualiser");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des projets"));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Actions pour les boutons
        insertButton.addActionListener(e -> insererProjet());
        deleteButton.addActionListener(e -> supprimerProjet());
        searchButton.addActionListener(e -> rechercherProjet());
        refreshButton.addActionListener(e -> chargerProjets());

        chargerProjets();
        setVisible(true);
    }

    private JPanel creerLigne(String label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }

    private void insererProjet() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nom = nomField.getText().trim();
            double budget = Double.parseDouble(budgetField.getText().trim());
            int idDept = Integer.parseInt(departementField.getText().trim());

            // Vérifier si le projet existe déjà
            projet existant = projetdb.findById(id);
            if (existant != null) {
                JOptionPane.showMessageDialog(this, "Un projet avec cet ID existe déjà !",
                        "Erreur d'insertion", JOptionPane.ERROR_MESSAGE);
                return;
            }

            projet p = new projet(id, nom, budget, idDept);
            boolean success = projetdb.insertprojet(p);

            if (success) {
                JOptionPane.showMessageDialog(this, "Projet inséré avec succès !");
                clearFields();
                chargerProjets();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format incorrect : l'ID et l'ID du département doivent être numériques, et le budget doit être un nombre.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void supprimerProjet() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            boolean success = projetdb.deleteProjet(id);

            if (success) {
                JOptionPane.showMessageDialog(this, "Projet supprimé.");
                clearFields();
                chargerProjets();
            } else {
                JOptionPane.showMessageDialog(this, "Aucun projet trouvé avec cet ID.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void rechercherProjet() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            projet p = projetdb.findById(id);

            if (p != null) {
                nomField.setText(p.getNom());
                budgetField.setText(String.valueOf(p.getBudget()));
                departementField.setText(String.valueOf(p.getIdDepartement()));
                JOptionPane.showMessageDialog(this, "Projet trouvé.");
            } else {
                JOptionPane.showMessageDialog(this, "Aucun projet trouvé avec cet ID.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void chargerProjets() {
        try {
            List<projet> list = projetdb.getAllProjets();
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"ID", "Nom du projet", "Budget", "ID Département"});

            for (projet p : list) {
                model.addRow(new Object[]{
                        p.getId(), p.getNom(), p.getBudget(), p.getIdDepartement()
                });
            }

            table.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des projets : " + ex.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nomField.setText("");
        budgetField.setText("");
        departementField.setText("");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(page_projet::new);
    }
}