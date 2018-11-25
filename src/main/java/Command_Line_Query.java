import java.sql.*;

public class Command_Line_Query {
    public static void main(String[] args) throws Exception {
        if (args.length == 5) {
            try {

                // Récupérer les arguments
                String jdbcurl = args[0];
                String drivername = args[1];
                String username = args[2];
                String password = args[3];
                String mysqlquery = args[4];


                System.out.println("Chargement du driver");
                Class.forName(drivername);
                System.out.println("ouverture de la connexion à la bas de données");
                Connection myConn = DriverManager.getConnection(jdbcurl, username, password);

                Statement myStmt = myConn.createStatement();

                System.out.println("Exécution de la requête sql");
                ResultSet myRs = myStmt.executeQuery(mysqlquery);

                ResultSetMetaData rsmd = myRs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                int rowID = 1;
                System.out.println("Print les résultats de la requête");
                while (myRs.next()) {
                    System.out.println("ROW = " + rowID);
                    for (int i = 1 ; i <= columnsNumber ; i++) {
                        System.out.println("    " + rsmd.getColumnName(i) + " = " + myRs.getString(i));
                    }
                    rowID++;
                }

                myRs.close();
                myStmt.close();

                System.out.println("Fin du programme");
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println("Error : " + ex);
            }

        } else {
            System.out.println("Invalid argument. Your command should be written like 'java Command_Line_Query \"JDBC:URL:FOR:DB\" myDb.Driver.Name DBUserName DBPassword \"SELECT MyDBQuery From MyMind\"'");
        }
    }

}
