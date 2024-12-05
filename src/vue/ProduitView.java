package vue;

import controleur.ProduitController;
import modele.Produit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProduitView extends JFrame {
    private ProduitController controller;

    public ProduitView(ProduitController controller) {
        this.controller = controller;

        setTitle("Gestion des Produits");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JTextField nomField = new JTextField(15);
        JTextField prixField = new JTextField(10);
        JButton ajouterButton = new JButton("Ajouter");

        panel.add(new JLabel("Nom : "));
        panel.add(nomField);
        panel.add(new JLabel("Prix : "));
        panel.add(prixField);
        panel.add(ajouterButton);

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                double prix = Double.parseDouble(prixField.getText());
                Produit produit = new Produit(0, nom, prix, 0);
                controller.ajouterProduit(produit);
                JOptionPane.showMessageDialog(null, "Produit ajouté !");
            }
        });

        add(panel);
    }
}