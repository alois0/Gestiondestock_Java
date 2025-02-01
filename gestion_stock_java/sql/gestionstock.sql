CREATE TABLE fournisseur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    contact VARCHAR(100) NOT NULL
);

CREATE TABLE produit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix DOUBLE NOT NULL,
    quantite INT NOT NULL,
    fournisseur_id INT, -- Ajout de la clé étrangère pour le fournisseur
    FOREIGN KEY (fournisseur_id) REFERENCES fournisseur(id) ON DELETE SET NULL
);




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

CREATE TABLE user (
id INT AUTO_INCREMENT PRIMARY KEY,
nom VARCHAR(100),
mot_de_passe VARCHAR(100),
role VARCHAR(20) );






/**

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

**/