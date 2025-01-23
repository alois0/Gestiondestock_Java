package controleur;

import modele.Fournisseur;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FournisseurControlleur {
    private List<Fournisseur> fournisseurs;
    private Connection connection;

    public FournisseurControlleur() {
        this.fournisseurs = new ArrayList<>();
        this.connection = DatabaseConnection.getConnection();
        getFournisseur();
    }

    public List<Fournisseur> getFournisseur() {
        String sql = "SELECT * FROM fournisseur";
        List<Fournisseur> fournisseurs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String contact = resultSet.getString("contact");
                Fournisseur fournisseur = new Fournisseur(id, nom, contact, null);
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement des fournisseurs.");
        }
        return fournisseurs;
    }

    public void ajouterFournisseur(Fournisseur fournisseur) {
        String sql = "INSERT INTO fournisseur (nom, contact) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, fournisseur.getNom());
            statement.setString(2, fournisseur.getContact());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                fournisseur.setId(generatedKeys.getInt(1));
            }

            fournisseurs.add(fournisseur);
            System.out.println("Fournisseur ajouté à la base de données : " + fournisseur);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ajout du fournisseur à la base de données.");
        }
    }



    public List<Fournisseur> getFournisseurs() {
        return fournisseurs;
    }

    public Fournisseur trouverFournisseurParId(int id) {
        for (Fournisseur fournisseur : fournisseurs) {
            if (fournisseur.getId() == id) {
                return fournisseur;
            }
        }
        return null;
    }

    public void supprimerFournisseur(int id) {
        String sql = "DELETE FROM fournisseur WHERE id = ?";
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