package vue;

import controleur.FournisseurController;
import modele.Fournisseur;
import modele.User;

import javax.swing.*;
import java.awt.*;

public class AjouterFournisseurView extends JFrame {
    private JTextField textFieldNom;
    private JTextField textFieldContact;
    private JButton btnAjouterFournisseur;
    private JButton btnRetour;
    private FournisseurController fournisseurController;
    private User utilisateur;

    public AjouterFournisseurView(User utilisateur) {
        this.utilisateur = utilisateur;
        this.fournisseurController = new FournisseurController();
        setTitle("Ajouter un Fournisseur");
        setLayout(new GridLayout(3, 2));
        setSize(400, 200);

        // Champs de saisie
        add(new JLabel("Nom du Fournisseur:"));
        textFieldNom = new JTextField();
        add(textFieldNom);

        add(new JLabel("Contact:"));
        textFieldContact = new JTextField();
        add(textFieldContact);

        btnAjouterFournisseur = new JButton("Ajouter Fournisseur");
        btnRetour = new JButton("Retour");
        add(btnAjouterFournisseur);
        add(btnRetour);

        btnRetour.addActionListener(e -> dispose());
        btnAjouterFournisseur.addActionListener(e -> ajouterFournisseur());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void ajouterFournisseur() {
        String nom = textFieldNom.getText().trim();
        String contact = textFieldContact.getText().trim();

        if (nom.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs !");
            return;
        }

        Fournisseur fournisseur = new Fournisseur(0, nom, contact);
        fournisseurController.ajouterFournisseur(fournisseur);
        JOptionPane.showMessageDialog(null, "Fournisseur ajouté avec succès !");

        // Effacer les champs après l'ajout
        textFieldNom.setText("");
        textFieldContact.setText("");
    }
}
