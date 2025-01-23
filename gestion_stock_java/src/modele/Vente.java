package modele;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Vente {
    private int id;
    private String nom;
    private int quantite_vendue;
    private Date date_vente;
    private Produit produit;




    
    public Vente(int idvente, String nom, int quantiteVendue, Date dateVente, Produit produit ) {
        this.id = idvente;
        this.nom = nom;
        this.quantite_vendue = quantiteVendue;
        this.date_vente = dateVente;
        this.produit = produit;

    }


    public Vente(int idvente, String nom, int quantiteVendue, Date dateVente) {
        this.id = idvente;
        this.nom = nom;
        this.quantite_vendue = quantiteVendue;
        this.date_vente = dateVente;

    }

    public Vente( String nom, int quantiteVendue, Date dateVente) {
        this.nom = nom;
        this.quantite_vendue = quantiteVendue;
        this.date_vente = dateVente;

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

    public int getQuantiteVendue() {
        return quantite_vendue;
    }

    public void setQuantiteVendue(int quantiteVendue) {
        this.quantite_vendue = quantiteVendue;
    }

    public Date getDateVente() {
        return date_vente;
    }

    public void setDateVente(Date dateVente) {
        this.date_vente = dateVente;
    }

    public Produit getProduit(){
        return produit;
    }

    public void setProduit(Produit produit){
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Fournisseur{" + "id=" + id +
                ", nom=" + nom + '\'' +
                ", quantitévendue='" + quantite_vendue + '\'' +
                ", date='" + date_vente + '\'' +

                ", produits=" + produit +
                '}';
    }
}

