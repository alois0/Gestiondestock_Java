package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ModifierFournisseurView extends JFrame {
    private JTable tableFournisseurs;
    private DefaultTableModel tableModel;
    private JTextField textFieldNom;
    private JTextField textFieldContact;
    private JButton btnModifierFournisseur;
    private JButton btnRetour;
    private FournisseurController fournisseurController;
    private User utilisateur;

    public ModifierFournisseurView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.fournisseurController = new FournisseurController();
        setTitle("Modifier un Fournisseur");
        setLayout(new BorderLayout());
        setSize(600, 400);

        // Table pour afficher les fournisseurs
        String[] columnNames = {"ID", "Nom", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableFournisseurs = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableFournisseurs);

        tableFournisseurs.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableFournisseurs.getSelectedRow() != -1) {
                int selectedRow = tableFournisseurs.getSelectedRow();
                textFieldNom.setText((String) tableModel.getValueAt(selectedRow, 1));
                textFieldContact.setText((String) tableModel.getValueAt(selectedRow, 2));
            }
        });

        // Panel pour modifier un fournisseur sélectionné
        JPanel panelModification = new JPanel(new GridLayout(3, 2));
        panelModification.add(new JLabel("Nom du Fournisseur:"));
        textFieldNom = new JTextField();
        panelModification.add(textFieldNom);
        panelModification.add(new JLabel("Contact:"));
        textFieldContact = new JTextField();
        panelModification.add(textFieldContact);

        btnModifierFournisseur = new JButton("Modifier Fournisseur");
        btnRetour = new JButton("Retour");
        panelModification.add(btnModifierFournisseur);
        panelModification.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(panelModification, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> dispose());
        btnModifierFournisseur.addActionListener(e -> modifierFournisseur());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerFournisseurs();
    }

    private void chargerFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurController.getFournisseurs();
        tableModel.setRowCount(0);
        for (Fournisseur fournisseur : fournisseurs) {
            tableModel.addRow(new Object[]{
                    fournisseur.getId(),
                    fournisseur.getNom(),
                    fournisseur.getContact()
            });
        }
    }

    private void modifierFournisseur() {
        int selectedRow = tableFournisseurs.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fournisseur à modifier !");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nom = textFieldNom.getText().trim();
        String contact = textFieldContact.getText().trim();

        if (nom.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs !");
            return;
        }

        Fournisseur fournisseur = new Fournisseur(id, nom, contact);
        fournisseurController.modifierFournisseur(fournisseur); // Correction ici ✅
        JOptionPane.showMessageDialog(null, "Fournisseur modifié avec succès !");
        chargerFournisseurs();
    }


}
