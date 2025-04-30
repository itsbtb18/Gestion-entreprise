package UI;

import db.departementdb;
import org.example.departement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class page_departement extends JFrame {
    private final JTextField idField = new JTextField(15);
    private final JTextField nomField = new JTextField(15);
    private final JTextField responsableField = new JTextField(15);
    private final JTable table = new JTable();

    public page_departement() {
        setTitle("Gestion des Départements");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panneau de formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Formulaire Département"));

        formPanel.add(createRow("ID Département:", idField));
        formPanel.add(createRow("Nom Département:", nomField));
        formPanel.add(createRow("ID Responsable:", responsableField));

        // Panneau de boutons
        JPanel buttonPanel = new JPanel();
        JButton insertButton = new JButton("Insérer");
        JButton deleteButton = new JButton("Supprimer");
        JButton searchButton = new JButton("Rechercher");
        JButton refreshButton = new JButton("Actualiser");

        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buttonPanel);

        // Tableau des départements
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des départements"));

        // Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Actions des boutons
        insertButton.addActionListener(e -> insertDepartement());
        deleteButton.addActionListener(e -> deleteDepartement());
        searchButton.addActionListener(e -> searchDepartement());
        refreshButton.addActionListener(e -> refreshTable());

        // Charger les données initiales
        refreshTable();

        setVisible(true);
    }

    private JPanel createRow(String label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }

    private void insertDepartement() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nom = nomField.getText().trim();
            int idResponsable = Integer.parseInt(responsableField.getText().trim());

            departement dept = new departement(id, nom, idResponsable);
            boolean success = departementdb.insertDepartement(dept);

            if (success) {
                JOptionPane.showMessageDialog(this, "Département inséré avec succès!");
                clearFields();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion du département.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides pour les IDs.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur SQL: " + e.getMessage());
        }
    }

    private void deleteDepartement() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            boolean success = departementdb.deleteDepartement(id);

            if (success) {
                JOptionPane.showMessageDialog(this, "Département supprimé avec succès!");
                clearFields();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Département non trouvé ou erreur lors de la suppression.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur SQL: " + e.getMessage());
        }
    }

    private void searchDepartement() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            departement dept = departementdb.findById(id);

            if (dept != null) {
                nomField.setText(dept.getNom());
                responsableField.setText(String.valueOf(dept.getIdResponsable()));
                JOptionPane.showMessageDialog(this, "Département trouvé.");
            } else {
                JOptionPane.showMessageDialog(this, "Département non trouvé.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur SQL: " + e.getMessage());
        }
    }

    private void refreshTable() {
        try {
            List<departement> departements = departementdb.getAllDepartements();
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"ID", "Nom", "ID Responsable"});

            for (departement dept : departements) {
                model.addRow(new Object[]{
                        dept.getId(),
                        dept.getNom(),
                        dept.getIdResponsable()
                });
            }

            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des départements: " + e.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nomField.setText("");
        responsableField.setText("");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(page_departement::new);
    }
}