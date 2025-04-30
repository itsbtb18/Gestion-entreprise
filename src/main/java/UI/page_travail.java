package UI;

import org.example.travail;
import db.travaildb;
import db.employedb;
import db.projetdb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class page_travail extends JFrame {
    private final JTextField idEmployeField = new JTextField(15);
    private final JTextField idProjetField = new JTextField(15);
    private final JTextField dateField = new JTextField(15);
    private final JTable table = new JTable();

    public page_travail() {
        setTitle("Gestion des Affectations (Travail)");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Formulaire d'Affectation"));

        formPanel.add(creerLigne("ID Employé :", idEmployeField));
        formPanel.add(creerLigne("ID Projet :", idProjetField));
        formPanel.add(creerLigne("Date d'affectation (YYYY-MM-DD) :", dateField));

        JButton insertButton = new JButton("Affecter");
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
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des affectations"));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Actions pour les boutons
        insertButton.addActionListener(e -> insererTravail());
        deleteButton.addActionListener(e -> supprimerTravail());
        searchButton.addActionListener(e -> rechercherTravail());
        refreshButton.addActionListener(e -> chargerTravaux());

        chargerTravaux();
        setVisible(true);
    }

    private JPanel creerLigne(String label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }

    private void insererTravail() {
        try {
            int idEmploye = Integer.parseInt(idEmployeField.getText().trim());
            int idProjet = Integer.parseInt(idProjetField.getText().trim());
            String dateStr = dateField.getText().trim();

            // Vérifier si l'employé existe
            if (employedb.rechercherEmploye(idEmploye) == null) {
                JOptionPane.showMessageDialog(this, "L'employé avec l'ID " + idEmploye + " n'existe pas.");
                return;
            }

            // Vérifier si le projet existe
            if (projetdb.findById(idProjet) == null) {
                JOptionPane.showMessageDialog(this, "Le projet avec l'ID " + idProjet + " n'existe pas.");
                return;
            }

            // Vérifier si l'affectation existe déjà
            if (travaildb.findByIds(idEmploye, idProjet) != null) {
                JOptionPane.showMessageDialog(this, "Cette affectation existe déjà !");
                return;
            }

            // Convertir la date
            Date dateAffectation;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsed = format.parse(dateStr);
                dateAffectation = new Date(parsed.getTime());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez YYYY-MM-DD.");
                return;
            }

            travail t = new travail(idEmploye, idProjet, dateAffectation);
            boolean success = travaildb.insertTravail(t);

            if (success) {
                JOptionPane.showMessageDialog(this, "Affectation ajoutée avec succès !");
                clearFields();
                chargerTravaux();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Les IDs doivent être numériques !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void supprimerTravail() {
        try {
            int idEmploye = Integer.parseInt(idEmployeField.getText().trim());
            int idProjet = Integer.parseInt(idProjetField.getText().trim());

            boolean success = travaildb.deleteTravail(idEmploye, idProjet);

            if (success) {
                JOptionPane.showMessageDialog(this, "Affectation supprimée.");
                clearFields();
                chargerTravaux();
            } else {
                JOptionPane.showMessageDialog(this, "Aucune affectation trouvée avec ces IDs.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void rechercherTravail() {
        try {
            int idEmploye = Integer.parseInt(idEmployeField.getText().trim());
            int idProjet = Integer.parseInt(idProjetField.getText().trim());

            travail t = travaildb.findByIds(idEmploye, idProjet);

            if (t != null) {
                dateField.setText(t.getDateAffectation().toString());
                JOptionPane.showMessageDialog(this, "Affectation trouvée.");
            } else {
                JOptionPane.showMessageDialog(this, "Aucune affectation trouvée avec ces IDs.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void chargerTravaux() {
        try {
            List<travail> list = travaildb.getAllTravaux();
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"ID Employé", "ID Projet", "Date d'affectation"});

            for (travail t : list) {
                model.addRow(new Object[]{
                        t.getIdEmploye(), t.getIdProjet(), t.getDateAffectation()
                });
            }

            table.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des affectations : " + ex.getMessage());
        }
    }

    private void clearFields() {
        idEmployeField.setText("");
        idProjetField.setText("");
        dateField.setText("");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(page_travail::new);
    }
}
