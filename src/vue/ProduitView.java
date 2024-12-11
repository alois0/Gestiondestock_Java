package vue;

import controleur.ProduitController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modele.Produit;

public class ProduitView extends JFrame {
    private ProduitController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProduitView(ProduitController controller) {
        this.controller = controller;

        setTitle("Gestion des Produits");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Formulaire d'ajout
        JPanel formPanel = new JPanel();
        JTextField nomField = new JTextField(20);
        JTextField prixField = new JTextField(20);
        JButton ajouterButton = new JButton("Ajouter");

        formPanel.add(new JLabel("Nom : "));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prix : "));
        formPanel.add(prixField);
        formPanel.add(ajouterButton);

        // Tableau pour afficher les produits
        String[] columnNames = {"ID", "Nom", "Prix", "Quantité"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Ajouter le tableau et le formulaire au panel principal
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Action sur le bouton "Ajouter"
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nom = nomField.getText();
                    double prix = Double.parseDouble(prixField.getText());
                    Produit produit = new Produit(0, nom, prix, 0); // Ajouter produit sans ID
                    controller.ajouterProduit(produit);

                    // Ajouter les données au tableau
                    tableModel.addRow(new Object[]{produit.getId(), produit.getNom(), produit.getPrix(), produit.getQuantite()});

                    // Réinitialiser les champs
                    nomField.setText("");
                    prixField.setText("");

                    JOptionPane.showMessageDialog(null, "Produit ajouté !");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un prix valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
    }
}