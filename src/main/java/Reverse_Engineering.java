import java.sql.*;

public class Reverse_Engineering {
    public static void main(String[] args) {
        try {
            System.out.println("Chargement du driver");
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("ouverture de la connexion à la bas de données");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/sakila", "root", "root");

            System.out.println("récupération du DatabaseMtaData");
            DatabaseMetaData dma = con.getMetaData();

            String[] types = new String[1];
            types[0] = "sakila"; //set table type mask

            System.out.println("Récupération de la liste des tables");
            ResultSet results = dma.getTables(null, null, "%", types);
            while (results.next()) {
                System.out.println("Traitement de la table " );
                for (int i = 0 ; i < results.getMetaData().getColumnCount() ; i++) {
                    System.out.print(results.getString(i) + " ");
                    System.out.println();
                }
            }

            //String dbname = dma.getDatabaseProductName();

        }
        catch(ClassNotFoundException | SQLException nf) {
            nf.printStackTrace();
        }

    }
}
