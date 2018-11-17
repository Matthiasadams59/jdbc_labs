import java.sql.*;

public class Command_Line_Query {
    public static void main(String[] args) throws Exception {
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
            int rowID = 1;
            while (rs.next()) {
                // print the values for the current row.
                String current_last_name = rs.getString("last_name");
                System.out.println("ROW = " + rowID + ", last name =  " + current_last_name);
                rowID++;
            }

            rs.close();
            stmt.close();

            System.out.println("here 2");
        } catch (SQLException ex) {
            System.out.println("Error : " + ex);
        }


    }

}
