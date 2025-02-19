package vue;

import controleur.ProduitController;
import modele.Produit;
import modele.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
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

        // Table pour afficher les produits
        String[] columnNames = {"ID", "Nom", "Prix", "Quantité"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableProduits = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableProduits);

        // Ajout du listener pour remplir les champs quand un produit est sélectionné
        tableProduits.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tableProduits.getSelectedRow() != -1) {
                    int selectedRow = tableProduits.getSelectedRow();
                    textFieldNom.setText((String) tableModel.getValueAt(selectedRow, 1));
                    textFieldPrix.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
                    textFieldQuantite.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
                }
            }
        });

        // Panel pour modifier un produit sélectionné
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

        JPanel panelBoutons = new JPanel();
        btnModifierProduit = createStyledButton("Modifier Produit");
        btnRetour = createStyledButton("Retour");
        panelBoutons.add(btnModifierProduit);
        panelBoutons.add(btnRetour);

        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.add(panelModification, BorderLayout.CENTER);
        panelBas.add(panelBoutons, BorderLayout.SOUTH);

// ✅ Ajouter les éléments correctement
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



}
