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
import java.text.SimpleDateFormat;
import java.util.List;
import java.text.SimpleDateFormat;

public class ConsulterVentesView extends JFrame {
    private JTable tableVentes;
    private DefaultTableModel tableModel;
    private JButton btnRetour;
    private JTextField textFieldRecherche;
    private VenteController venteController;

    public ConsulterVentesView() {
        this.venteController = new VenteController();
        setTitle("Historique des Ventes");
        setLayout(new BorderLayout());
        setSize(700, 400);

        // Barre de recherche
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

        // Ajout des éléments
        add(panelRecherche, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBouton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerHistoriqueVentes();
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

    private void chargerHistoriqueVentes() {
        List<Vente> ventes = venteController.getHistoriqueVentes();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        tableModel.setRowCount(0);
        for (Vente vente : ventes) {
            tableModel.addRow(new Object[]{
                    vente.getId(),
                    vente.getProduit().getNom(),
                    vente.getQuantiteVendue(),
                    dateFormat.format(vente.getDateVente()),
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
