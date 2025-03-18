package vue;

import controleur.UserController;
import modele.User;

import javax.swing.*;
import java.awt.*;

public class GestionUtilisateurMenu extends JFrame {
    private User utilisateur;

    private UserController userController;

    public GestionUtilisateurMenu(User utilisateur) {
        this.utilisateur = utilisateur;
        this.userController = new UserController();

        setTitle("Gestion des Utilisateurs");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel titleLabel = new JLabel("Gestion des Utilisateurs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, gbc);

        // 📌 Boutons pour gérer les utilisateurs
        JButton btnAjouter = createStyledButton("Ajouter Utilisateur");
        JButton btnModifier = createStyledButton("Modifier Utilisateur");
        JButton btnSupprimer = createStyledButton("Supprimer Utilisateur");
        JButton btnQuitter = createStyledButton("Quitter");

        btnAjouter.addActionListener(e -> new AjouterUtilisateurView(utilisateur));
        btnModifier.addActionListener(e -> new ModifierUtilisateurView(utilisateur));
        btnSupprimer.addActionListener(e -> new SupprimerUtilisateurView(utilisateur));
        btnQuitter.addActionListener(e -> {

            userController.logout();
            dispose();
            new LoginView();
        });

        add(btnAjouter, gbc);
        add(btnModifier, gbc);
        add(btnSupprimer, gbc);
        add(btnQuitter, gbc);

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
