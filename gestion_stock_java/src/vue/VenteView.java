package vue;

import controleur.VenteController;
import modele.Vente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class VenteView extends JFrame {
    private VenteController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public VenteView(VenteController controller) {
        this.controller = controller;

        setTitle("Gestion des Ventes");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel();
        JTextField nomField = new JTextField(15);
        JTextField quantiteField = new JTextField(10);
        JButton ajouterButton = new JButton("Ajouter");

        formPanel.add(new JLabel("Nom : "));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Quantité Vendue : "));
        formPanel.add(quantiteField);
        formPanel.add(ajouterButton);

        String[] columnNames = {"ID", "Nom", "Quantité Vendue", "Date de Vente"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Charger les ventes au démarrage
        for (Vente vente : controller.getVentes()) {
            tableModel.addRow(new Object[]{
                    vente.getId(),
                    vente.getNom(),
                    vente.getQuantiteVendue(),
                    vente.getDateVente()
            });
        }

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nom = nomField.getText();
                    int quantiteVendue = Integer.parseInt(quantiteField.getText());
                    Date dateVente = new Date(); // Date actuelle pour la vente

                    Vente vente = new Vente(0, nom, quantiteVendue, dateVente);
                    controller.ajouterVente(vente);

                    tableModel.addRow(new Object[]{
                            vente.getId(),
                            vente.getNom(),
                            vente.getQuantiteVendue(),
                            vente.getDateVente()
                    });

                    nomField.setText("");
                    quantiteField.setText("");

                    JOptionPane.showMessageDialog(null, "Vente ajoutée avec succès !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la vente.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
    }
}

