package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.Produit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AssocierProduitFournisseurView extends JFrame {
    private JTable tableProduits;
    private JTable tableFournisseurs;
    private DefaultTableModel produitTableModel;
    private DefaultTableModel fournisseurTableModel;
    private JButton btnAssocier;
    private JButton btnRetour;
    private FournisseurController fournisseurController;

    public AssocierProduitFournisseurView() {
        this.fournisseurController = new FournisseurController();
        setTitle("Associer Produits à un Fournisseur");
        setLayout(new BorderLayout());
        setSize(600, 600);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        // Panel Fournisseurs
        JPanel panelFournisseurs = new JPanel(new BorderLayout());
        String[] fournisseurColumns = {"ID", "Nom"};
        fournisseurTableModel = new DefaultTableModel(fournisseurColumns, 0);
        tableFournisseurs = new JTable(fournisseurTableModel);
        panelFournisseurs.add(new JLabel("Sélectionner un fournisseur:"), BorderLayout.NORTH);
        panelFournisseurs.add(new JScrollPane(tableFournisseurs), BorderLayout.CENTER);

        // Panel Produits
        JPanel panelProduits = new JPanel(new BorderLayout());
        String[] produitColumns = {"ID", "Nom"};
        produitTableModel = new DefaultTableModel(produitColumns, 0);
        tableProduits = new JTable(produitTableModel);
        tableProduits.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // ✅ Sélection multiple
        panelProduits.add(new JLabel("Sélectionner un ou plusieurs produits:"), BorderLayout.NORTH);
        panelProduits.add(new JScrollPane(tableProduits), BorderLayout.CENTER);

        // Boutons
        JPanel panelBoutons = new JPanel();
        btnAssocier = createStyledButton("Associer Produits");
        btnRetour = createStyledButton("Retour");
        panelBoutons.add(btnAssocier);
        panelBoutons.add(btnRetour);

        panelPrincipal.add(panelFournisseurs);
        panelPrincipal.add(panelProduits);
        panelPrincipal.add(panelBoutons);

        add(panelPrincipal, BorderLayout.CENTER);

        btnRetour.addActionListener(e -> dispose());

        // 🔥 Correction : S'assurer que l'événement n'est ajouté qu'une seule fois
        for (ActionListener al : btnAssocier.getActionListeners()) {
            btnAssocier.removeActionListener(al);
        }
        btnAssocier.addActionListener(this::associerProduits);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerFournisseurs();
        chargerProduits();
    }

    private void chargerFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurController.getFournisseurs();
        fournisseurTableModel.setRowCount(0);
        for (Fournisseur fournisseur : fournisseurs) {
            fournisseurTableModel.addRow(new Object[]{
                    fournisseur.getId(),
                    fournisseur.getNom()
            });
        }
    }

    private void chargerProduits() {
        List<Produit> produits = fournisseurController.getProduits();
        produitTableModel.setRowCount(0);
        for (Produit produit : produits) {
            produitTableModel.addRow(new Object[]{
                    produit.getId(),
                    produit.getNom()
            });
        }
    }

    private void associerProduits(ActionEvent e) {
        int fournisseurRow = tableFournisseurs.getSelectedRow();
        if (fournisseurRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fournisseur !");
            return;
        }
        int fournisseurId = (int) fournisseurTableModel.getValueAt(fournisseurRow, 0);

        int[] produitRows = tableProduits.getSelectedRows();
        if (produitRows.length == 0) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner au moins un produit !");
            return;
        }

        List<Integer> produitIds = new ArrayList<>();
        for (int row : produitRows) {
            produitIds.add((int) produitTableModel.getValueAt(row, 0));
        }

        // Associer uniquement si des produits existent
        if (!produitIds.isEmpty()) {
            fournisseurController.associerProduitsAFournisseur(fournisseurId, produitIds);


        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Texte plus grand
        button.setBackground(new Color(211, 211, 211)); // Gris clair (Light Gray)
        button.setForeground(Color.BLACK); // Texte en noir
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Bordure fine en gris
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding interne

        // Effet au survol (hover)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(169, 169, 169)); // Gris plus foncé (Dark Gray)
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(211, 211, 211)); // Retour à la couleur normale
            }
        });

        return button;
    }

}
