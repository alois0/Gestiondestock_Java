package vue;

import controleur.UserController;
import modele.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ModifierUtilisateurView extends JFrame {
    private JTable tableUtilisateurs;
    private DefaultTableModel tableModel;
    private JTextField textFieldNom;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton btnModifierUtilisateur;
    private JTextField textFieldRecherche;
    private JButton btnRetour;
    private UserController userController;
    private User utilisateur;

    public ModifierUtilisateurView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.userController = new UserController();
        setTitle("Modifier un Utilisateur");
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

        // Listener pour remplir les champs automatiquement
        tableUtilisateurs.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && tableUtilisateurs.getSelectedRow() != -1) {
                int selectedRow = tableUtilisateurs.getSelectedRow();
                textFieldNom.setText((String) tableModel.getValueAt(selectedRow, 1));
                roleComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 2));
            }
        });

        // Panel pour modifier un utilisateur
        JPanel panelModification = new JPanel(new GridLayout(4, 2));
        panelModification.add(new JLabel("Nom d'utilisateur:"));
        textFieldNom = new JTextField();
        styliserChamp(textFieldNom);
        panelModification.add(textFieldNom);

        panelModification.add(new JLabel("Nouveau Mot de Passe:"));
        passwordField = new JPasswordField();
        styliserChamp(passwordField);
        panelModification.add(passwordField);

        panelModification.add(new JLabel("Nouveau Rôle:"));
        String[] roles = {"admin", "manager", "user"};
        roleComboBox = new JComboBox<>(roles);
        styliserChamp(roleComboBox);
        panelModification.add(roleComboBox);

        // Boutons stylisés
        JPanel panelBoutons = new JPanel();
        btnModifierUtilisateur = createStyledButton("Modifier Utilisateur");
        btnRetour = createStyledButton("Retour");
        panelBoutons.add(btnModifierUtilisateur);
        panelBoutons.add(btnRetour);

        // Conteneur en bas avec les champs et les boutons
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.add(panelModification, BorderLayout.CENTER);
        panelBas.add(panelBoutons, BorderLayout.SOUTH);

        add(panelRecherche, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> dispose());
        btnModifierUtilisateur.addActionListener(e -> modifierUtilisateur());

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

    private void modifierUtilisateur() {
        int selectedRow = tableUtilisateurs.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un utilisateur à modifier !");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nom = textFieldNom.getText().trim();
        String motDePasse = new String(passwordField.getPassword()).trim();
        String role = (String) roleComboBox.getSelectedItem();

        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le nom d'utilisateur ne peut pas être vide !");
            return;
        }

        boolean success = userController.modifierUtilisateur(id, nom, motDePasse, role);
        if (success) {
            JOptionPane.showMessageDialog(null, "Utilisateur modifié avec succès !");
            chargerUtilisateurs();
        } else {
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification !");
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

    private void styliserChamp(JComponent champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        champ.setBackground(new Color(240, 240, 240));
        champ.setForeground(Color.BLACK);
        champ.setOpaque(true);
        champ.setPreferredSize(new Dimension(200, 30));
    }
}
