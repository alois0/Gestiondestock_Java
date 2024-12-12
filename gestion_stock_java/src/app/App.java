package app;

import controleur.FournisseurControler;
import vue.FournisseurView;

public class App {
    public static void main(String[] args) {
        // Initialiser le contrôleur des fournisseurs
        FournisseurControler fournisseurController = new FournisseurControler();

        // Initialiser la vue des fournisseurs et l'attacher au contrôleur
        FournisseurView fournisseurView = new FournisseurView(fournisseurController);

        // Rendre la vue visible
        fournisseurView.setVisible(true);
    }
}
