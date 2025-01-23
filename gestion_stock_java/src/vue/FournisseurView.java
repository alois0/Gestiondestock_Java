package vue;

import controleur.FournisseurControlleur;
import modele.Fournisseur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FournisseurView extends JFrame {
    private FournisseurControlleur controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public FournisseurView(FournisseurControlleur controller) {
        this.controller = controller;

        setTitle("Gestion des Fournisseurs");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel();
        JTextField nomField = new JTextField(15);
        JTextField contactField = new JTextField(15); // Champ de texte pour le contact
        JButton ajouterButton = new JButton("Ajouter");

        formPanel.add(new JLabel("Nom : "));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Contact : "));
        formPanel.add(contactField);
        formPanel.add(ajouterButton);

        JTextField rechercheField = new JTextField(15);
        JButton rechercheButton = new JButton("Rechercher");
        formPanel.add(new JLabel("Rechercher : "));
        formPanel.add(rechercheField);
        formPanel.add(rechercheButton);


        String[] columnNames = {"ID", "Nom", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);


        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nom = nomField.getText();
                    String contact = contactField.getText();
                    Fournisseur fournisseur = new Fournisseur(0, nom, contact, null);
                    controller.ajouterFournisseur(fournisseur);

                    // Ajouter au tableau
                    tableModel.addRow(new Object[]{fournisseur.getId(), fournisseur.getNom(), fournisseur.getContact()});

                    // Réinitialiser les champs
                    nomField.setText("");
                    contactField.setText("");

                    JOptionPane.showMessageDialog(null, "Fournisseur ajouté !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        rechercheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rechercheNom = rechercheField.getText();
                List<Fournisseur> resultats = controller.rechercherFournisseurParNom(rechercheNom);

                tableModel.setRowCount(0);
                for (Fournisseur fournisseur : resultats) {
                    tableModel.addRow(new Object[]{fournisseur.getId(), fournisseur.getNom(), fournisseur.getContact()});
                }
            }
        });


        List<Fournisseur> fournisseurs = controller.getFournisseurs();
        for (Fournisseur fournisseur : fournisseurs) {
            tableModel.addRow(new Object[]{fournisseur.getId(), fournisseur.getNom(), fournisseur.getContact()});
        }

        add(panel);
    }
}
