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
        setLayout(new GridLayout(5, 2));
        setSize(400, 250);

        // Champs de saisie
        add(new JLabel("Nom du Produit:"));
        textFieldNom = new JTextField();
        add(textFieldNom);

        add(new JLabel("Prix:"));
        textFieldPrix = new JTextField();
        add(textFieldPrix);

        add(new JLabel("Quantité:"));
        textFieldQuantite = new JTextField();
        add(textFieldQuantite);

        btnAjouterProduit = new JButton("Ajouter Produit");
        btnRetour = new JButton("Retour");
        add(btnAjouterProduit);
        add(btnRetour);

        btnRetour.addActionListener(e -> dispose());
        btnAjouterProduit.addActionListener(e -> ajouterProduit());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
}