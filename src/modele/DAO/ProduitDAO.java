package modele.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProduitDAO {
    public void ajouterProduit(Produit produit){
        String query= "INSERT INTO produits(nom,prix) values (?,?)";
        try(Connection connection = Connexion.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,produit.getNom());
            statement;setDouble(2,produit.getPrix());
            statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Erreur lors de l'ajout du produit :" +e.getMessage());
        }
    }
    
}
