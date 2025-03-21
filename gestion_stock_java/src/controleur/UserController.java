package controleur;

import modele.Connexion;
import modele.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    private Connection connection;
    private User utilisateurConnecte;

    public UserController() {
        this.connection = Connexion.getConnection();
    }

    public List<User> getUtilisateurs() {
        List<User> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String motDePasse = resultSet.getString("mot_de_passe");
                String role = resultSet.getString("role");

                utilisateurs.add(new User(id, nom, motDePasse, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la récupération des utilisateurs.");
        }
        return utilisateurs;
    }

    public boolean ajouterUtilisateur(String nom, String motDePasse, String role) {
        String sql = "INSERT INTO users (nom, mot_de_passe, role) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, motDePasse);
            stmt.setString(3, role);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Modifier un utilisateur
    public boolean modifierUtilisateur(int id, String nom, String motDePasse, String role) {
        String sql = "UPDATE users SET nom = ?, mot_de_passe = ?, role = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, motDePasse);
            stmt.setString(3, role);
            stmt.setInt(4, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Supprimer un utilisateur
    public boolean supprimerUtilisateur(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<User> rechercherUtilisateurs(String recherche) {
        List<User> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE nom LIKE ? OR role LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + recherche + "%");
            statement.setString(2, "%" + recherche + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String motDePasse = resultSet.getString("mot_de_passe");
                String role = resultSet.getString("role");

                utilisateurs.add(new User(id, nom, motDePasse, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la recherche des utilisateurs.");
        }
        return utilisateurs;
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
