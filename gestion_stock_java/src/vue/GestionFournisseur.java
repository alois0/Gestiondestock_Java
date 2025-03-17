package vue;

import controleur.ProduitController;
import modele.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionFournisseur extends JFrame {
    private User utilisateur;

    private ProduitController produitController;
    public GestionFournisseur(User utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Gestion des Fournisseurs");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Gestionnaire de positionnement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Titre
        JLabel titleLabel = new JLabel("Gestion des Fournisseurs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, gbc);

        JButton ajouterFournisseurButton = createStyledButton("Ajouter Fournisseur");
        JButton modifierFournisseurButton = createStyledButton("Modifier Fournisseur");
        JButton supprimerFournisseurButton = createStyledButton("Supprimer Fournisseur");
        JButton ConsulterFournisseurButton = createStyledButton("Consulter Fournisseur");
        JButton btnRetour = createStyledButton("Retour");

        ajouterFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("📢 Ouverture de l'interface d'ajout de fournisseur...");

                AjouterFournisseurView vue = new AjouterFournisseurView(utilisateur);
            }
        });

        modifierFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("📢 Ouverture de l'interface de modification de fournisseur...");

                ModifierFournisseurView vue = new ModifierFournisseurView(utilisateur);
            }
        });

        supprimerFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("📢 Ouverture de l'interface de suppression de fournisseur...");

                SupprimerFournisseurView vue = new SupprimerFournisseurView(utilisateur);


            }
        });


        ConsulterFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("📢 Ouverture de l'interface de consultation de fournisseur...");

                ConsulterFournisseurView vue = new ConsulterFournisseurView();


            }
        });




// Désactivation du bouton suppression si l'utilisateur N'EST PAS un manager
        if (!utilisateur.getRole().equals("admin")) {
            supprimerFournisseurButton.setEnabled(false); // Grise le bouton
        }

        add(ajouterFournisseurButton, gbc);
        add(modifierFournisseurButton, gbc);
        add(supprimerFournisseurButton, gbc);
        add(ConsulterFournisseurButton, gbc);



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
