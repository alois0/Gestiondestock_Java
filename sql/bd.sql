CREATE TABLE produit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix DOUBLE NOT NULL,
    quantite INT NOT NULL,
    FOREIGN KEY (fournisseur_id) REFERENCES fournisseur(id) ON DELETE CASCADE
);

CREATE TABLE fournisseur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    contact VARCHAR(100) NOT NULL
    FOREIGN KEY(produit_id) REFERENCES produit(id) ON DELETE CASCADE
);

/**
CREATE TABLE fournir (
    produit_id INT NOT NULL,
    fournisseur_id INT NOT NULL,
    PRIMARY KEY (produit_id, fournisseur_id),
    FOREIGN KEY (produit_id) REFERENCES produit(id) ON DELETE CASCADE,
    FOREIGN KEY (fournisseur_id) REFERENCES fournisseur(id) ON DELETE CASCADE
);
**/
CREATE TABLE vente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    quantite_vendue INT NOT NULL,
    date_vente DATE NOT NULL
);

CREATE TABLE rapport (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description Text
);

CREATE TABLE vente_rapport (
    vente_id INT NOT NULL,
    rapport_id INT NOT NULL,
    PRIMARY KEY (vente_id, rapport_id),
    FOREIGN KEY (vente_id) REFERENCES vente(id) ON DELETE CASCADE,
    FOREIGN KEY (rapport_id) REFERENCES rapport(id) ON DELETE CASCADE
);

CREATE TABLE livre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(20) NOT NULL,
    annee INT NOT NULL,
    ISBN VARCHAR(20)
)

CREATE TABLE auteur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(20),
    prenom VARCHAR(20)
)

CREATE TABLE livre_auteur (
    id_livre INT NOT NULL,
    id_auteur INT NOT NULL?
    PRIMARY KEY(id_livre, id_auteur),
    FOREIGN KEY (id_livre) REFERENCES livre(id) ON DELETE CASCADE,
    FOREIGN KEY (id_auteur) REFERENCES auteur(id) ON DELETE CASCADE
)


CREATE TABLE utilisateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(20) NOT NULL,
    prenom VARCHAR(20) NOT NULL
    addresse VARCHAR(20) NOT NULL
)

CREATE TABLE emprunt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_emprunt DATE NOT NULL,
    date_retour DATE NOT NULL,
)
