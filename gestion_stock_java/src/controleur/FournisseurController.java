package controleur;

import modele.Fournisseur;
import modele.Connexion;
import modele.Produit;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FournisseurController {
    private Connection connection;

    public FournisseurController() {
        this.connection = Connexion.getConnection();
    }

    public List<Fournisseur> getFournisseurs() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String sql = "SELECT * FROM fournisseur";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String contact = resultSet.getString("contact");
                fournisseurs.add(new Fournisseur(id, nom, contact));
            }
            System.out.println("Fournisseurs récupérés : " + fournisseurs);
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
            System.out.println("Fournisseur ajouté : " + fournisseur);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ajout du fournisseur.");
        }
    }

    public void modifierFournisseur(Fournisseur fournisseur) {
        String sql = "UPDATE fournisseur SET nom = ?, contact = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fournisseur.getNom());
            statement.setString(2, fournisseur.getContact());
            statement.setInt(3, fournisseur.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Fournisseur mis à jour : " + fournisseur);
            } else {
                System.out.println("Aucun fournisseur trouvé avec l'ID " + fournisseur.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la mise à jour du fournisseur.");
        }
    }

    public void supprimerFournisseur(int id) {
        // Vérifier si le fournisseur est associé à des produits
        if (estFournisseurAssocieAProduits(id)) {
            JOptionPane.showMessageDialog(null, "⛔ Impossible de supprimer ce fournisseur !\nIl est encore associé à des produits.\nVeuillez réattribuer ces produits à un autre fournisseur avant suppression.", "Alerte", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "DELETE FROM fournisseur WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Fournisseur supprimé avec succès.");
            } else {
                System.out.println("❌ Aucun fournisseur trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la suppression du fournisseur.");
        }
    }


    public boolean estFournisseurAssocieAProduits(int fournisseurId) {
        String sql = "SELECT COUNT(*) FROM produit WHERE fournisseur_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, fournisseurId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Si le nombre de produits > 0, alors il est encore associé
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public List<Fournisseur> rechercherFournisseurs(String recherche) {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String sql = "SELECT * FROM fournisseur WHERE nom LIKE ? OR contact LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + recherche + "%");
            statement.setString(2, "%" + recherche + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String contact = resultSet.getString("contact");
                fournisseurs.add(new Fournisseur(id, nom, contact));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la recherche des fournisseurs.");
        }
        return fournisseurs;
    }



    public List<Produit> getProduits() {
        String sql = "SELECT * FROM produit";
        List<Produit> produits = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                double prix = resultSet.getDouble("prix");
                int quantite = resultSet.getInt("quantite");
                produits.add(new Produit(id, nom, prix, quantite));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement des produits.");
        }
        return produits;
    }


}
