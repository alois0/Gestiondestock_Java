package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import controleur.UserController;
import modele.User;

public class Menuview extends JFrame {

    private User utilisateur;

    private UserController userController;


    public Menuview(User utilisateur) {
        this.utilisateur = utilisateur;
        this.userController = new UserController();

        setTitle("Menu Principal");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Gestionnaire de positionnement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les éléments
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Titre
        JLabel titleLabel = new JLabel("Menu Principal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, gbc);

        JButton gestionProduitButton = createStyledButton("Gestion Produit");
        JButton gestionVenteButton = createStyledButton("Gestion Ventes");
        JButton gestionFournisseurButton = createStyledButton("Gestion Fournisseur");
        JButton btnQuitter = createStyledButton("Quitter");


        gestionProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionProduit(utilisateur);
            }
        });

        gestionFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionFournisseur(utilisateur);
            }
        });

        gestionVenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionVente(utilisateur);
            }
        });


        btnQuitter.addActionListener(e -> {

            userController.logout();
            dispose(); // Ferme le menu
            new LoginView(); // Ouvre la fenêtre de connexion
        });







        add(gestionProduitButton, gbc);
        add(gestionVenteButton, gbc);
        add(gestionFournisseurButton, gbc);
        add(btnQuitter, gbc);



        // Désactivation des options en fonction du r

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