package vue;

import controleur.ProduitController;
import modele.Produit;
import modele.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SupprimerProduitView extends JFrame {
    private JTable tableProduits;
    private DefaultTableModel tableModel;
    private JButton btnSupprimerProduit;
    private JButton btnRetour;
    private ProduitController produitController;
    private User utilisateur;

    public SupprimerProduitView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.produitController = new ProduitController();
        setTitle("Supprimer un Produit");
        setLayout(new BorderLayout());
        setSize(600, 400);

        // Table pour afficher les produits
        String[] columnNames = {"ID", "Nom", "Prix", "Quantité"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableProduits = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableProduits);

        // Boutons d'action
        JPanel panelBoutons = new JPanel();
        btnSupprimerProduit = new JButton("Supprimer Produit");
        btnRetour = new JButton("Retour");
        panelBoutons.add(btnSupprimerProduit);
        panelBoutons.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> dispose());
        btnSupprimerProduit.addActionListener(e -> supprimerProduit());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerProduits();
    }

    private void chargerProduits() {
        List<Produit> produits = produitController.getProduits();
        tableModel.setRowCount(0);
        for (Produit produit : produits) {
            tableModel.addRow(new Object[]{
                    produit.getId(),
                    produit.getNom(),
                    produit.getPrix(),
                    produit.getQuantite()
            });
        }
    }

    private void supprimerProduit() {
        int selectedRow = tableProduits.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit à supprimer !");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce produit ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            produitController.supprimerProduit(id);
            JOptionPane.showMessageDialog(null, "Produit supprimé avec succès !");
            chargerProduits();
        }
    }
}
