package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SupprimerFournisseurView extends JFrame {
    private JTable tableFournisseurs;
    private DefaultTableModel tableModel;
    private JButton btnSupprimerFournisseur;
    private JButton btnRetour;
    private FournisseurController fournisseurController;
    private User utilisateur;

    public SupprimerFournisseurView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.fournisseurController = new FournisseurController();
        setTitle("Supprimer un Fournisseur");
        setLayout(new BorderLayout());
        setSize(600, 400);

        // Table pour afficher les fournisseurs
        String[] columnNames = {"ID", "Nom", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableFournisseurs = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableFournisseurs);

        // Boutons d'action
        JPanel panelBoutons = new JPanel();
        btnSupprimerFournisseur = new JButton("Supprimer Fournisseur");
        btnRetour = new JButton("Retour");
        panelBoutons.add(btnSupprimerFournisseur);
        panelBoutons.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> dispose());
        btnSupprimerFournisseur.addActionListener(e -> supprimerFournisseur());

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

    private void supprimerFournisseur() {
        int selectedRow = tableFournisseurs.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fournisseur à supprimer !");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce fournisseur ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            fournisseurController.supprimerFournisseur(id);
            JOptionPane.showMessageDialog(null, "Fournisseur supprimé avec succès !");
            chargerFournisseurs();
        }
    }
}
