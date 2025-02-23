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
    fournisseur_id INT,
    FOREIGN KEY (fournisseur_id) REFERENCES fournisseur(id) ON DELETE SET NULL
);




CREATE TABLE vente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    quantite_vendue INT NOT NULL,
    date_vente DATETIME NOT NULL,
    produit_id INT NOT NULL
    FOREIGN KEY (produit_id) REFERENCES produit(id) ON DELETE CASCADE;

);


CREATE TABLE user (
id INT AUTO_INCREMENT PRIMARY KEY,
nom VARCHAR(100),
mot_de_passe VARCHAR(100),
role VARCHAR(20) );





