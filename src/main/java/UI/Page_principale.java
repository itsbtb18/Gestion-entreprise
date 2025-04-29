package UI;

import javax.swing.*;
import java.awt.*;

public class Page_principale extends JFrame {

    public Page_principale() {
        setTitle("Gestion d'Entreprise - Accueil");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centre la fenêtre
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 245)); // Fond gris clair

        // Création du panneau principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre en haut
        JLabel labelTitre = new JLabel("Gestion d'Entreprise", SwingConstants.CENTER);
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        labelTitre.setForeground(new Color(50, 50, 50));
        mainPanel.add(labelTitre, BorderLayout.NORTH);

        // Panneau pour les boutons
        JPanel panelBoutons = new JPanel(new GridLayout(5, 1, 15, 15));
        panelBoutons.setBackground(new Color(245, 245, 245));

        // Création des boutons stylés
        JButton btnEmploye = createStyledButton("Gérer Employés");
        JButton btnDepartement = createStyledButton("Gérer Départements");
        JButton btnProjet = createStyledButton("Gérer Projets");
        JButton btnTravail = createStyledButton("Gérer Travaux");
        JButton btnRequetes = createStyledButton("Requêtes SQL");

        // Ajout des boutons
        panelBoutons.add(btnEmploye);
        panelBoutons.add(btnDepartement);
        panelBoutons.add(btnProjet);
        panelBoutons.add(btnTravail);
        panelBoutons.add(btnRequetes);

        // Ajouter le panneau de boutons au centre
        mainPanel.add(panelBoutons, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        // Actions des boutons
        btnEmploye.addActionListener(e -> new page_employe());
        btnDepartement.addActionListener(e -> new page_departement());
        btnProjet.addActionListener(e -> new page_projet());
        btnTravail.addActionListener(e -> new page_travail());
        btnRequetes.addActionListener(e -> new page_requetes());
    }

    // Méthode pour créer un bouton stylé
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180)); // Bleu acier
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Page_principale());
    }
}
