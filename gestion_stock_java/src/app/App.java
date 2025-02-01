package app;

import modele.Connexion;
import vue.Menuview;


import modele.User;

public class App {
    public static void main(String[] args) {
        System.out.println("🔹 Lancement de l'application...");

        // 📌 Vérification de la connexion à la base de données
        if (Connexion.getConnection() == null) {
            System.err.println("❌ ERREUR : Impossible de se connecter à la base de données !");
            return;
        }
        System.out.println("✅ Connexion réussie à la base de données !");

        // 📌 Création d'un utilisateur simulé (Admin Manager)
        User utilisateurSimule = new User(1, "admin", "password123", "manager");

        // 📌 Vérification que l'utilisateur est bien créé
        if (utilisateurSimule == null) {
            System.err.println("❌ ERREUR : L'utilisateur simulé est null !");
            return;
        }
        System.out.println("👤 Utilisateur simulé : " + utilisateurSimule.getNom() + " (Rôle: " + utilisateurSimule.getRole() + ")");

        // 📌 Lancement du menu principal
        new Menuview(utilisateurSimule);

        System.out.println("✅ Application lancée avec succès !");
    }
}
