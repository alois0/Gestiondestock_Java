package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VenteView {
    private JFrame frame;
    private JTextField textFieldProduit;
    private JTextField textFieldQuantite;
    private JTextField textFieldDate;
    private JButton btnAjouterVente;

    public VenteView() {
        frame = new JFrame("Gestion des Ventes");
        frame.setLayout(new FlowLayout());
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);

        JLabel labelProduit = new JLabel("Produit vendu:");
        textFieldProduit = new JTextField(20);

        JLabel labelQuantite = new JLabel("Quantité vendue:");
        textFieldQuantite = new JTextField(20);

        JLabel labelDate = new JLabel("Date de vente (YYYY-MM-DD):");
        textFieldDate = new JTextField(20);

        btnAjouterVente = new JButton("Ajouter Vente");

        frame.add(labelProduit);
        frame.add(textFieldProduit);
        frame.add(labelQuantite);
        frame.add(textFieldQuantite);
        frame.add(labelDate);
        frame.add(textFieldDate);
        frame.add(btnAjouterVente);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public String getProduitVendu() {
        return textFieldProduit.getText();
    }

    public int getQuantiteVendue() {
        try {
            return Integer.parseInt(textFieldQuantite.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Veuillez entrer une quantité valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    public String getDateVente() {
        return textFieldDate.getText();
    }

    public void clearFields() {
        textFieldProduit.setText("");
        textFieldQuantite.setText("");
        textFieldDate.setText("");
    }

    public void setAjouterVenteListener(ActionListener listener) {
        btnAjouterVente.addActionListener(listener);
    }
}
