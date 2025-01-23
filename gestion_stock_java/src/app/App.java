package app;

import controleur.FournisseurControlleur;
import vue.FournisseurView;

public class App {
    public static void main(String[] args) {
        FournisseurControlleur fournisseurController = new FournisseurControlleur();
        FournisseurView fournisseurView = new FournisseurView(fournisseurController);
        fournisseurView.setVisible(true);
    }
}
