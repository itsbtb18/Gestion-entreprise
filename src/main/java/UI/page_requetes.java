package UI;

import javax.swing.*;
import java.awt.*;

public class page_requetes extends JFrame {
    public page_requetes() {
        setTitle("Exécution de Requêtes SQL");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea(5, 50);
        JTable table = new JTable();
        JButton exec = new JButton("Exécuter");

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createTitledBorder("Saisir la requête SQL"));
        top.add(new JScrollPane(area), BorderLayout.CENTER);
        top.add(exec, BorderLayout.EAST);

        JScrollPane result = new JScrollPane(table);
        result.setBorder(BorderFactory.createTitledBorder("Résultat"));

        add(top, BorderLayout.NORTH);
        add(result, BorderLayout.CENTER);
        setVisible(true);
    }
}
