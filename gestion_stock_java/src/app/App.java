package app;

import modele.Connexion;
import vue.LoginView;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // 🔹 Utilisation de SwingUtilities pour exécuter l'interface graphique dans le thread principal
        SwingUtilities.invokeLater(() -> {
            System.out.println("🔹 Lancement de l'application...");

            // 📌 Vérification de la connexion à la base de données
            if (Connexion.getConnection() == null) {
                System.err.println("❌ ERREUR : Impossible de se connecter à la base de données !");
                JOptionPane.showMessageDialog(null, "Erreur : Connexion à la base de données impossible !", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            System.out.println("✅ Connexion réussie à la base de données !");

            // 📌 Lancement de l'interface de connexion
            new LoginView();
            System.out.println("✅ Interface de connexion affichée !");
        });
    }
}
