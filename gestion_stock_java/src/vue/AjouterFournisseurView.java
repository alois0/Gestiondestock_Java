package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.User;

import javax.swing.*;
import java.awt.*;

public class AjouterFournisseurView extends JFrame {
    private JTextField textFieldNom;
    private JTextField textFieldContact;
    private JButton btnAjouterFournisseur;
    private JButton btnRetour;
    private FournisseurController fournisseurController;
    private User utilisateur;

    public AjouterFournisseurView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.fournisseurController = new FournisseurController();

        setTitle("Ajouter un Fournisseur");
        setSize(500, 300);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Gestionnaire de positionnement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement uniforme
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;

        // **Ajout du titre**
        JLabel titleLabel = new JLabel("Ajouter un Fournisseur");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Le titre prend toute la largeur
        add(titleLabel, gbc);

        // **Champs de saisie**
        gbc.gridwidth = 1; // Retour à une seule colonne
        addLabel("Nom du Fournisseur:", gbc, 1);
        textFieldNom = new JTextField(15);
        addField(textFieldNom, gbc, 1);

        addLabel("Contact:", gbc, 2);
        textFieldContact = new JTextField(15);
        addField(textFieldContact, gbc, 2);

        // **Boutons bien alignés en bas**
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAjouterFournisseur = createStyledButton("Ajouter Fournisseur");
        btnRetour = createStyledButton("Retour");
        buttonPanel.add(btnAjouterFournisseur);
        buttonPanel.add(btnRetour);

        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(buttonPanel, gbc);

        // **Événements**
        btnRetour.addActionListener(e -> dispose());
        btnAjouterFournisseur.addActionListener(e -> ajouterFournisseur());

        setVisible(true);
    }

    private void ajouterFournisseur() {
        String nom = textFieldNom.getText().trim();
        String contact = textFieldContact.getText().trim();

        if (nom.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs !");
            return;
        }

        Fournisseur fournisseur = new Fournisseur(0, nom, contact);
        fournisseurController.ajouterFournisseur(fournisseur);
        JOptionPane.showMessageDialog(null, "Fournisseur ajouté avec succès !");

        // Effacer les champs après l'ajout
        textFieldNom.setText("");
        textFieldContact.setText("");
    }

    private void addLabel(String text, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel(text), gbc);
    }

    private void addField(JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(field, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Texte plus grand
        button.setBackground(new Color(211, 211, 211)); // Gris clair (Light Gray)
        button.setForeground(Color.BLACK); // Texte en noir
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Bordure fine en gris
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding interne

        // Effet au survol (hover)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(169, 169, 169)); // Gris plus foncé (Dark Gray)
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(211, 211, 211)); // Retour à la couleur normale
            }
        });

        return button;
    }
}
