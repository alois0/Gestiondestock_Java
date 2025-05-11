package vue;

import controleur.ProduitController;
import controleur.FournisseurController;
import modele.Produit;
import modele.Fournisseur;
import modele.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AjouterProduitView extends JFrame {
    private JTextField textFieldNom;
    private JTextField textFieldPrix;
    private JTextField textFieldQuantite;
    private JComboBox<String> comboBoxFournisseur;
    private JButton btnAjouterProduit;
    private JButton btnRetour;
    private ProduitController produitController;
    private FournisseurController fournisseurController;
    private User utilisateur;

    public AjouterProduitView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.produitController = new ProduitController();
        this.fournisseurController = new FournisseurController();

        setTitle("Ajouter un Produit");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Ajouter un Produit");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
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

        addLabel("Fournisseur:", gbc, 4);
        comboBoxFournisseur = new JComboBox<>();
        chargerFournisseurs();
        addField(comboBoxFournisseur, gbc, 4);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnAjouterProduit = createStyledButton("Ajouter Produit");
        btnRetour = createStyledButton("Retour");
        buttonPanel.add(btnAjouterProduit);
        buttonPanel.add(btnRetour);

        gbc.gridwidth = 2;
        gbc.gridy = 5;
        add(buttonPanel, gbc);

        btnRetour.addActionListener(e -> dispose());
        btnAjouterProduit.addActionListener(e -> ajouterProduit());

        setVisible(true);
    }

    private void chargerFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurController.getFournisseurs();
        comboBoxFournisseur.addItem("Sélectionnez un fournisseur");
        for (Fournisseur fournisseur : fournisseurs) {
            comboBoxFournisseur.addItem(fournisseur.getId() + " - " + fournisseur.getNom());
        }
    }

    private void ajouterProduit() {
        String nom = textFieldNom.getText().trim();
        double prix;
        int quantite;

        try {
            prix = Double.parseDouble(textFieldPrix.getText().trim());
            quantite = Integer.parseInt(textFieldQuantite.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs valides pour le prix et la quantité !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le nom du produit ne peut pas être vide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (comboBoxFournisseur.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fournisseur !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //  Récupération de l'ID du fournisseur
        String selectedItem = (String) comboBoxFournisseur.getSelectedItem();
        int fournisseurId = 0;

        if (selectedItem != null && selectedItem.contains(" - ")) {
            try {
                fournisseurId = Integer.parseInt(selectedItem.split(" - ")[0].trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la récupération de l'ID fournisseur !", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sélection du fournisseur invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("ID du fournisseur sélectionné : " + fournisseurId); // 🔎 Debug

        if (fournisseurId == 0) {
            JOptionPane.showMessageDialog(null, "Erreur : Aucun fournisseur valide sélectionné !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Produit produit = new Produit(0, nom, prix, quantite, fournisseurId);
        boolean success = produitController.ajouterProduit(produit);

        if (success) {
            JOptionPane.showMessageDialog(null, "Produit ajouté avec succès !");
            textFieldNom.setText("");
            textFieldPrix.setText("");
            textFieldQuantite.setText("");
            comboBoxFournisseur.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du produit !", "Erreur", JOptionPane.ERROR_MESSAGE);
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
    }
}
