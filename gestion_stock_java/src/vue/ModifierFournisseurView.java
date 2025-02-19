package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
        setLocationRelativeTo(null); // ✅ Centrer la fenêtre

        // ✅ Table pour afficher les fournisseurs
        String[] columnNames = {"ID", "Nom", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableFournisseurs = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableFournisseurs);

        // ✅ Listener pour remplir les champs quand un fournisseur est sélectionné
        tableFournisseurs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tableFournisseurs.getSelectedRow() != -1) {
                    int selectedRow = tableFournisseurs.getSelectedRow();
                    textFieldNom.setText((String) tableModel.getValueAt(selectedRow, 1));
                    textFieldContact.setText((String) tableModel.getValueAt(selectedRow, 2));
                }
            }
        });

        // ✅ Panel pour modifier un fournisseur sélectionné
        JPanel panelModification = new JPanel(new GridLayout(3, 2));
        panelModification.add(new JLabel("Nom du Fournisseur:"));
        textFieldNom = new JTextField();
        panelModification.add(textFieldNom);
        panelModification.add(new JLabel("Contact:"));
        textFieldContact = new JTextField();
        panelModification.add(textFieldContact);

        // ✅ Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        btnModifierFournisseur = createStyledButton("Modifier Fournisseur");
        btnRetour = createStyledButton("Retour");

        panelBoutons.add(btnModifierFournisseur);
        panelBoutons.add(btnRetour);

        // ✅ Panel global pour contenir le formulaire + boutons
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.add(panelModification, BorderLayout.CENTER);
        panelBas.add(panelBoutons, BorderLayout.SOUTH);

        // ✅ Ajouter les éléments correctement
        add(scrollPane, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        // ✅ Événements des boutons
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
        fournisseurController.modifierFournisseur(fournisseur);
        JOptionPane.showMessageDialog(null, "Fournisseur modifié avec succès !");
        chargerFournisseurs();
    }

    /**
     * Méthode pour créer un bouton stylisé en gris.
     */
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
