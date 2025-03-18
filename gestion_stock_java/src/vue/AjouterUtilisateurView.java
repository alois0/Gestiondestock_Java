package vue;

import controleur.UserController;
import modele.User;

import javax.swing.*;
import java.awt.*;

public class AjouterUtilisateurView extends JFrame {
    private JTextField textFieldNom;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton btnAjouterUtilisateur;
    private JButton btnRetour;
    private UserController userController;
    private User utilisateur;

    public AjouterUtilisateurView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.userController = new UserController();

        setTitle("Ajouter un Utilisateur");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Ajouter un Utilisateur");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Champs de saisie
        gbc.gridwidth = 1;
        addLabel("Nom d'utilisateur:", gbc, 1);
        textFieldNom = new JTextField(15);
        styliserChamp(textFieldNom);
        addField(textFieldNom, gbc, 1);

        addLabel("Mot de passe:", gbc, 2);
        passwordField = new JPasswordField(15);
        styliserChamp(passwordField);
        addField(passwordField, gbc, 2);

        addLabel("Rôle:", gbc, 3);
        String[] roles = {"admin", "manager", "user"};
        roleComboBox = new JComboBox<>(roles);
        styliserChamp(roleComboBox);
        addField(roleComboBox, gbc, 3);

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnAjouterUtilisateur = createStyledButton("Ajouter Utilisateur");
        btnRetour = createStyledButton("Retour");
        buttonPanel.add(btnAjouterUtilisateur);
        buttonPanel.add(btnRetour);

        gbc.gridwidth = 2;
        gbc.gridy = 4;
        add(buttonPanel, gbc);

        // Événements
        btnRetour.addActionListener(e -> dispose());
        btnAjouterUtilisateur.addActionListener(e -> ajouterUtilisateur());

        setVisible(true);
    }

    private void ajouterUtilisateur() {
        String nom = textFieldNom.getText().trim();
        String motDePasse = new String(passwordField.getPassword()).trim();
        String role = (String) roleComboBox.getSelectedItem();

        if (nom.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis !");
            return;
        }

        boolean success = userController.ajouterUtilisateur(nom, motDePasse, role);
        if (success) {
            JOptionPane.showMessageDialog(null, "Utilisateur ajouté avec succès !");
            textFieldNom.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'utilisateur !");
        }
    }

    private void addLabel(String text, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel(text), gbc);
    }

    private void addField(JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(field, gbc);
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

    private void styliserChamp(JComponent champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        champ.setBackground(new Color(240, 240, 240));
        champ.setForeground(Color.BLACK);
        champ.setOpaque(true);
        champ.setPreferredSize(new Dimension(200, 30));
    }
}
