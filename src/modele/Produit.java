package modele;


public class Produit {
    private int id;
    private String nom;
    private double prix;
    private int quantite;
    

    public Produit(int id, String nom, double prix, int quantite) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
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



    @Override
    public String toString() {
        return "Fournisseur{" + "id=" + id +
                ", nom=" + nom + '\'' +
                ", prix='" + prix + '\'' +
                ", quantité=" + quantite +
                '}';
    }
}