package vue;

import controleur.ProduitController;
import modele.Produit;
import modele.User;

import javax.swing.*;
import java.awt.*;

public class AjouterProduitView extends JFrame {
    private JTextField textFieldNom;
    private JTextField textFieldPrix;
    private JTextField textFieldQuantite;
    private JButton btnAjouterProduit;
    private JButton btnRetour;
    private ProduitController produitController;
    private User utilisateur;

    public AjouterProduitView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.produitController = new ProduitController();

        setTitle("Ajouter un Produit");
        setSize(400, 250);
        // Centrer la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Gestionnaire de positionnement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); // Espacement uniforme
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Ajouter un Produit");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2; // Prend toute la ligne
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Champs de saisie bien alignés
        gbc.gridwidth = 1; // Retour à une colonne normale
        addLabel("Nom du Produit:", gbc, 1);
        textFieldNom = new JTextField(15);
        styliserChamp(textFieldNom);
        addField(textFieldNom, gbc, 1);

        addLabel("Prix:", gbc, 2);
        textFieldPrix = new JTextField(15);
        styliserChamp(textFieldPrix);
        addField(textFieldPrix, gbc, 2);

        addLabel("Quantité:", gbc, 3);
        textFieldQuantite = new JTextField(15);
        styliserChamp(textFieldQuantite);
        addField(textFieldQuantite, gbc, 3);

        // Boutons côte à côte
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnAjouterProduit = createStyledButton("Ajouter Produit");
        btnRetour = createStyledButton("Retour");
        buttonPanel.add(btnAjouterProduit);
        buttonPanel.add(btnRetour);

        gbc.gridwidth = 2;
        gbc.gridy = 4;
        add(buttonPanel, gbc);

        // Événements
        btnRetour.addActionListener(e -> dispose());
        btnAjouterProduit.addActionListener(e -> ajouterProduit());

        setVisible(true);
    }

    private void ajouterProduit() {
        String nom = textFieldNom.getText().trim();
        double prix;
        int quantite;

        try {
            prix = Double.parseDouble(textFieldPrix.getText().trim());
            quantite = Integer.parseInt(textFieldQuantite.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs valides pour le prix et la quantité !");
            return;
        }

        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le nom du produit ne peut pas être vide !");
            return;
        }

        Produit produit = new Produit(0, nom, prix, quantite);
        produitController.ajouterProduit(produit);
        JOptionPane.showMessageDialog(null, "Produit ajouté avec succès !");

        // Effacer les champs après l'ajout
        textFieldNom.setText("");
        textFieldPrix.setText("");
        textFieldQuantite.setText("");
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

    private void styliserChamp(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14)); // Police et taille du texte
        champ.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Bordure grise
        champ.setBackground(new Color(240, 240, 240)); // Fond gris clair
        champ.setForeground(Color.BLACK); // Texte noir
        champ.setOpaque(true);
        champ.setPreferredSize(new Dimension(200, 30)); // Taille du champ
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding interne
        ));

        // Effet au survol (hover)
        champ.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(220, 220, 220)); // Gris plus foncé
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(240, 240, 240)); // Retour à la couleur normale
            }
        });
    }

}