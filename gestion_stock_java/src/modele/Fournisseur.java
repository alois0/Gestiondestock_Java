package modele;

public class Fournisseur {
    private int id;         
    private String nom;      
    private String contact;
    private String adresse;
    private String produits;  


    
    public Fournisseur(int id, String nom, String contact) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Fournisseur {" 
        + "id: " + id 
        + ", nom: " + nom 
        + ", adresse" + adresse 
        + ", produits" + produits 
        + "}";
    }
    
}
