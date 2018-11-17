import java.sql.*;

public class Command_Line_Query {
    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                } catch (ClassNotFoundException nf) {
                    nf.printStackTrace();
                }
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8888/sakila",
                        "root", "root");
                System.out.println("here 0");

                Statement stmt = conn.createStatement();

                System.out.println("here 1");

                String sql_query = args[1];

                ResultSet rs = stmt.executeQuery(sql_query);

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                int rowID = 1;
                while (rs.next()) {
                    System.out.println("ROW = " + rowID);
                    for (int i = 0 ; i < columnsNumber ; i++) {
                        System.out.println("    " + rsmd.getColumnName(i) + " = " + rs.getString(i));
                    }
                    rowID++;
                }

                rs.close();
                stmt.close();

                System.out.println("here 2");
            } catch (SQLException ex) {
                System.out.println("Error : " + ex);
            }

        } else {
            System.out.println("Invalid argument. Your command should be written like 'java Command_Line_Query \"your sql query\"");
        }
    }

}
