package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        setLocationRelativeTo(null);

        // Table pour afficher les fournisseurs
        String[] columnNames = {"ID", "Nom", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableFournisseurs = new JTable(tableModel);
        styliserTable(tableFournisseurs);
        JScrollPane scrollPane = new JScrollPane(tableFournisseurs);


        // Listener pour remplir les champs quand un fournisseur est sélectionné
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

        // Panel pour modifier un fournisseur sélectionné
        JPanel panelModification = new JPanel(new GridLayout(3, 2));
        panelModification.add(new JLabel("Nom du Fournisseur:"));
        textFieldNom = new JTextField();
        styliserChamp(textFieldNom);
        panelModification.add(textFieldNom);
        panelModification.add(new JLabel("Contact:"));
        textFieldContact = new JTextField();
        styliserChamp(textFieldContact);
        panelModification.add(textFieldContact);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        btnModifierFournisseur = createStyledButton("Modifier Fournisseur");
        btnRetour = createStyledButton("Retour");

        panelBoutons.add(btnModifierFournisseur);
        panelBoutons.add(btnRetour);

        // Panel global pour contenir le formulaire + boutons
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.add(panelModification, BorderLayout.CENTER);
        panelBas.add(panelBoutons, BorderLayout.SOUTH);


        add(scrollPane, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);


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

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(211, 211, 211));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(169, 169, 169));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(211, 211, 211));
            }
        });

        return button;
    }

    private void styliserTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(180, 180, 180));
        header.setForeground(Color.BLACK);

        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(230, 230, 230));
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });
    }

    private void styliserChamp(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        champ.setBackground(new Color(240, 240, 240));
        champ.setForeground(Color.BLACK);
        champ.setOpaque(true);
        champ.setPreferredSize(new Dimension(200, 30));
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        champ.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                champ.setBackground(new Color(240, 240, 240));
            }
        });
    }
}
