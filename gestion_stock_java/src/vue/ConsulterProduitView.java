package vue;

import controleur.ProduitController;
import modele.Produit;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ConsulterProduitView extends JFrame {
    private JTable tableProduits;
    private DefaultTableModel tableModel;
    private JTextField textFieldRecherche;
    private JButton btnRetour;
    private ProduitController produitController;

    public ConsulterProduitView() {
        this.produitController = new ProduitController();
        setTitle("Consulter les Produits");
        setLayout(new BorderLayout());
        setSize(600, 400);

        // Barre de recherche
        JPanel panelRecherche = new JPanel();
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

        // Table pour afficher les produits
        String[] columnNames = {"ID", "Nom", "Prix", "Quantité","Fournisseur"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableProduits = new JTable(tableModel);
        styliserTable(tableProduits);
        JScrollPane scrollPane = new JScrollPane(tableProduits);

        // Bouton retour
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

        chargerProduits();
    }

    private void chargerProduits() {
        List<Produit> produits = produitController.getProduits();
        remplirTable(produits);
    }

    private void rechercherProduits(String recherche) {
        List<Produit> produits = produitController.rechercherProduits(recherche);
        remplirTable(produits);
    }

    private void remplirTable(List<Produit> produits) {
        tableModel.setRowCount(0);
        for (Produit produit : produits) {
            tableModel.addRow(new Object[]{
                    produit.getId(),
                    produit.getNom(),
                    produit.getPrix(),
                    produit.getQuantite(),
                    produit.getFournisseur(),
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

        // Couleur alternée des lignes
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

    private void styliserChamp(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14)); // Police et taille du texte
        champ.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Bordure grise
        champ.setBackground(new Color(240, 240, 240)); // Fond gris clair
        champ.setForeground(Color.BLACK); // Texte noir
        champ.setOpaque(true);
        champ.setPreferredSize(new Dimension(200, 30)); // Taille du champ
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding interne
        ));

        // Effet au survol (hover)
        champ.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(220, 220, 220)); // Gris plus foncé
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(240, 240, 240)); // Retour à la couleur normale
            }
        });
    }
}
