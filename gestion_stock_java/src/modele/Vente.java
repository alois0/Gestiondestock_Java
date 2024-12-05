package modele;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Vente {
    private int id;
    private String nom;
    private int quantiteVendue;
    private Date dateVente;
    private List<Produit> produits;
    private List<Rapport> rapports;

    public Vente(){
        this.rapports = new ArrayList<>();
        this.produits = new ArrayList<>();
    }

    
    public Vente(int id, String nom, int quantiteVendue, Date dateVente, List<Produit> produits, List<Rapport> rapports ) {
        this.id = id;
        this.nom = nom;
        this.quantiteVendue = quantiteVendue;
        this.dateVente = dateVente;
        this.produits = produits;
        this.rapports = rapports;
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


    @Override
    public String toString() {
        return "Fournisseur{" + "id=" + id +
                ", nom=" + nom + '\'' +
                ", quantitévendue='" + quantiteVendue + '\'' +
                ", date='" + dateVente + '\'' +
                ", rapport='" + rapports + '\'' +
                ", produits=" + produits +
                '}';
    }
}

