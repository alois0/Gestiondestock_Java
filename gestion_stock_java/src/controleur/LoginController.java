package controleur;

import modele.Connexion;
import modele.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    public User verifierUtilisateur(String nom, String motDePasse) {
        String sql = "SELECT * FROM users WHERE nom = ? AND mot_de_passe = ?"; // ⚠️ À sécuriser avec un hachage plus tard

        try (Connection conn = Connexion.getConnection(); // Utilisation de ta classe Connexion
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            stmt.setString(2, motDePasse);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("nom"), rs.getString("mot_de_passe"), rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retourne null si l'utilisateur n'existe pas
    }
}
