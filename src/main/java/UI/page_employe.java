package UI;

import org.example.employe;
import db.employedb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class page_employe extends JFrame {
    private final JTextField idField = new JTextField(15);
    private final JTextField nomField = new JTextField(15);
    private final JTextField prenomField = new JTextField(15);
    private final JTextField posteField = new JTextField(15);
    private final JTextField salaireField = new JTextField(15);
    private final JTextField deptField = new JTextField(15);
    private final JTable table = new JTable();

    public page_employe() {
        setTitle("Gestion des Employés");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Formulaire Employé"));

        formPanel.add(creerLigne("ID Employé :", idField));
        formPanel.add(creerLigne("Nom :", nomField));
        formPanel.add(creerLigne("Prénom :", prenomField));
        formPanel.add(creerLigne("Poste :", posteField));
        formPanel.add(creerLigne("Salaire :", salaireField));
        formPanel.add(creerLigne("ID Département :", deptField));

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
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des employés"));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        insertButton.addActionListener(e -> insererEmploye());
        deleteButton.addActionListener(e -> supprimerEmploye());
        searchButton.addActionListener(e -> rechercherEmploye());
        refreshButton.addActionListener(e -> chargerEmployes());

        chargerEmployes();
        setVisible(true);
    }

    private JPanel creerLigne(String label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }

    private void insererEmploye() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            String poste = posteField.getText().trim();
            double salaire = Double.parseDouble(salaireField.getText().trim());
            int idDept = Integer.parseInt(deptField.getText().trim());

            employe emp = new employe(id, nom, prenom, poste, salaire, idDept);
            boolean success = employedb.insertemploye(emp);

            if (success) {
                JOptionPane.showMessageDialog(this, "Employé inséré avec succès !");
                clearFields();
                chargerEmployes();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void supprimerEmploye() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            boolean success = employedb.supprimerEmploye(id);

            if (success) {
                JOptionPane.showMessageDialog(this, "Employé supprimé.");
                clearFields();
                chargerEmployes();
            } else {
                JOptionPane.showMessageDialog(this, "Aucun employé trouvé avec cet ID.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void rechercherEmploye() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            employe e = employedb.rechercherEmploye(id);

            if (e != null) {
                nomField.setText(e.getNom());
                prenomField.setText(e.getPrenom());
                posteField.setText(e.getPoste());
                salaireField.setText(String.valueOf(e.getSalaire()));
                deptField.setText(String.valueOf(e.getIdDepartement()));
                JOptionPane.showMessageDialog(this, "Employé trouvé.");
            } else {
                JOptionPane.showMessageDialog(this, "Aucun employé trouvé avec cet ID.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void chargerEmployes() {
        try {
            List<employe> list = employedb.getAllEmployes();
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"ID", "Nom", "Prénom", "Poste", "Salaire", "Département"});

            for (employe e : list) {
                model.addRow(new Object[]{
                        e.getId(), e.getNom(), e.getPrenom(), e.getPoste(), e.getSalaire(), e.getIdDepartement()
                });
            }

            table.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nomField.setText("");
        prenomField.setText("");
        posteField.setText("");
        salaireField.setText("");
        deptField.setText("");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(page_employe::new);
    }
}