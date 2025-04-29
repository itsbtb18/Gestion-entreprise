package UI;


import javax.swing.*;
import java.awt.*;

public class page_departement extends JFrame {
    public page_departement() {
        setTitle("Gestion des Départements");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField id = new JTextField();
        JTextField nom = new JTextField();
        JTextField responsable = new JTextField();

        form.add(new JLabel("ID Département"));
        form.add(id);
        form.add(new JLabel("Nom Département"));
        form.add(nom);
        form.add(new JLabel("ID Responsable"));
        form.add(responsable);

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Insérer"));
        buttons.add(new JButton("Supprimer"));

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        setVisible(true);
    }
}
