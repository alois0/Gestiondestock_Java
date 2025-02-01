package vue;

import controleur.VenteController;
import modele.Produit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;

public class AjouterVenteView extends JFrame {
    private JTable tableProduits;
    private DefaultTableModel tableModel;
    private JTextField textFieldRecherche;
    private JTextField textFieldQuantiteVendue;
    private JButton btnAjouterVente;
    private JButton btnRetour;
    private VenteController venteController;

    public AjouterVenteView() {
        this.venteController = new VenteController();
        setTitle("Ajouter une Vente");
        setLayout(new BorderLayout());
        setSize(600, 500);

        // ✅ Barre de recherche
        JPanel panelRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRecherche.add(new JLabel("Rechercher:"));
        textFieldRecherche = new JTextField(20);
        panelRecherche.add(textFieldRecherche);
        textFieldRecherche.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                rechercherProduits(textFieldRecherche.getText().trim());
            }
        });

        // ✅ Table des produits
        String[] columnNames = {"ID", "Nom", "Prix", "Quantité"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableProduits = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableProduits);

        // ✅ Panel pour "Quantité Vendue"
        JPanel panelQuantite = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelQuantiteVendue = new JLabel("Quantité Vendue:");
        textFieldQuantiteVendue = new JTextField(10);
        panelQuantite.add(labelQuantiteVendue);
        panelQuantite.add(textFieldQuantiteVendue);

        // ✅ Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        btnAjouterVente = new JButton("Ajouter Vente");
        btnRetour = new JButton("Retour");
        panelBoutons.add(btnAjouterVente);
        panelBoutons.add(btnRetour);

        btnRetour.addActionListener(e -> dispose());
        btnAjouterVente.addActionListener(e -> ajouterVente());

        // ✅ Ajout des composants à la fenêtre
        JPanel panelBas = new JPanel(new GridLayout(2, 1));
        panelBas.add(panelQuantite); // ✅ Ajout du champ "Quantité Vendue"
        panelBas.add(panelBoutons);

        add(panelRecherche, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerProduits();
    }

    private void chargerProduits() {
        List<Produit> produits = venteController.getProduits();
        remplirTable(produits);
    }

    private void rechercherProduits(String recherche) {
        List<Produit> produits = venteController.rechercherProduits(recherche);
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

    private void ajouterVente() {
        int selectedRow = tableProduits.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit !");
            return;
        }

        int produitId = (int) tableModel.getValueAt(selectedRow, 0);
        String produitNom = (String) tableModel.getValueAt(selectedRow, 1);
        int quantiteVendue;

        try {
            quantiteVendue = Integer.parseInt(textFieldQuantiteVendue.getText().trim()); // ✅ Utilisation correcte
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer une quantité valide !");
            return;
        }

        if (quantiteVendue <= 0) {
            JOptionPane.showMessageDialog(null, "La quantité vendue doit être positive !");
            return;
        }

        venteController.enregistrerVente(produitNom, produitId, quantiteVendue, new Date());
        dispose();
    }
}
