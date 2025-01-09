package modele.DAO;

import java.sql.DriverManager;
import java.sql.SQLException;


public class Connexion {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_stock";
    private static final String USER = "root";
    private static final String PASSWORD ="";



    private static Connection connection;

    private Connexion() {}

    public static Connection getConnection(){
        if(connection == null){
            try {
                connection= DriverManager.getConnection(URL,USER,PASSWORD);
            }catch(SQLException e) {
                System.out.println("Erreur de connexion à la base de données :" +e.getMessage());
            }
        }

        return connection;
    }
    
}
