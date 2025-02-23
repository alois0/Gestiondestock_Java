package controleur;

import modele.Connexion;
import modele.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    private Connection connection;
    private User utilisateurConnecte;

    public UserController() {

    }

    public User getUser(String nom, String motDePasse) {
        String query = "SELECT * FROM utilisateurs WHERE nom = ? AND mot_de_passe = ?";

        try (Connection connection = Connexion.getConnection(); // Correction ici
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nom);
            statement.setString(2, motDePasse);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Connexion utilisateur
    public boolean login(String nom, String motDePasse) {
        utilisateurConnecte = this.getUser(nom, motDePasse);
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
