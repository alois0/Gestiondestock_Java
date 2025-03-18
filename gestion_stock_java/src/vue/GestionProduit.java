package vue;

import controleur.ProduitController;
import modele.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionProduit extends JFrame {
    private User utilisateur;

    private ProduitController produitController;
    public GestionProduit(User utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Gestion des Produits");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Gestionnaire de positionnement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les éléments
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Titre
        JLabel titleLabel = new JLabel("Gestion des Produits");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, gbc);

        // Boutons
        JButton ajouterProduitButton = createStyledButton("Ajouter Produit");
        JButton modifierProduitButton = createStyledButton("Modifier Produit");
        JButton supprimerProduitButton = createStyledButton("Supprimer Produit");
        JButton consulterProduitButton = createStyledButton("Consulter Produits");
        JButton rapportStockButton = createStyledButton("Rapport Stock");
        JButton btnRetour = createStyledButton("Retour");

        ajouterProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("📢 Ouverture de l'interface d'ajout de produit...");

                AjouterProduitView vue = new AjouterProduitView(utilisateur);
                ProduitController controller = new ProduitController();

            }
        });

        modifierProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("📢 Ouverture de l'interface de modification de produit...");

                ModifierProduitView vue = new ModifierProduitView(utilisateur);
            }
        });

        supprimerProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("📢 Ouverture de l'interface de suppression de produit...");
                SupprimerProduitView vue = new SupprimerProduitView(utilisateur);
            }
        });


        consulterProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("📢 Ouverture de l'interface de consultation des produits...");
                new ConsulterProduitView();
            }
        });


        // Ajout de l'action pour générer le rapport stock
        rapportStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("📢 Génération du rapport sur l'état du stock...");
                new RapportStockView();
            }
        });

// Désactivation du bouton suppression si l'utilisateur N'EST PAS un manager
        if (!utilisateur.getRole().equals("admin")) {
            supprimerProduitButton.setEnabled(false); // Grise le bouton
        }

        add(ajouterProduitButton, gbc);
        add(modifierProduitButton, gbc);
        add(supprimerProduitButton, gbc);
        add(consulterProduitButton, gbc);
        add(rapportStockButton, gbc);

        gbc.insets = new Insets(20, 10, 10, 10);
        add(btnRetour, gbc);

        btnRetour.addActionListener(e -> dispose());


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
}
