package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import modele.User;

public class Menuview extends JFrame {

    private User utilisateur;

    public Menuview(User utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("Menu Principal");
        setLayout(new FlowLayout());
        setSize(400, 200);

        JButton gestionProduitButton = new JButton("Gestion Produit");
        JButton gestionVenteButton = new JButton("Gestion Ventes");
        JButton gestionFournisseurButton = new JButton("Gestion Fournisseur");

        gestionProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionProduit(utilisateur);
            }
        });

        gestionVenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VenteView();
            }
        });

        gestionFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionFournisseur(utilisateur);
            }
        });

        add(gestionProduitButton);
        add(gestionVenteButton);
        add(gestionFournisseurButton);

        // Désactivation des options en fonction du r

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}