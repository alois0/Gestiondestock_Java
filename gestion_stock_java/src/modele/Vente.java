package modele;

import java.util.Date;

public class Vente {
    private int id;
    private String nom;
    private int quantiteVendue;
    private Date dateVente;

    
    public Vente(int id, String nom, int quantiteVendue, Date dateVente) {
        this.id = id;
        this.nom = nom;
        this.quantiteVendue = quantiteVendue;
        this.dateVente = dateVente;
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
}

