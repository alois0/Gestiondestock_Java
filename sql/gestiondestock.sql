CREATE TABLE produit (
    id_produits INT AUTO_INCREMENT,
    nom VARCHAR(250) NOT NULL,
    prix DOUBLE NOT NULL,
    quantite INT NOT NULL,
    PRIMARY KEY(id_produits),
    FOREIGN KEY (fournisseur_id) REFERENCES fournisseur(id) ON DELETE CASCADE
);

CREATE TABLE fournisseur (
    id_fournisseur INT AUTO_INCREMENT,
    nom VARCHAR(250) NOT NULL,
    contact VARCHAR(250) NOT NULL,
    PRIMARY KEY(id_fournisseur),
    FOREIGN KEY(produit_id) REFERENCES produit(id) ON DELETE CASCADE
);


CREATE TABLE vente (
    id_vente INT AUTO_INCREMENT ,
    nom VARCHAR(250) NOT NULL,
    quantite_vendue INT NOT NULL,
    date_vente DATE NOT NULL,
    PRIMARY KEY(id_vente)
);

CREATE TABLE rapport (
    id_table INT AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    description Text
    PRIMARY KEY(id_table)
);

CREATE TABLE vente_rapport (
    vente_id INT NOT NULL,
    rapport_id INT NOT NULL,
    PRIMARY KEY (vente_id, rapport_id),
    FOREIGN KEY (vente_id) REFERENCES vente(id) ON DELETE CASCADE,
    FOREIGN KEY (rapport_id) REFERENCES rapport(id) ON DELETE CASCADE
);



