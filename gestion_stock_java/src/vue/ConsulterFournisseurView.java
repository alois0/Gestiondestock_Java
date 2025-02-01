package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.Produit;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsulterFournisseurView extends JFrame {
    private JTable tableFournisseurs;
    private JTable tableProduits;
    private DefaultTableModel fournisseurTableModel;
    private DefaultTableModel produitTableModel;
    private JTextField textFieldRecherche;
    private JButton btnRetour;
    private JButton btnDesassocier;
    private FournisseurController fournisseurController;

    public ConsulterFournisseurView() {
        this.fournisseurController = new FournisseurController();
        setTitle("Consulter les Fournisseurs");
        setLayout(new BorderLayout());
        setSize(800, 500);

        // Barre de recherche
        JPanel panelRecherche = new JPanel();
        panelRecherche.add(new JLabel("Rechercher:"));
        textFieldRecherche = new JTextField(20);
        panelRecherche.add(textFieldRecherche);

        // Table des fournisseurs
        String[] fournisseurColumns = {"ID", "Nom", "Contact"};
        fournisseurTableModel = new DefaultTableModel(fournisseurColumns, 0);
        tableFournisseurs = new JTable(fournisseurTableModel);
        JScrollPane scrollFournisseurs = new JScrollPane(tableFournisseurs);

        // Table des produits associés
        String[] produitColumns = {"ID", "Nom", "Prix", "Quantité"};
        produitTableModel = new DefaultTableModel(produitColumns, 0);
        tableProduits = new JTable(produitTableModel);
        JScrollPane scrollProduits = new JScrollPane(tableProduits);

        // Bouton désassocié et retour
        JPanel panelBoutons = new JPanel();
        btnDesassocier = new JButton("Désassocier Produit");
        btnRetour = new JButton("Retour");

        btnDesassocier.setEnabled(false); // Désactiver le bouton par défaut

        panelBoutons.add(btnDesassocier);
        panelBoutons.add(btnRetour);

        // Panel contenant les tables
        JPanel panelTables = new JPanel(new GridLayout(2, 1));
        panelTables.add(scrollFournisseurs);
        panelTables.add(scrollProduits);

        add(panelRecherche, BorderLayout.NORTH);
        add(panelTables, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> dispose());
        btnDesassocier.addActionListener(e -> desassocierProduit());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerFournisseurs();

        // Sélection d'un fournisseur pour afficher les produits associés
        tableFournisseurs.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && tableFournisseurs.getSelectedRow() != -1) {
                int fournisseurId = (int) fournisseurTableModel.getValueAt(tableFournisseurs.getSelectedRow(), 0);
                chargerProduitsAssocies(fournisseurId);
            }
        });

        // Activer/Désactiver le bouton en fonction de la sélection d'un produit
        tableProduits.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            btnDesassocier.setEnabled(tableProduits.getSelectedRow() != -1);
        });
    }

    private void chargerFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurController.getFournisseurs();
        fournisseurTableModel.setRowCount(0);
        for (Fournisseur fournisseur : fournisseurs) {
            fournisseurTableModel.addRow(new Object[]{
                    fournisseur.getId(),
                    fournisseur.getNom(),
                    fournisseur.getContact()
            });
        }
    }

    private void chargerProduitsAssocies(int fournisseurId) {
        List<Produit> produits = fournisseurController.getProduitsParFournisseur(fournisseurId);
        produitTableModel.setRowCount(0);
        for (Produit produit : produits) {
            produitTableModel.addRow(new Object[]{
                    produit.getId(),
                    produit.getNom(),
                    produit.getPrix(),
                    produit.getQuantite()
            });
        }
        btnDesassocier.setEnabled(false); // Désactiver le bouton après le changement de fournisseur
    }

    private void desassocierProduit() {
        int fournisseurRow = tableFournisseurs.getSelectedRow();
        if (fournisseurRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fournisseur !");
            return;
        }
        int fournisseurId = (int) fournisseurTableModel.getValueAt(fournisseurRow, 0);

        int produitRow = tableProduits.getSelectedRow();
        if (produitRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit à désassocier !");
            return;
        }
        int produitId = (int) produitTableModel.getValueAt(produitRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment désassocier ce produit du fournisseur ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            fournisseurController.desassocierProduitFournisseur(produitId);
            JOptionPane.showMessageDialog(null, "Produit désassocié avec succès !");
            chargerProduitsAssocies(fournisseurId);
        }
    }
}
