package vue;

import controleur.ProduitController;
import modele.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionProduit extends JFrame {
    private User utilisateur;

    private ProduitController produitController;
    public GestionProduit(User utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Gestion des Produits");
        setLayout(new FlowLayout());
        setSize(400, 200);

        JButton ajouterProduitButton = new JButton("Ajouter Produit");
        JButton modifierProduitButton = new JButton("Modifier Produit");
        JButton supprimerProduitButton = new JButton("Supprimer Produit");
        JButton consulterProduitButton = new JButton("Consulter Produits");

        ajouterProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("📢 Ouverture de l'interface d'ajout de produit...");

                // 📌 Création de ProduitView SEULEMENT quand on clique
                AjouterProduitView vue = new AjouterProduitView(utilisateur);
                ProduitController controller = new ProduitController();

            }
        });

        modifierProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("📢 Ouverture de l'interface de modification de produit...");

                ModifierProduitView vue = new ModifierProduitView(utilisateur);
            }
        });

        supprimerProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("📢 Ouverture de l'interface de suppression de produit...");
                SupprimerProduitView vue = new SupprimerProduitView(utilisateur);
            }
        });


        consulterProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("📢 Ouverture de l'interface de consultation des produits...");
                new ConsulterProduitView(); // Ouvre la page de consultation
            }
        });

// Désactivation du bouton suppression si l'utilisateur N'EST PAS un manager
        if (!utilisateur.getRole().equals("manager")) {
            supprimerProduitButton.setEnabled(false); // Grise le bouton
        }

        add(ajouterProduitButton);
        add(modifierProduitButton);
        add(supprimerProduitButton);
        add(consulterProduitButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
