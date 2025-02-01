package modele;

import java.util.ArrayList;
import java.util.List;

public class Fournisseur {
    private int id;
    private String nom;
    private String contact;
    private List<Produit> produits;

    // Liste statique de tous les fournisseurs
    private static List<Fournisseur> fournisseurs = new ArrayList<>();

    // Constructeur par défaut
    public Fournisseur() {
        this.produits = new ArrayList<>();
    }

    // Constructeur avec paramètres
    public Fournisseur(int id, String nom, String contact, List<Produit> produits) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
        this.produits = (produits != null) ? produits : new ArrayList<>();
    }


    public Fournisseur(int id, String nom, String contact) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }








    // Méthode toString()
    @Override
    public String toString() {
        return "Fournisseur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", contact='" + contact + '\'' +
                ", produits=" + produits +
                '}';
    }
}
