package controleur;

import modele.Vente;
import modele.Produit;
import modele.Connexion;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VenteController {
    private Connection connection;

    public VenteController() {
        this.connection = Connexion.getConnection();
    }

    // ✅ Récupérer la liste des produits disponibles
    public List<Produit> getProduits() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit";

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
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de la récupération des produits !");
        }

        return produits;
    }

    // ✅ Récupérer la liste des ventes enregistrées
    public List<Vente> getVentes() {
        List<Vente> ventes = new ArrayList<>();
        String sql = "SELECT v.id, v.nom, v.quantite_vendue, v.date_vente, " +
                "p.id AS produit_id, p.nom AS produit_nom, p.prix, p.quantite " +
                "FROM vente v " +
                "JOIN produit p ON v.produit_id = p.id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                int quantiteVendue = resultSet.getInt("quantite_vendue");
                Date dateVente = resultSet.getTimestamp("date_vente");

                int produitId = resultSet.getInt("produit_id");
                String produitNom = resultSet.getString("produit_nom");
                double produitPrix = resultSet.getDouble("prix");
                int produitQuantite = resultSet.getInt("quantite");

                Produit produit = new Produit(produitId, produitNom, produitPrix, produitQuantite);
                ventes.add(new Vente(id, nom, produitId, quantiteVendue, dateVente, produit));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de la récupération des ventes !");
        }

        return ventes;
    }

    // ✅ Enregistrer une vente avec mise à jour du stock
    public void enregistrerVente(String nomProduit, int produitId, int quantiteVendue, Date dateVente) {
        // Vérifier la disponibilité du stock
        String checkStockSql = "SELECT quantite FROM produit WHERE id = ?";
        String updateStockSql = "UPDATE produit SET quantite = quantite - ? WHERE id = ?";
        String insertVenteSql = "INSERT INTO vente (produit_id, nom, quantite_vendue, date_vente) VALUES (?, ?, ?, ?)";

        try (PreparedStatement checkStatement = connection.prepareStatement(checkStockSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateStockSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertVenteSql)) {

            // Vérification du stock
            checkStatement.setInt(1, produitId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                int stockDisponible = resultSet.getInt("quantite");

                if (quantiteVendue > stockDisponible) {
                    JOptionPane.showMessageDialog(null, "⛔ Stock insuffisant ! Disponible : " + stockDisponible);
                    return;
                }

                // Insérer la vente
                insertStatement.setInt(1, produitId);
                insertStatement.setString(2, nomProduit);
                insertStatement.setInt(3, quantiteVendue);
                insertStatement.setTimestamp(4, new java.sql.Timestamp(dateVente.getTime()));

                insertStatement.executeUpdate();

                // Mettre à jour le stock
                updateStatement.setInt(1, quantiteVendue);
                updateStatement.setInt(2, produitId);
                updateStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "✅ Vente enregistrée avec succès !");
            } else {
                JOptionPane.showMessageDialog(null, "❌ Produit non trouvé !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'enregistrement de la vente !");
        }
    }

    // ✅ Récupérer l'historique des ventes


    // ✅ Supprimer une vente
    public void supprimerVente(int venteId) {
        String sql = "DELETE FROM vente WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, venteId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Vente supprimée avec succès !");
            } else {
                JOptionPane.showMessageDialog(null, "Aucune vente trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la vente.");
        }
    }

    // ✅ Rechercher des produits
    public List<Produit> rechercherProduits(String recherche) {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE nom LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + recherche + "%");
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
            System.err.println("❌ Erreur lors de la recherche des produits !");
        }

        return produits;
    }

    public List<Vente> getHistoriqueVentes() {
        List<Vente> ventes = new ArrayList<>();
        String sql = "SELECT v.id, v.nom, v.quantite_vendue, v.date_vente, " +
                "p.id AS produit_id, p.nom AS produit_nom, p.prix " +
                "FROM vente v JOIN produit p ON v.produit_id = p.id ORDER BY v.date_vente DESC";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nomProduit = resultSet.getString("produit_nom");
                int quantiteVendue = resultSet.getInt("quantite_vendue");

                // 🔹 Récupération correcte de la date et de l'heure
                Timestamp timestamp = resultSet.getTimestamp("date_vente");
                Date dateVente = new Date(timestamp.getTime()); // Utilisation de Timestamp

                int produitId = resultSet.getInt("produit_id");
                double prixUnitaire = resultSet.getDouble("prix");

                Produit produit = new Produit(produitId, nomProduit, prixUnitaire, 0);
                Vente vente = new Vente(id, nomProduit, produitId, quantiteVendue, dateVente, produit);

                ventes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de l'historique des ventes.");
        }
        return ventes;
    }



}
