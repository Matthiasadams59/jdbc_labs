import java.sql.*;

public class Command_Line_Query {
    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            try {
                System.out.println("Chargement du driver");
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                System.out.println("ouverture de la connexion à la bas de données");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/sakila",
                        "root", "root");

                Statement stmt = conn.createStatement();

                System.out.println("Récupération de la requête sql de la command line");
                String sql_query = args[0];

                System.out.println("Exécution de la requête sql");
                ResultSet rs = stmt.executeQuery(sql_query);

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                int rowID = 1;
                System.out.println("Print les résultats de la requête");
                while (rs.next()) {
                    System.out.println("ROW = " + rowID);
                    for (int i = 1 ; i <= columnsNumber ; i++) {
                        System.out.println("    " + rsmd.getColumnName(i) + " = " + rs.getString(i));
                    }
                    rowID++;
                }

                rs.close();
                stmt.close();

                System.out.println("Fin du programme");
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println("Error : " + ex);
            }

        } else {
            System.out.println("Invalid argument. Your command should be written like 'java Command_Line_Query \"your sql query\"'");
        }
    }

}
