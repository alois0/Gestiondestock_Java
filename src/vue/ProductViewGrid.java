package vue;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ProductViewGrid {
    private JFrame frame;
    private JTextField txtCode;
    private JTextField txtNom;
    private JButton btnAjouter;
    private JLabel labelCode;
    private JLabel labelNom;
    private JLabel imageLabel;

    public ProductViewGrid() {
        // Créer une nouvelle fenêtre (JFrame) avec un titre
        frame = new JFrame("Ajouter un produit");

        // Créer un JPanel pour ajouter les marges à gauche et à droite
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(4, 2, 10, 10)); // GridLayout avec espacement de 10px
        contentPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Marge de 10px en haut et en bas, 20px à gauche et à droite

        // Créer des labels pour "Code Produit" et "Nom Produit"
        labelCode = new JLabel("Code Produit:");
        labelNom = new JLabel("Nom Produit:");

        // Créer des champs de texte pour saisir le code et le nom du produit
        txtCode = new JTextField(15);  // Largeur du champ de texte
        txtNom = new JTextField(15);  // Largeur du champ de texte

        // Créer un bouton pour ajouter le produit
        btnAjouter = new JButton("Ajouter");

        // Créer un label pour afficher l'image
        imageLabel = new JLabel(new ImageIcon("path_to_image.jpg")); // Remplacer par le chemin réel de l'image

        // Ajouter les composants au JPanel (dans un ordre bien défini)
        contentPanel.add(labelCode);
        contentPanel.add(txtCode);
        contentPanel.add(labelNom);
        contentPanel.add(txtNom);
        contentPanel.add(new JLabel());  // Une ligne vide pour la séparation avant l'image
        contentPanel.add(imageLabel);  // L'image
        contentPanel.add(new JLabel());  // Une autre ligne vide
        contentPanel.add(btnAjouter);   // Le bouton pour ajouter le produit

        // Ajouter le JPanel contenant le formulaire dans la fenêtre
        frame.add(contentPanel, BorderLayout.CENTER);

        // Configurer la fenêtre (taille plus petite)
        frame.setSize(400, 300);  // Taille réduite de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Fermer la fenêtre correctement
        frame.setVisible(true);  // Rendre la fenêtre visible
    }


}
