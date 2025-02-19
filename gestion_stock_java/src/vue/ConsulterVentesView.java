package vue;

import controleur.VenteController;
import modele.Vente;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        styliserTable(tableVentes);
        JScrollPane scrollPane = new JScrollPane(tableVentes);

        JPanel panelBouton = new JPanel();
        btnRetour = createStyledButton("Retour");
        btnRetour.addActionListener(e -> dispose());
        panelBouton.add(btnRetour);

        // ✅ Ajout des éléments
        add(scrollPane, BorderLayout.CENTER);
        add(panelBouton, BorderLayout.SOUTH);

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

    private void styliserTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(180, 180, 180)); // Gris foncé pour l'en-tête
        header.setForeground(Color.BLACK);

        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(25);

        // ✅ Alignement centré des cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // ✅ Couleur alternée des lignes
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(230, 230, 230)); // Gris clair pour les lignes paires
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });
    }

}
