package vue;

import controleur.VenteController;
import modele.Vente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.text.SimpleDateFormat;

public class ConsulterVentesView extends JFrame {
    private JTable tableVentes;
    private DefaultTableModel tableModel;
    private JButton btnRetour;
    private VenteController venteController;

    public ConsulterVentesView() {
        this.venteController = new VenteController();
        setTitle("Historique des Ventes");
        setLayout(new BorderLayout());
        setSize(700, 400); // Augmenter légèrement la largeur

        // Table pour afficher les ventes
        String[] columnNames = {"ID", "Produit", "Quantité", "Date", "Montant Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableVentes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableVentes);

        btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> dispose());

        add(scrollPane, BorderLayout.CENTER);
        add(btnRetour, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerHistoriqueVentes();
    }

    private void chargerHistoriqueVentes() {
        List<Vente> ventes = venteController.getHistoriqueVentes();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // 🔹 Format avec l'heure
        tableModel.setRowCount(0);
        for (Vente vente : ventes) {
            tableModel.addRow(new Object[]{
                    vente.getId(),
                    vente.getProduit().getNom(),
                    vente.getQuantiteVendue(),
                    dateFormat.format(vente.getDateVente()), // ✅ Affichage correct avec l'heure
                    vente.getMontantTotal()
            });
        }
    }

}
