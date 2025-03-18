package vue;

import controleur.VenteController;
import modele.Produit;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;

public class AjouterVenteView extends JFrame {
    private JTable tableProduits;
    private DefaultTableModel tableModel;
    private JTextField textFieldRecherche;
    private JTextField textFieldQuantiteVendue;
    private JButton btnAjouterVente;
    private JButton btnRetour;
    private VenteController venteController;

    public AjouterVenteView() {
        this.venteController = new VenteController();
        setTitle("Ajouter une Vente");
        setLayout(new BorderLayout());
        setSize(600, 500);

        // ✅ Barre de recherche
        JPanel panelRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRecherche.add(new JLabel("🔎 Rechercher:"));
        textFieldRecherche = new JTextField(20);
        styliserChamp(textFieldRecherche);
        panelRecherche.add(textFieldRecherche);
        textFieldRecherche.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                rechercherProduits(textFieldRecherche.getText().trim());
            }
        });

        // Table des produits
        String[] columnNames = {"ID", "Nom", "Prix", "Quantité"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableProduits = new JTable(tableModel);
        styliserTable(tableProduits);
        JScrollPane scrollPane = new JScrollPane(tableProduits);

        // Panel pour "Quantité Vendue"
        JPanel panelQuantite = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelQuantiteVendue = new JLabel("Quantité Vendue:");
        textFieldQuantiteVendue = new JTextField(10);
        styliserChamp(textFieldQuantiteVendue);
        panelQuantite.add(labelQuantiteVendue);
        panelQuantite.add(textFieldQuantiteVendue);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        btnAjouterVente = createStyledButton("Ajouter Vente");
        btnRetour = createStyledButton("Retour");
        panelBoutons.add(btnAjouterVente);
        panelBoutons.add(btnRetour);

        btnRetour.addActionListener(e -> dispose());
        btnAjouterVente.addActionListener(e -> ajouterVente());

        // Ajout des composants à la fenêtre
        JPanel panelBas = new JPanel(new GridLayout(2, 1));
        panelBas.add(panelQuantite);
        panelBas.add(panelBoutons);

        add(panelRecherche, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerProduits();
    }

    private void chargerProduits() {
        List<Produit> produits = venteController.getProduits();
        remplirTable(produits);
    }

    private void rechercherProduits(String recherche) {
        List<Produit> produits = venteController.rechercherProduits(recherche);
        remplirTable(produits);
    }

    private void remplirTable(List<Produit> produits) {
        tableModel.setRowCount(0);
        for (Produit produit : produits) {
            tableModel.addRow(new Object[]{
                    produit.getId(),
                    produit.getNom(),
                    produit.getPrix(),
                    produit.getQuantite()
            });
        }
    }

    private void ajouterVente() {
        int selectedRow = tableProduits.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit !");
            return;
        }

        int produitId = (int) tableModel.getValueAt(selectedRow, 0);
        String produitNom = (String) tableModel.getValueAt(selectedRow, 1);
        int quantiteVendue;

        try {
            quantiteVendue = Integer.parseInt(textFieldQuantiteVendue.getText().trim()); // ✅ Utilisation correcte
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer une quantité valide !");
            return;
        }

        if (quantiteVendue <= 0) {
            JOptionPane.showMessageDialog(null, "La quantité vendue doit être positive !");
            return;
        }

        venteController.enregistrerVente(produitNom, produitId, quantiteVendue, new Date());
        dispose();
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
