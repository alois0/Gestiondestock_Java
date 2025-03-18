package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class SupprimerFournisseurView extends JFrame {
    private JTable tableFournisseurs;
    private DefaultTableModel tableModel;
    private JButton btnSupprimerFournisseur;

    private DefaultTableModel fournisseurTableModel;

    private JTextField textFieldRecherche;
    private JButton btnRetour;
    private FournisseurController fournisseurController;
    private User utilisateur;

    public SupprimerFournisseurView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.fournisseurController = new FournisseurController();
        setTitle("Supprimer un Fournisseur");
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
                rechercherFournisseurs(textFieldRecherche.getText().trim());
            }
        });

        // Table pour afficher les fournisseurs
        String[] columnNames = {"ID", "Nom", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableFournisseurs = new JTable(tableModel);
        styliserTable(tableFournisseurs);
        JScrollPane scrollPane = new JScrollPane(tableFournisseurs);

        // Boutons stylisés bien alignés en bas
        JPanel panelBoutons = new JPanel();
        btnSupprimerFournisseur = createStyledButton("Supprimer Fournisseur");
        btnRetour = createStyledButton("Retour");

        panelBoutons.add(btnSupprimerFournisseur);
        panelBoutons.add(btnRetour);

        // Panel global pour structurer les boutons
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.add(panelBoutons, BorderLayout.SOUTH);

        // Ajouter les éléments correctement
        add(panelRecherche, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

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
            chargerFournisseurs();
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
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(211, 211, 211));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Effet au survol (hover)
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

        // Effet au survol (hover)
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
