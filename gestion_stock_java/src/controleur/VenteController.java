package controleur;

import modele.DAO.VenteDAO;
import modele.Vente;

import java.util.Date;
import java.util.List;

public class VenteController {
    private VenteDAO venteDAO;

    public VenteController() {
        this.venteDAO = new VenteDAO();
    }

    // Afficher toutes les ventes
    public void afficherVentes() {
        List<Vente> ventes = venteDAO.getVente();
        if (ventes.isEmpty()) {
            System.out.println("Aucune vente enregistrée.");
        } else {
            for (Vente vente : ventes) {
                System.out.println(vente);
            }
        }
    }

    // Ajouter une vente
    public void ajouterVente(String nomProduit, int quantite, Date dateVente) {
        Vente vente = new Vente(0, nomProduit, quantite, dateVente);
        venteDAO.ajouterVente(vente);
    }

    // Rechercher une vente par ID
    public void afficherVenteParId(int id) {
        Vente vente = venteDAO.trouverVenteParId(id);
        if (vente != null) {
            System.out.println("Vente trouvée : " + vente);
        } else {
            System.out.println("Aucune vente trouvée avec l'ID " + id);
        }
    }

    // Supprimer une vente
    public void supprimerVente(int id) {
        venteDAO.supprimerVente(id);
    }
}
