package UI;

import javax.swing.*;
import java.awt.*;

public class page_travail extends JFrame {
    public page_travail() {
        setTitle("Gestion des Affectations (Travail)");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField idEmp = new JTextField();
        JTextField idProj = new JTextField();
        JTextField date = new JTextField();

        form.add(new JLabel("ID Employé"));
        form.add(idEmp);
        form.add(new JLabel("ID Projet"));
        form.add(idProj);
        form.add(new JLabel("Date Affectation (YYYY-MM-DD)"));
        form.add(date);

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Affecter"));
        buttons.add(new JButton("Supprimer"));

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        setVisible(true);
    }
}
