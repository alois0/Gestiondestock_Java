package test;

import static org.junit.jupiter.api.Assertions.*;

import modele.Produit;
import modele.Vente;
import controleur.ProduitController;
import controleur.VenteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

class TestUnitaireProduitVente {
    private Produit produit;
    private Vente vente;

    @BeforeEach
    public void setUp() {
        produit = new Produit(1, "Produit Test", 100.0, 20);

        vente = new Vente(1, produit, 5, new Date());

        // Ajouter la vente au produit
        produit.ajouterVente(vente);
    }

    @Test
    public void testProduitVenteRelation() {
        // Vérifier que la vente est bien associée au produit
        assertNotNull(produit.getVentes());
        assertEquals(1, produit.getVentes().size(), "Le produit devrait avoir une vente associée.");
        assertEquals(vente, produit.getVentes().get(0), "La vente associée au produit devrait être correcte.");

        // Vérifier que la vente est bien liée au bon produit
        assertEquals(produit, vente.getProduit(), "Le produit de la vente devrait correspondre au produit associé.");
    }

    @Test
    public void testAjoutVenteProduit() {
        // Créer un nouveau produit et une nouvelle vente
        Produit nouveauProduit = new Produit(2, "Produit 2", 200.0, 50);
        Vente nouvelleVente = new Vente(2, nouveauProduit, 10, new Date());

        // Ajouter la vente au produit
        nouveauProduit.ajouterVente(nouvelleVente);

        // Vérifier que la vente a été correctement ajoutée
        assertTrue(nouveauProduit.getVentes().contains(nouvelleVente), "La nouvelle vente devrait être associée au produit.");
    }
}
