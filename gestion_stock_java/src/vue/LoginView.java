package vue;

import controleur.LoginController;
import modele.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField textFieldNom;
    private JPasswordField passwordField;
    private JButton btnConnexion;
    private JButton btnQuitter;
    private LoginController loginController;

    public LoginView() {
        loginController = new LoginController();

        setTitle("Connexion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel titleLabel = new JLabel("Connexion");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, gbc);

        add(new JLabel("Nom d'utilisateur:"), gbc);
        textFieldNom = new JTextField(15);
        styliserChamp(textFieldNom);
        add(textFieldNom, gbc);

        add(new JLabel("Mot de passe:"), gbc);
        passwordField = new JPasswordField(15);
        styliserChamp(passwordField);
        add(passwordField, gbc);

        btnConnexion = createStyledButton("Se connecter");
        btnQuitter = createStyledButton("Quitter");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnConnexion);
        panelButtons.add(btnQuitter);

        add(panelButtons, gbc);

        btnQuitter.addActionListener(e -> System.exit(0));
        btnConnexion.addActionListener(e -> verifierLogin());

        setVisible(true);
    }

    private void verifierLogin() {
        String nom = textFieldNom.getText().trim();
        String motDePasse = new String(passwordField.getPassword()).trim();

        if (nom.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User utilisateur = loginController.verifierUtilisateur(nom, motDePasse);
        if (utilisateur != null) {
            JOptionPane.showMessageDialog(this, "Connexion réussie ! Bienvenue, " + utilisateur.getNom(), "Succès", JOptionPane.INFORMATION_MESSAGE);

            // Si l'utilisateur est ADMIN, on affiche un choix
            if (utilisateur.getRole().equalsIgnoreCase("admin")) {
                int choix = JOptionPane.showOptionDialog(
                        this,
                        "Vous êtes connecté en tant qu'administrateur.\nOù souhaitez-vous aller ?",
                        "Choix de Navigation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Menu Principal", "Gestion des Utilisateurs"},
                        "Menu Principal");

                if (choix == 0) {
                    new Menuview(utilisateur); // Aller au menu principal
                } else if (choix == 1) {
                    new GestionUtilisateurMenu(utilisateur); // Aller à la gestion des utilisateurs
                }
            } else {
                // Si ce n'est pas un admin, direction le menu principal directement
                new Menuview(utilisateur);
            }

            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
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

    private void styliserChamp(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        champ.setBackground(new Color(240, 240, 240));
        champ.setForeground(Color.BLACK);
        champ.setOpaque(true);
        champ.setPreferredSize(new Dimension(200, 30));
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        champ.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(240, 240, 240));
            }
        });
    }

}
