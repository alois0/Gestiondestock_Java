package vue;

import controleur.VenteController;
import modele.Vente;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class SupprimerVenteView extends JFrame {
    private JTable tableVentes;
    private DefaultTableModel tableModel;
    private JButton btnSupprimer;

    private JTextField textFieldRecherche;
    private JButton btnRetour;
    private VenteController venteController;

    public SupprimerVenteView() {
        this.venteController = new VenteController();
        setTitle("Supprimer une Vente");
        setLayout(new BorderLayout());
        setSize(600, 400);

        JPanel panelRecherche = new JPanel();
        textFieldRecherche = new JTextField(20);
        styliserChamp(textFieldRecherche);
        panelRecherche.add(new JLabel("🔎 Rechercher : "));
        panelRecherche.add(textFieldRecherche);

        textFieldRecherche.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                rechercherVentes(textFieldRecherche.getText().trim());
            }
        });

        String[] columnNames = {"ID", "Produit", "Quantité", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableVentes = new JTable(tableModel);
        styliserTable(tableVentes);
        JScrollPane scrollPane = new JScrollPane(tableVentes);

        JPanel panelBoutons = new JPanel();
        btnSupprimer = createStyledButton("Supprimer Vente");
        btnRetour = createStyledButton("Retour");

        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnRetour);

        add(panelRecherche, BorderLayout.NORTH);
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

    private void rechercherVentes(String recherche) {
        List<Vente> ventes = venteController.rechercherVentes(recherche);
        remplirTable(ventes);
    }

    private void remplirTable(List<Vente> ventes) {
        tableModel.setRowCount(0);
        for (Vente vente : ventes) {
            tableModel.addRow(new Object[]{
                    vente.getId(),
                    vente.getProduit().getNom(),
                    vente.getQuantiteVendue(),
                    vente.getDateVente(),
                    vente.getMontantTotal()
            });
        }
    }


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(211, 211, 211));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(169, 169, 169));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(211, 211, 211));
            }
        });

        return button;
    }

    private void styliserTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(180, 180, 180));
        header.setForeground(Color.BLACK);

        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(25);

        // Alignement centré des cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Couleur alternée des lignes
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(230, 230, 230));
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });
    }

    private void styliserChamp(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        champ.setBackground(new Color(240, 240, 240));
        champ.setForeground(Color.BLACK);
        champ.setOpaque(true);
        champ.setPreferredSize(new Dimension(200, 30));
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        champ.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(240, 240, 240));
            }
        });
    }
}
