package vue;

import controleur.VenteController;
import modele.User;

import javax.swing.*;
import java.awt.*;

public class GestionVente extends JFrame {
    private User utilisateur;
    private VenteController venteController;

    public GestionVente(User utilisateur) {
        this.utilisateur = utilisateur;
        this.venteController = new VenteController();

        setTitle("Gestion des Ventes");
        setLayout(new FlowLayout());
        setSize(400, 200);

        JButton ajouterVenteButton = new JButton("Enregistrer une Vente");
        JButton consulterVentesButton = new JButton("Consulter les Ventes");
        JButton supprimerVenteButton = new JButton("Supprimer une Vente");
        JButton rapportVentesButton = new JButton("Rapport des Ventes");

        ajouterVenteButton.addActionListener(e -> {
            System.out.println("📢 Ouverture de l'interface d'enregistrement de vente...");
            new AjouterVenteView(); // Ouvre la vue pour enregistrer une vente
        });

        consulterVentesButton.addActionListener(e -> {
            System.out.println("📢 Ouverture de l'historique des ventes...");
            new ConsulterVentesView(); // Ouvre la vue de consultation des ventes
        });

        supprimerVenteButton.addActionListener(e -> {
            System.out.println("📢 Ouverture de la suppression de vente...");
            new SupprimerVenteView(); // Ouvre la vue de suppression de vente
        });

        rapportVentesButton.addActionListener(e -> {
            System.out.println("📢 Ouverture du rapport des ventes...");
            new RapportVenteView(); // Ouvre la vue du rapport des ventes
        });

        // Désactivation du bouton suppression si l'utilisateur n'est pas autorisé
        if (!utilisateur.getRole().equals("manager")) {
            supprimerVenteButton.setEnabled(false); // Grise le bouton
        }

        add(ajouterVenteButton);
        add(consulterVentesButton);
        add(supprimerVenteButton);
        add(rapportVentesButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
