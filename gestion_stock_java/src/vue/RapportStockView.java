package vue;

import controleur.ProduitController;
import modele.Produit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RapportStockView extends JFrame {
    private JTable tableStock;
    private DefaultTableModel tableModel;
    private JButton btnExporterCSV;
    private JButton btnRetour;
    private ProduitController produitController;

    public RapportStockView() {
        this.produitController = new ProduitController();
        setTitle("Rapport sur l'état du stock");
        setLayout(new BorderLayout());
        setSize(600, 400);

        // Table pour afficher le stock
        String[] columnNames = {"ID", "Produit", "Quantité", "Fournisseur"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableStock = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableStock);

        // Boutons
        JPanel panelButtons = new JPanel();
        btnExporterCSV = createStyledButton("Exporter CSV");
        btnRetour = createStyledButton("Retour");

        btnExporterCSV.addActionListener(e -> exporterCSV());
        btnRetour.addActionListener(e -> dispose());

        panelButtons.add(btnExporterCSV);
        panelButtons.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerEtatStock();
    }

    private void chargerEtatStock() {
        List<Produit> produits = produitController.getEtatStock();
        tableModel.setRowCount(0);
        for (Produit produit : produits) {
            tableModel.addRow(new Object[]{
                    produit.getId(),
                    produit.getNom(),
                    produit.getQuantite(),
                    produit.getFournisseur()
            });
        }
    }

    private void exporterCSV() {
        String[] headers = {"ID", "Produit", "Quantité", "Fournisseur"};
        produitController.exporterCSVStock(headers);
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
