package UI;

import javax.swing.*;
import java.awt.*;

public class page_projet extends JFrame {
    public page_projet() {
        setTitle("Gestion des Projets");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField id = new JTextField();
        JTextField nom = new JTextField();
        JTextField budget = new JTextField();
        JTextField departement = new JTextField();

        form.add(new JLabel("ID Projet"));
        form.add(id);
        form.add(new JLabel("Nom Projet"));
        form.add(nom);
        form.add(new JLabel("Budget"));
        form.add(budget);
        form.add(new JLabel("ID Département"));
        form.add(departement);

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Insérer"));
        buttons.add(new JButton("Supprimer"));

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        setVisible(true);
    }
}
