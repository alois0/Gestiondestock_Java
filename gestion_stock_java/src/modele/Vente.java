package modele;

import java.util.Date;

public class Vente {
    private int id;
    private String nomProduit;
    private int produitId;
    private int quantiteVendue;
    private Date dateVente;
    private Produit produit;

    // 🔹 Constructeur COMPLET avec Produit
    public Vente(int id, String nomProduit, int produitId, int quantiteVendue, Date dateVente, Produit produit) {
        this.id = id;
        this.nomProduit = nomProduit;
        this.produitId = produitId;
        this.quantiteVendue = quantiteVendue;
        this.dateVente = dateVente;
        this.produit = produit;
    }

    // 🔹 Constructeur pour une nouvelle vente (sans ID, car auto-généré par la base)
    public Vente(String nomProduit, int produitId, int quantiteVendue, Date dateVente) {
        this.nomProduit = nomProduit;
        this.produitId = produitId;
        this.quantiteVendue = quantiteVendue;
        this.dateVente = dateVente;
    }

    public Vente(int id, String nomProduit, int quantiteVendue, Date dateVente, double montantTotal) {
        this.id = id;
        this.nomProduit = nomProduit;
        this.quantiteVendue = quantiteVendue;
        this.dateVente = dateVente;
        this.produit = null; // Pas besoin d'un objet Produit ici
    }





    public double getMontantTotal() {
        return (produit != null) ? produit.getPrix() * quantiteVendue : 0.0;
    }


    // ✅ Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public int getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(int quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Vente{" +
                "id=" + id +
                ", produit='" + nomProduit + '\'' +
                ", produitId=" + produitId +
                ", quantiteVendue=" + quantiteVendue +
                ", dateVente=" + dateVente +
                ", produit=" + produit +
                '}';
    }
}
