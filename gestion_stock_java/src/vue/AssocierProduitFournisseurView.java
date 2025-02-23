package vue;

import controleur.FournisseurController;
import controleur.ProduitController;
import modele.Fournisseur;
import modele.Produit;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class AssocierProduitFournisseurView extends JFrame {
    private JTable tableProduits;
    private JTable tableFournisseurs;
    private DefaultTableModel produitTableModel;
    private DefaultTableModel fournisseurTableModel;
    private JButton btnAssocier;

    private JTextField textFieldRechercheProduit;

    private JTextField textFieldRechercheFournisseur;
    private JButton btnRetour;
    private FournisseurController fournisseurController;

    private ProduitController produitController;

    public AssocierProduitFournisseurView() {
        this.fournisseurController = new FournisseurController();
        this.produitController = new ProduitController();
        setTitle("Associer Produits à un Fournisseur");
        setLayout(new BorderLayout());
        setSize(600, 600);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        // 🔍 Barre de recherche pour les fournisseurs
        JPanel panelRechercheFournisseur = new JPanel();
        textFieldRechercheFournisseur = new JTextField(20);
        textFieldRechercheFournisseur.addActionListener(e -> rechercherFournisseurs(textFieldRechercheFournisseur.getText().trim()));
        panelRechercheFournisseur.add(new JLabel("🔎 Rechercher Fournisseur :"));
        panelRechercheFournisseur.add(textFieldRechercheFournisseur);
        styliserChamp(textFieldRechercheFournisseur);

        textFieldRechercheFournisseur.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                rechercherFournisseurs(textFieldRechercheFournisseur.getText().trim());
            }
        });

        // Panel Fournisseurs
        JPanel panelFournisseurs = new JPanel(new BorderLayout());
        String[] fournisseurColumns = {"ID", "Nom"};
        fournisseurTableModel = new DefaultTableModel(fournisseurColumns, 0);
        tableFournisseurs = new JTable(fournisseurTableModel);
        styliserTable(tableFournisseurs);
        panelFournisseurs.add(new JLabel("Sélectionner un fournisseur:"), BorderLayout.NORTH);
        panelFournisseurs.add(new JScrollPane(tableFournisseurs), BorderLayout.CENTER);

        // 🔍 Barre de recherche pour les produits
        JPanel panelRechercheProduit = new JPanel();
        textFieldRechercheProduit = new JTextField(20);
        textFieldRechercheProduit.addActionListener(e -> rechercherProduits(textFieldRechercheProduit.getText().trim()));
        panelRechercheProduit.add(new JLabel("🔎 Rechercher Produit :"));
        panelRechercheProduit.add(textFieldRechercheProduit);
        styliserChamp(textFieldRechercheProduit);

        textFieldRechercheProduit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                rechercherProduits(textFieldRechercheProduit.getText().trim());
            }
        });

        // Panel Produits
        JPanel panelProduits = new JPanel(new BorderLayout());
        String[] produitColumns = {"ID", "Nom"};
        produitTableModel = new DefaultTableModel(produitColumns, 0);
        tableProduits = new JTable(produitTableModel);
        styliserTable(tableProduits);
        panelProduits.add(new JLabel("Sélectionner un ou plusieurs produits:"), BorderLayout.NORTH);
        panelProduits.add(new JScrollPane(tableProduits), BorderLayout.CENTER);

        // Boutons
        JPanel panelBoutons = new JPanel();
        btnAssocier = createStyledButton("Associer Produits");
        btnRetour = createStyledButton("Retour");
        panelBoutons.add(btnAssocier);
        panelBoutons.add(btnRetour);

        panelPrincipal.add(panelRechercheFournisseur);
        panelPrincipal.add(panelFournisseurs);
        panelPrincipal.add(panelRechercheProduit);
        panelPrincipal.add(panelProduits);
        panelPrincipal.add(panelBoutons);

        add(panelPrincipal, BorderLayout.CENTER);

        btnRetour.addActionListener(e -> dispose());

        // 🔥 Correction : S'assurer que l'événement n'est ajouté qu'une seule fois
        for (ActionListener al : btnAssocier.getActionListeners()) {
            btnAssocier.removeActionListener(al);
        }
        btnAssocier.addActionListener(this::associerProduits);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerFournisseurs();
        chargerProduits();
    }

    private void chargerFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurController.getFournisseurs();
        fournisseurTableModel.setRowCount(0);
        for (Fournisseur fournisseur : fournisseurs) {
            fournisseurTableModel.addRow(new Object[]{
                    fournisseur.getId(),
                    fournisseur.getNom()
            });
        }
    }

    private void chargerProduits() {
        List<Produit> produits = fournisseurController.getProduits();
        produitTableModel.setRowCount(0);
        for (Produit produit : produits) {
            produitTableModel.addRow(new Object[]{
                    produit.getId(),
                    produit.getNom()
            });
        }
    }

    private void associerProduits(ActionEvent e) {
        int fournisseurRow = tableFournisseurs.getSelectedRow();
        if (fournisseurRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fournisseur !");
            return;
        }
        int fournisseurId = (int) fournisseurTableModel.getValueAt(fournisseurRow, 0);

        int[] produitRows = tableProduits.getSelectedRows();
        if (produitRows.length == 0) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner au moins un produit !");
            return;
        }

        List<Integer> produitIds = new ArrayList<>();
        for (int row : produitRows) {
            produitIds.add((int) produitTableModel.getValueAt(row, 0));
        }

        // Associer uniquement si des produits existent
        if (!produitIds.isEmpty()) {
            fournisseurController.associerProduitsAFournisseur(fournisseurId, produitIds);


        }
    }

    private void rechercherProduits(String recherche) {
        List<Produit> produits = produitController.rechercherProduits(recherche);
        remplirTableProduits(produits);
    }


    private void remplirTableProduits(List<Produit> produits) {
        produitTableModel.setRowCount(0);
        for (Produit produit : produits) {
            produitTableModel.addRow(new Object[]{
                    produit.getId(),
                    produit.getNom(),
                    produit.getPrix(),
                    produit.getQuantite()
            });
        }
    }

    private void rechercherFournisseurs(String recherche) {
        List<Fournisseur> fournisseurs = fournisseurController.rechercherFournisseurs(recherche);
        remplirTableFournisseurs(fournisseurs);
    }

    private void remplirTableFournisseurs(List<Fournisseur> fournisseurs) {
        fournisseurTableModel.setRowCount(0);
        for (Fournisseur fournisseur : fournisseurs) {
            fournisseurTableModel.addRow(new Object[]{
                    fournisseur.getId(),
                    fournisseur.getNom(),
                    fournisseur.getContact()
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
