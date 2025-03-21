package vue;

import controleur.VenteController;
import modele.User;

import javax.swing.*;
import java.awt.*;

public class GestionVente extends JFrame {
    private User utilisateur;
    private VenteController venteController;

    public GestionVente(User utilisateur) {
        this.utilisateur = utilisateur;
        this.venteController = new VenteController();

        setTitle("Gestion des Ventes");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Gestion
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les éléments
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Titre
        JLabel titleLabel = new JLabel("Gestion des Ventes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, gbc);

        JButton ajouterVenteButton = createStyledButton("Enregistrer une Vente");
        JButton consulterVentesButton = createStyledButton("Consulter les Ventes");
        JButton supprimerVenteButton = createStyledButton("Supprimer une Vente");
        JButton rapportVentesButton = createStyledButton("Rapport des Ventes");
        JButton btnRetour = createStyledButton("Retour");

        ajouterVenteButton.addActionListener(e -> {
            System.out.println("📢 Ouverture de l'interface d'enregistrement de vente...");
            new AjouterVenteView();
        });

        consulterVentesButton.addActionListener(e -> {
            System.out.println("📢 Ouverture de l'historique des ventes...");
            new ConsulterVentesView();
        });

        supprimerVenteButton.addActionListener(e -> {
            System.out.println("📢 Ouverture de la suppression de vente...");
            new SupprimerVenteView();
        });

        rapportVentesButton.addActionListener(e -> {
            System.out.println("📢 Ouverture du rapport des ventes...");
            new RapportVenteView();
        });

        // Désactivation du bouton suppression si l'utilisateur n'est pas autorisé
        if (!utilisateur.getRole().equals("admin")) {
            supprimerVenteButton.setEnabled(false); // Grise le bouton
        }

        add(ajouterVenteButton, gbc);
        add(consulterVentesButton, gbc);
        add(supprimerVenteButton, gbc);
        add(rapportVentesButton, gbc);



        gbc.insets = new Insets(20, 10, 10, 10); // Ajout d’un espacement supplémentaire en haut du bouton retour
        add(btnRetour, gbc);

        btnRetour.addActionListener(e -> dispose());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(211, 211, 211));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));


        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(169, 169, 169));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(211, 211, 211));
            }
        });

        return button;
    }
}
