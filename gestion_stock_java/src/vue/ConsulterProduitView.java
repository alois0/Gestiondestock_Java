package vue;

import controleur.ProduitController;
import modele.Produit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ConsulterProduitView extends JFrame {
    private JTable tableProduits;
    private DefaultTableModel tableModel;
    private JTextField textFieldRecherche;
    private JButton btnRetour;
    private ProduitController produitController;

    public ConsulterProduitView() {
        this.produitController = new ProduitController();
        setTitle("Consulter les Produits");
        setLayout(new BorderLayout());
        setSize(600, 400);

        // Barre de recherche
        JPanel panelRecherche = new JPanel();
        panelRecherche.add(new JLabel("Rechercher:"));
        textFieldRecherche = new JTextField(20);
        panelRecherche.add(textFieldRecherche);

        textFieldRecherche.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                rechercherProduits(textFieldRecherche.getText().trim());
            }
        });

        // Table pour afficher les produits
        String[] columnNames = {"ID", "Nom", "Prix", "Quantité"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableProduits = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableProduits);

        // Bouton retour
        btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> dispose());

        add(panelRecherche, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnRetour, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerProduits();
    }

    private void chargerProduits() {
        List<Produit> produits = produitController.getProduits();
        remplirTable(produits);
    }

    private void rechercherProduits(String recherche) {
        List<Produit> produits = produitController.rechercherProduits(recherche);
        remplirTable(produits);
    }

    private void remplirTable(List<Produit> produits) {
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
}
