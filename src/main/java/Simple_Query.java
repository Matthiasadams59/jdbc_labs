import java.sql.*;
//import com.mysql.jdbc.Driver;

public class Simple_Query {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Chargement du driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("ouverture de la connexion à la bas de données");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/sakila",
                "root", "root");

            Statement stmt = conn.createStatement();

            System.out.println("here 1");
            System.out.println("Exécution de la requête sql");
            ResultSet rs = stmt.executeQuery("SELECT last_name from actor;");
            int rowID = 1;
            System.out.println("Print les résultats de la requête");
            while (rs.next()) {
                // print the values for the current row.
                String current_last_name = rs.getString("last_name");
                System.out.println("ROW = " + rowID + ", last name =  " + current_last_name);
                rowID++;
            }

            rs.close();
            stmt.close();

            System.out.println("Fin du programme");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error : " + ex);
        }


    }
}
