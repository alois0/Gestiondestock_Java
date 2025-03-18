package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.Produit;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ConsulterFournisseurView extends JFrame {
    private JTable tableFournisseurs;
    private JTable tableProduits;
    private DefaultTableModel fournisseurTableModel;
    private DefaultTableModel produitTableModel;
    private JTextField textFieldRecherche;
    private JButton btnRetour;
    private JButton btnDesassocier;
    private FournisseurController fournisseurController;

    public ConsulterFournisseurView() {
        this.fournisseurController = new FournisseurController();
        setTitle("Consulter les Fournisseurs");
        setLayout(new BorderLayout());
        setSize(800, 500);

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


        // Table des fournisseurs
        String[] fournisseurColumns = {"ID", "Nom", "Contact"};
        fournisseurTableModel = new DefaultTableModel(fournisseurColumns, 0);
        tableFournisseurs = new JTable(fournisseurTableModel);
        styliserTable(tableFournisseurs);
        JScrollPane scrollFournisseurs = new JScrollPane(tableFournisseurs);



        // Bouton  retour
        JPanel panelBoutons = new JPanel();
        btnRetour = createStyledButton("Retour");
        panelBoutons.add(btnRetour);

        // Panel contenant les tables
        JPanel panelTables = new JPanel(new GridLayout(2, 1));
        panelTables.add(scrollFournisseurs);


        add(panelRecherche, BorderLayout.NORTH);
        add(panelTables, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> dispose());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerFournisseurs();




    }

    private void chargerFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurController.getFournisseurs();
        fournisseurTableModel.setRowCount(0);
        for (Fournisseur fournisseur : fournisseurs) {
            fournisseurTableModel.addRow(new Object[]{
                    fournisseur.getId(),
                    fournisseur.getNom(),
                    fournisseur.getContact()
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
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(211, 211, 211));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
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
