package vue;

import controleur.ProduitController;
import modele.DAO.VenteDAO;
import modele.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionFournisseur extends JFrame {
    private User utilisateur;

    private ProduitController produitController;
    public GestionFournisseur(User utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Gestion des Produits");
        setLayout(new FlowLayout());
        setSize(400, 200);

        JButton ajouterFournisseurButton = new JButton("Ajouter Fournisseur");
        JButton modifierFournisseurButton = new JButton("Modifier Fournisseur");
        JButton supprimerFournisseurButton = new JButton("Supprimer Fournisseur");
        JButton ConsulterFournisseurButton = new JButton("Consulter Fournisseur");
        JButton AssocierProduitFournisseurButton = new JButton( ("Associer Produit"));

        ajouterFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("📢 Ouverture de l'interface d'ajout de fournisseur...");

                AjouterFournisseurView vue = new AjouterFournisseurView(utilisateur);
            }
        });

        modifierFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("📢 Ouverture de l'interface de modification de fournisseur...");

                ModifierFournisseurView vue = new ModifierFournisseurView(utilisateur);
            }
        });

        supprimerFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("📢 Ouverture de l'interface de suppression de fournisseur...");

                SupprimerFournisseurView vue = new SupprimerFournisseurView(utilisateur);


            }
        });


        ConsulterFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("📢 Ouverture de l'interface de consultation de fournisseur...");

                ConsulterFournisseurView vue = new ConsulterFournisseurView();


            }
        });


        AssocierProduitFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("📢 Ouverture de l'interface de consultation de fournisseur...");

               AssocierProduitFournisseurView vue = new AssocierProduitFournisseurView();


            }
        });

// Désactivation du bouton suppression si l'utilisateur N'EST PAS un manager
        if (!utilisateur.getRole().equals("manager")) {
            supprimerFournisseurButton.setEnabled(false); // Grise le bouton
        }

        add(ajouterFournisseurButton);
        add(modifierFournisseurButton);
        add(supprimerFournisseurButton);
        add(ConsulterFournisseurButton);
        add(AssocierProduitFournisseurButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
