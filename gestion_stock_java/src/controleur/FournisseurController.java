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
        String sql = "DELETE FROM fournisseur WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Fournisseur avec l'ID " + id + " supprimé.");
            } else {
                System.out.println("Aucun fournisseur trouvé avec l'ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la suppression du fournisseur.");
        }
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

    public void associerProduitsAFournisseur(int fournisseurId, List<Integer> produitIds) {
        String sql = "UPDATE produit SET fournisseur_id = ? WHERE id = ?";
        boolean produitAjoute = false; // Vérifier si au moins un produit a été associé

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int produitId : produitIds) {
                if (estProduitDejaAssocie(fournisseurId, produitId)) {
                    System.out.println("⛔ Le produit ID " + produitId + " est déjà associé à ce fournisseur !");
                    JOptionPane.showMessageDialog(null, "Le produit ID " + produitId + " est déjà associé à ce fournisseur !");
                    continue; // On passe au produit suivant sans l'ajouter à nouveau
                }

                statement.setInt(1, fournisseurId);
                statement.setInt(2, produitId);
                statement.executeUpdate();
                produitAjoute = true; // Un produit a bien été ajouté
            }

            // ✅ On affiche un message UNIQUEMENT si au moins un produit a été ajouté
            if (produitAjoute) {
                JOptionPane.showMessageDialog(null, "✅ Produits associés avec succès !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'association des produits !");
        }
    }



    public void desassocierProduitFournisseur(int produitId) {
        String sql = "UPDATE produit SET fournisseur_id = NULL WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, produitId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Produit ID " + produitId + " désassocié de son fournisseur.");
            } else {
                System.out.println("Aucune modification effectuée, vérifiez l'ID du produit.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la désassociation du produit.");
        }
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

    public List<Produit> getProduitsParFournisseur(int fournisseurId) {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT id, nom, prix, quantite FROM produit WHERE fournisseur_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, fournisseurId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                double prix = resultSet.getDouble("prix");
                int quantite = resultSet.getInt("quantite");
                produits.add(new Produit(id, nom, prix, quantite));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la récupération des produits du fournisseur.");
        }
        return produits;
    }

    public boolean estProduitDejaAssocie(int fournisseurId, int produitId) {
        String sql = "SELECT COUNT(*) FROM produit WHERE id = ? AND fournisseur_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, produitId);
            statement.setInt(2, fournisseurId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Si le produit est déjà associé, on renvoie true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
