package controleur;

import java.util.ArrayList;
import java.util.List;
import modele.Produit;

public class ProduitController {
    private ProduitView vue;
    private ProduitDAO produitDAO;
    private VenteDAO venteDAO;


    public ProduitController(ProduitView vue, ProduitDAO produitDAO, VenteDAO venteDAO) {
        this.vue = vue;
        this.produitDAO = produitDAO;
        this.venteDAO = venteDAO;

        this.vue.setAjouterProduitListener(e -> {

            String nom = vue.getNomProduit();
            double prix = vue.getPrixProduit();
            Produit produit = new Produit(0, nom, prix); // L'id sera généré par la DB
            produitDAO.ajouterProduit(produit);
            JOptionPane.showMessageDialog(null, "Produit ajouté !");

        });

    }
}