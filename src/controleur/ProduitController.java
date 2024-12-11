package controleur;

import java.util.ArrayList;
import java.util.List;
import modele.Produit;

public class ProduitController {
    private List<Produit> produits = new ArrayList<>();

    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public Produit trouverProduitParId(int id) {
        for (Produit produit : produits) {
            if (produit.getId() == id) {
                return produit;
            }
        }
        return null;
    }
}