package modele;

import java.util.ArrayList;
import java.util.List;


public class Rapport {
    private int id;
    private String nom;
    private List<Vente> ventes;

    public Rapport(){
        this.ventes = new ArrayList<>();
    }

    public Rapport(int id, String nom, List<Vente> ventes) {
        this.id = id;
        this.nom = nom;
        this.ventes = ventes;
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

    @Override
    public String toString() {
        return "Fournisseur{" + "id=" + id +
                ", nom=" + nom + '\'' +
                ", ventes=" + ventes +
                '}';
    }
}