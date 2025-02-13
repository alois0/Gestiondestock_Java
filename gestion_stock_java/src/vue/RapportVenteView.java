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

        // Boutons
        JPanel panelButtons = new JPanel();
        btnExporterCSV = new JButton("Exporter CSV");
        btnRetour = new JButton("Retour");

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
}
