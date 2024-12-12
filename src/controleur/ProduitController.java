package controleur;

import modele.Produit;
import java.util.ArrayList;
import java.util.List;

public class ProduitController {
    private List<Produit> produits = new ArrayList<>();
    private int nextId = 1;

    // create
    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    // read
    public List<Produit> getProduits() {
        return produits;
    }

    // update
    public Produit trouverProduitParId(int id) {
        for (Produit produit : produits) {
            if (produit.getId() == id) {
                return produit;
            }
        }
        return null;
    }
}
