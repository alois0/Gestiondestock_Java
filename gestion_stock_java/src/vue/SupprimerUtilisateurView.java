package vue;

import controleur.UserController;
import modele.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class SupprimerUtilisateurView extends JFrame {
    private JTable tableUtilisateurs;
    private DefaultTableModel tableModel;
    private JButton btnSupprimerUtilisateur;
    private JTextField textFieldRecherche;
    private JButton btnRetour;
    private UserController userController;

    public SupprimerUtilisateurView(User utilisateur) {
        this.userController = new UserController();
        setTitle("Supprimer un Utilisateur");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Barre de recherche
        JPanel panelRecherche = new JPanel();
        panelRecherche.add(new JLabel("🔎 Rechercher:"));
        textFieldRecherche = new JTextField(20);
        styliserChamp(textFieldRecherche);
        panelRecherche.add(textFieldRecherche);

        textFieldRecherche.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                rechercherUtilisateurs(textFieldRecherche.getText().trim());
            }
        });

        // Table pour afficher les utilisateurs
        String[] columnNames = {"ID", "Nom", "Rôle"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableUtilisateurs = new JTable(tableModel);
        styliserTable(tableUtilisateurs);
        JScrollPane scrollPane = new JScrollPane(tableUtilisateurs);

        // Boutons d'action
        JPanel panelBoutons = new JPanel();
        btnSupprimerUtilisateur = createStyledButton("Supprimer Utilisateur");
        btnRetour = createStyledButton("Retour");
        panelBoutons.add(btnSupprimerUtilisateur);
        panelBoutons.add(btnRetour);

        add(panelRecherche, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> dispose());
        btnSupprimerUtilisateur.addActionListener(e -> supprimerUtilisateur());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        chargerUtilisateurs();
    }

    private void chargerUtilisateurs() {
        List<User> utilisateurs = userController.getUtilisateurs();
        tableModel.setRowCount(0);
        for (User utilisateur : utilisateurs) {
            tableModel.addRow(new Object[]{
                    utilisateur.getId(),
                    utilisateur.getNom(),
                    utilisateur.getRole()
            });
        }
    }

    private void rechercherUtilisateurs(String recherche) {
        List<User> utilisateurs = userController.rechercherUtilisateurs(recherche);
        remplirTable(utilisateurs);
    }

    private void remplirTable(List<User> utilisateurs) {
        tableModel.setRowCount(0);
        for (User utilisateur : utilisateurs) {
            tableModel.addRow(new Object[]{
                    utilisateur.getId(),
                    utilisateur.getNom(),
                    utilisateur.getRole()
            });
        }
    }

    private void supprimerUtilisateur() {
        int selectedRow = tableUtilisateurs.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un utilisateur à supprimer !");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(
                null,
                "Êtes-vous sûr de vouloir supprimer cet utilisateur ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = userController.supprimerUtilisateur(id);
            if (success) {
                JOptionPane.showMessageDialog(null, "Utilisateur supprimé avec succès !");
                chargerUtilisateurs();
            } else {
                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression !");
            }
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(211, 211, 211)); // Gris clair
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
                    c.setBackground(new Color(230, 230, 230)); // Gris clair
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
    }
}
