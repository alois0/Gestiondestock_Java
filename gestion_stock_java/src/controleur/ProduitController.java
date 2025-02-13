package controleur;

import modele.Connexion;
import modele.Produit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ProduitController {
    private Connection connection;

    public ProduitController() {
        this.connection = Connexion.getConnection();
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

    public void ajouterProduit(Produit produit) {
        String sql = "INSERT INTO produit (nom, prix, quantite) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, produit.getNom());
            statement.setDouble(2, produit.getPrix());
            statement.setInt(3, produit.getQuantite());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    produit.setId(generatedKeys.getInt(1));
                }
                System.out.println("✅ Produit ajouté avec succès : " + produit);
            } else {
                System.out.println("❌ Aucun produit ajouté !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de l'ajout du produit.");
        }
    }

    public Produit trouverProduitParId(int id) {
        String sql = "SELECT * FROM produit WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    double prix = resultSet.getDouble("prix");
                    int quantite = resultSet.getInt("quantite");
                    return new Produit(id, nom, prix, quantite);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la recherche du produit.");
        }
        return null;
    }

    public void supprimerProduit(int id) {
        String sql = "DELETE FROM produit WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Produit avec l'ID " + id + " supprimé.");
            } else {
                System.out.println("Aucun produit trouvé avec l'ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la suppression du produit.");
        }
    }

    public void mettreAJourProduit(Produit produit) {
        String sql = "UPDATE produit SET nom = ?, prix = ?, quantite = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, produit.getNom());
            statement.setDouble(2, produit.getPrix());
            statement.setInt(3, produit.getQuantite());
            statement.setInt(4, produit.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Produit mis à jour : " + produit);
            } else {
                System.out.println("Aucun produit trouvé avec l'ID " + produit.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la mise à jour du produit.");
        }
    }

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
            System.err.println("Erreur lors de la recherche des produits.");
        }
        return produits;
    }

    public void verifierStockMinimum(int seuil) {
        String sql = "SELECT * FROM produit WHERE quantite <= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, seuil);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("⚠️ Attention ! Le produit " + resultSet.getString("nom") + " a un stock faible (" + resultSet.getInt("quantite") + ").");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la vérification du stock minimum.");
        }
    }

    public List<Produit> getEtatStock() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT p.id, p.nom, p.quantite, f.nom AS fournisseur " +
                "FROM produit p LEFT JOIN fournisseur f ON p.fournisseur_id = f.id"; // ✅ Correction ici

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                int quantite = resultSet.getInt("quantite");
                String fournisseur = resultSet.getString("fournisseur"); // ✅ Récupération correcte du fournisseur

                produits.add(new Produit(id, nom, quantite, fournisseur)); // ✅ Utilisation correcte du constructeur
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de la récupération de l'état du stock !");
        }

        return produits;
    }

    public void exporterCSVStock(String[] headers) {
        List<Produit> produits = getEtatStock(); // 📌 Récupère les produits du stock
        List<String[]> data = new ArrayList<>();

        // 🔹 Transformation des objets Produit en lignes de texte
        for (Produit produit : produits) {
            data.add(new String[]{
                    String.valueOf(produit.getId()),
                    produit.getNom(),
                    String.valueOf(produit.getQuantite()),
                    produit.getFournisseur() != null ? produit.getFournisseur() : "Aucun"
            });
        }

        // 🔹 Appelle la fonction pour exporter en CSV
        exporterCSV(data, headers, "rapport_stock.csv");
    }

    public void exporterCSV(List<String[]> data, String[] headers, String fileName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le fichier CSV");

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.append(String.join(",", headers)).append("\n"); // 📌 Ajoute l'en-tête CSV

                for (String[] row : data) {
                    writer.append(String.join(",", row)).append("\n");
                }

                JOptionPane.showMessageDialog(null, "✅ Export en CSV réussi : " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'export CSV !");
            }
        }
    }





}
