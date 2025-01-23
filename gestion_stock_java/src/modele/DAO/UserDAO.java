package modele.DAO;
import java.sql.*;
import modele.User;
import modele.Connexion;

public class UserDAO {
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
}
