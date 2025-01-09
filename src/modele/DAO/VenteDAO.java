package modele.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VenteDAO {
    public void ajouterVente(Vente vente){
        String query="INSERT INTO ventes(produit_id,quantite,date_vente)VALUES(?,?,?)";
        try(Connection connetion = Connexion.getConnection();
        PreparedStatement statement = Connection.prepareStatement(query)) {
            statement.setIn(1,vente.getProduit().getId());
            statement.setInt(2,vente.getQuantite());
            statement.setDate(3,vente.getDateVente());
            statement.executeUpdate();
        }catch(SQLException e) {
            System.out.printIn("Erreur lors de l'ajout de la vente :" +e.getMessage());
        }
    }
    
}
