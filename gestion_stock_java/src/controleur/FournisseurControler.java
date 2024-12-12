package controleur;

import modele.Fournisseur;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FournisseurControler {
    private List<Fournisseur> fournisseurs;
    private Connection connection;

    public FournisseurControler() {
        this.fournisseurs = new ArrayList<>();
        this.connection = DatabaseConnection.getConnection();
        chargerFournisseursDepuisDB();
    }

    // Charger les fournisseurs depuis la base de données
    private void chargerFournisseursDepuisDB() {
        String sql = "SELECT * FROM fournisseur";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id_Fournisseur");
                String nom = resultSet.getString("nom");
                String contact = resultSet.getString("telephone");
                Fournisseur fournisseur = new Fournisseur(id, nom, contact, null);
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement des fournisseurs depuis la base de données.");
        }
    }

    // Ajouter un fournisseur à la base de données et à la liste
    public void ajouterFournisseur(Fournisseur fournisseur) {
        String sql = "INSERT INTO fournisseur (nom, telephone) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, fournisseur.getNom());
            statement.setString(2, fournisseur.getContact());
            statement.executeUpdate();

            // Récupérer l'ID généré
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                fournisseur.setId(generatedKeys.getInt(1));
            }

            // Ajouter à la liste locale
            fournisseurs.add(fournisseur);
            System.out.println("Fournisseur ajouté : " + fournisseur);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ajout du fournisseur dans la base de données.");
        }
    }

    // Obtenir la liste des fournisseurs
    public List<Fournisseur> getFournisseurs() {
        return fournisseurs;
    }

    // Trouver un fournisseur par ID (depuis la liste locale)
    public Fournisseur trouverFournisseurParId(int id) {
        for (Fournisseur fournisseur : fournisseurs) {
            if (fournisseur.getId() == id) {
                return fournisseur;
            }
        }
        return null;
    }

    // Supprimer un fournisseur par ID
    public void supprimerFournisseur(int id) {
        String sql = "DELETE FROM fournisseur WHERE id_Fournisseur = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();

            // Supprimer de la liste locale
            fournisseurs.removeIf(fournisseur -> fournisseur.getId() == id);
            System.out.println("Fournisseur avec l'ID " + id + " supprimé.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la suppression du fournisseur dans la base de données.");
        }
    }

    public List<Fournisseur> rechercherFournisseurParNom(String nom) {
        List<Fournisseur> resultats = new ArrayList<>();
        for (Fournisseur fournisseur : fournisseurs) {
            if (fournisseur.getNom().toLowerCase().contains(nom.toLowerCase())) {
                resultats.add(fournisseur);
            }
        }
        return resultats;
    }
}
