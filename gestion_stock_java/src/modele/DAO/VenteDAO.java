package modele.DAO;

import modele.Produit;
import modele.Vente;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VenteDAO {

    private Connection connection;
    public VenteDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
    public List<Vente> getVente() {
        String sql = "SELECT * FROM produit";
        List<Vente> ventes  = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                int quantiteVendue = resultSet.getInt("quantiteVendue");
                Date dateVente = resultSet.getDate("dateVente");
                ventes.add(new Vente(id, nom, quantiteVendue, dateVente));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement des produits.");
        }
        return ventes;
    }

    public void ajouterVente(Vente vente) {
        String sql = "INSERT INTO produit (nom, quantite_vendue, date_vente) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, vente.getNom());
            statement.setInt(2, vente.getQuantiteVendue());
            statement.setDate(3, new java.sql.Date(vente.getDateVente().getTime()));

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                vente.setId(generatedKeys.getInt(1));
            }
            System.out.println("Vente ajouté : " + vente);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ajout du produit.");
        }
    }
///*
public Vente trouverVenteParId(int id) {
    String sql = " SELECT v.id AS vente_id, v.quantite_vendue, v.date_vente, p.id AS produit_id, p.nom AS produit_nom, p.prix FROM vente v INNER JOIN produit p ON v.produit_id = p.id WHERE v.id = ? """;
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id); // Correction : assignation de l'ID avant exécution
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                String nom = resultSet.getString("nom");
                int quantiteVendue = resultSet.getInt("quantite_vendue");
                Date dateVente = resultSet.getDate("date_vente");
                int produitId = resultSet.getInt("produit_id");
                String produitNom = resultSet.getString("produit_nom");
                double produitPrix = resultSet.getDouble("prix");

                // Créer le produit
                Produit produit = new Produit(produitId, produitNom,);

                // Retourner l'objet Vente avec le produit associé
                return new Vente(nom, quantiteVendue, dateVente, produit);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Erreur lors de la recherche de la vente.");
    }
    return null;
}
    ///

    public void supprimerVente(int id) {
        String sql = "DELETE FROM vente WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Vente avec l'ID " + id + " supprimé.");
            } else {
                System.out.println("Aucun vente trouvé avec l'ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la suppression de la ventee.");
        }
    }




}
