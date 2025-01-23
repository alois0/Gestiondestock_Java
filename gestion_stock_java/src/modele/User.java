package modele;

public class User {
    private int id;
    private String nom;
    private String motDePasse;

    private String role;

    // Constructeurs, getters et setters
    public User(int id, String nom, String motDePasse, String role) {
        this.id = id;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.role = role;
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

    public String getMotDePasse() {
        return nom;
    }

        public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
