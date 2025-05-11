package modele;


import java.util.ArrayList;
import java.util.List;

public class Produit {
    private int id;
    private String nom;
    private double prix;
    private int quantite;

    private List<Vente> ventes;

    private String fournisseur;

    private int fournisseurId;



    public Produit() {
        this.ventes = new ArrayList<>();
    }



    public Produit(int id, String nom, double prix, int quantite) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    public Produit(int id, String nom, double prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    public void ajouterVente(Vente vente) {
        if (ventes == null) {
            ventes = new ArrayList<>(); // Empêche NullPointerException
        }
        if (vente != null) {
            this.ventes.add(vente);
        }
    }


    public List<Vente> getVentes() {
        return ventes;
    }




    public Produit(int id, String nom, double prix, int quantite, List<Vente> ventes) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.ventes = (ventes != null) ? ventes : new ArrayList<>(); // Évite NullPointerException
    }

    public Produit(int id, String nom, int quantite, String fournisseur) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.fournisseur = fournisseur;
    }

    public Produit(int id, String nom, double prix, int quantite, String fournisseur) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.fournisseur = fournisseur;
    }

    public Produit(int id, String nom, double prix, int quantite, int fournisseurId) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.fournisseurId = fournisseurId;
    }






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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getFournisseur() { //  Ajout du getter
        return fournisseur;
    }


    @Override
    public String toString() {
        return "Produit{" + "id=" + id +
                ", nom=" + nom + '\'' +
                ", prix='" + prix + '\'' +
                ", quantité=" + quantite +
                '}';
    }

    public int getFournisseurId() {
        return fournisseurId;
    }


}
