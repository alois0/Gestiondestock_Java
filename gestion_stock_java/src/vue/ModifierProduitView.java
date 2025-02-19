package vue;

import controleur.ProduitController;
import modele.Produit;
import modele.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ModifierProduitView extends JFrame {
    private JTable tableProduits;
    private DefaultTableModel tableModel;
    private JTextField textFieldNom;
    private JTextField textFieldPrix;
    private JTextField textFieldQuantite;
    private JButton btnModifierProduit;
    private JButton btnRetour;
    private ProduitController produitController;
    private User utilisateur;

    public ModifierProduitView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.produitController = new ProduitController();
        setTitle("Modifier un Produit");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(null);

        // ✅ Table pour afficher les produits avec style
        String[] columnNames = {"ID", "Nom", "Prix", "Quantité"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableProduits = new JTable(tableModel);
        styliserTable(tableProduits);
        JScrollPane scrollPane = new JScrollPane(tableProduits);

        // ✅ Listener pour remplir les champs automatiquement quand un produit est sélectionné
        tableProduits.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && tableProduits.getSelectedRow() != -1) {
                int selectedRow = tableProduits.getSelectedRow();
                textFieldNom.setText((String) tableModel.getValueAt(selectedRow, 1));
                textFieldPrix.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
                textFieldQuantite.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
            }
        });

        // ✅ Panel pour modifier un produit
        JPanel panelModification = new JPanel(new GridLayout(4, 2));
        panelModification.add(new JLabel("Nom du Produit:"));
        textFieldNom = new JTextField();
        panelModification.add(textFieldNom);
        panelModification.add(new JLabel("Nouveau Prix:"));
        textFieldPrix = new JTextField();
        panelModification.add(textFieldPrix);
        panelModification.add(new JLabel("Nouvelle Quantité:"));
        textFieldQuantite = new JTextField();
        panelModification.add(textFieldQuantite);

        // ✅ Boutons stylisés
        JPanel panelBoutons = new JPanel();
        btnModifierProduit = createStyledButton("Modifier Produit");
        btnRetour = createStyledButton("Retour");
        panelBoutons.add(btnModifierProduit);
        panelBoutons.add(btnRetour);

        // ✅ Conteneur en bas avec les champs et les boutons
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.add(panelModification, BorderLayout.CENTER);
        panelBas.add(panelBoutons, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> dispose());
        btnModifierProduit.addActionListener(e -> modifierProduit());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerProduits();
    }

    private void chargerProduits() {
        List<Produit> produits = produitController.getProduits();
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

    private void modifierProduit() {
        int selectedRow = tableProduits.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit à modifier !");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nom = textFieldNom.getText().trim();
        double prix;
        int quantite;

        try {
            prix = Double.parseDouble(textFieldPrix.getText().trim());
            quantite = Integer.parseInt(textFieldQuantite.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs valides pour le prix et la quantité !");
            return;
        }

        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le nom du produit ne peut pas être vide !");
            return;
        }

        Produit produit = new Produit(id, nom, prix, quantite);
        produitController.mettreAJourProduit(produit);
        JOptionPane.showMessageDialog(null, "Produit modifié avec succès !");
        chargerProduits();
    }

    // ✅ Méthode pour styliser les boutons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(211, 211, 211)); // Gris clair
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // ✅ Effet hover (sombre au survol)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(169, 169, 169)); // Gris foncé
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(211, 211, 211));
            }
        });

        return button;
    }

    // ✅ Méthode pour styliser la JTable
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
