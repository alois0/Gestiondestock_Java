package vue;

import controleur.VenteController;
import modele.Vente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SupprimerVenteView extends JFrame {
    private JTable tableVentes;
    private DefaultTableModel tableModel;
    private JButton btnSupprimer;
    private JButton btnRetour;
    private VenteController venteController;

    public SupprimerVenteView() {
        this.venteController = new VenteController();
        setTitle("Supprimer une Vente");
        setLayout(new BorderLayout());
        setSize(600, 400);

        // Table pour afficher les ventes
        String[] columnNames = {"ID", "Produit", "Quantité", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableVentes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableVentes);

        // Boutons
        JPanel panelBoutons = new JPanel();
        btnSupprimer = new JButton("Supprimer Vente");
        btnRetour = new JButton("Retour");

        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        // Actions des boutons
        btnRetour.addActionListener(e -> dispose());
        btnSupprimer.addActionListener(e -> supprimerVente());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerVentes();
    }

    private void chargerVentes() {
        List<Vente> ventes = venteController.getVentes();
        tableModel.setRowCount(0);

        for (Vente vente : ventes) {
            tableModel.addRow(new Object[]{
                    vente.getId(),
                    (vente.getProduit() != null ? vente.getProduit().getNom() : "Aucun produit"),
                    vente.getQuantiteVendue(),
                    vente.getDateVente()
            });
        }
    }


    private void supprimerVente() {
        int selectedRow = tableVentes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une vente à supprimer !");
            return;
        }

        int idVente = (int) tableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer cette vente ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            venteController.supprimerVente(idVente);

            chargerVentes();
        }
    }
}
