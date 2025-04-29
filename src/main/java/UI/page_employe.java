package UI;

import org.example.employe;
import db.employedb;
import javax.swing.*;
import java.awt.*;

public class page_employe extends JFrame {
    public page_employe() {
        setTitle("Gestion des Employés");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Nom", "Prénom", "Poste", "Salaire", "Département"};
        JTable table = new JTable(new Object[0][columnNames.length], columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        JTextField[] fields = new JTextField[6];
        String[] labels = {"ID", "Nom", "Prénom", "Poste", "Salaire", "ID Département"};

        for (int i = 0; i < labels.length; i++) {
            form.add(new JLabel(labels[i]));
            fields[i] = new JTextField();
            form.add(fields[i]);
        }

        JPanel buttons = new JPanel();
        JButton insert = new JButton("Insérer");
        JButton delete = new JButton("Supprimer");
        JButton search = new JButton("Rechercher");
        buttons.add(insert);
        buttons.add(delete);
        buttons.add(search);

        insert.addActionListener(e -> {
            try {
                int id = Integer.parseInt(fields[0].getText());
                String nom = fields[1].getText();
                String prenom = fields[2].getText();
                String poste = fields[3].getText();
                double salaire = Double.parseDouble(fields[4].getText());
                int idDept = Integer.parseInt(fields[5].getText());
                employe emp = new employe(id, nom, prenom, poste, salaire, idDept);
                employedb.insertemploye(emp);
                JOptionPane.showMessageDialog(this, "Employé inséré avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });



        add(scrollPane, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        setVisible(true);
    }
}
