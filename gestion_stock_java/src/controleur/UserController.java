package controleur;

import modele.DAO.UserDAO;
import modele.User;

public class UserController {
    private UserDAO userDAO;
    private User utilisateurConnecte;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    // Connexion utilisateur
    public boolean login(String nom, String motDePasse) {
        utilisateurConnecte = userDAO.getUser(nom, motDePasse);
        if (utilisateurConnecte != null) {
            System.out.println("Connexion réussie ! Bienvenue, " + utilisateurConnecte.getNom() + " 🎉");
            return true;
        } else {
            System.out.println("Échec de connexion. Vérifiez vos identifiants.");
            return false;
        }
    }

    // Afficher les infos de l'utilisateur connecté
    public void afficherUtilisateurConnecte() {
        if (utilisateurConnecte != null) {
            System.out.println("Utilisateur connecté : " + utilisateurConnecte);
        } else {
            System.out.println("Aucun utilisateur connecté.");
        }
    }

    // Déconnexion
    public void logout() {
        if (utilisateurConnecte != null) {
            System.out.println("Déconnexion de " + utilisateurConnecte.getNom() + "...");
            utilisateurConnecte = null;
        } else {
            System.out.println("Aucun utilisateur n'est actuellement connecté.");
        }
    }
}
