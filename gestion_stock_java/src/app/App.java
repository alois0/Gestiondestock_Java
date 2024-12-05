package app;

import controleur.ProduitController;
import vue.ProduitView;

public class App {
    public static void main(String[] args) {
        ProduitController produitController = new ProduitController();
        ProduitView produitView = new ProduitView(produitController);
        produitView.setVisible(true);
    }
}
