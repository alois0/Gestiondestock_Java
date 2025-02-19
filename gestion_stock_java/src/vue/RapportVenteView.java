package vue;

import controleur.VenteController;
import modele.Vente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RapportVenteView extends JFrame {
    private JTable tableVentes;
    private DefaultTableModel tableModel;
    private JButton btnExporterCSV;
    private JButton btnRetour;
    private VenteController venteController;

    public RapportVenteView() {
        this.venteController = new VenteController();
        setTitle("Rapport des ventes");
        setLayout(new BorderLayout());
        setSize(700, 400);

        // Table pour afficher les ventes
        String[] columnNames = {"ID", "Produit", "Quantité Vendue", "Date", "Montant Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableVentes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableVentes);

        // ✅ Boutons stylisés bien alignés en bas
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

        chargerRapportVentes();
    }

    private void chargerRapportVentes() {
        List<Vente> ventes = venteController.getRapportVentes();
        System.out.println("Nombre de ventes récupérées : " + ventes.size());

        tableModel.setRowCount(0);
        for (Vente vente : ventes) {
            System.out.println("Vente ajoutée : " + vente.getNomProduit() + " - " + vente.getMontantTotal()); // Debug
            tableModel.addRow(new Object[]{
                    vente.getId(),
                    vente.getNomProduit(),
                    vente.getQuantiteVendue(),
                    vente.getDateVente(),
                    vente.getMontantTotal()
            });
        }
        tableModel.fireTableDataChanged();
    }




    private void exporterCSV() {
        String[] headers = {"ID", "Produit", "Quantité Vendue", "Date", "Montant Total"};
        venteController.exporterCSVVentes(headers);
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
