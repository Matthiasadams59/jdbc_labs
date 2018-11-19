import java.sql.*;
//import com.mysql.jdbc.Driver;

public class Simple_Query {
    public static void main(String[] args) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/sakila",
                "root", "root");
            System.out.println("here 0");

            Statement stmt = conn.createStatement();

            System.out.println("here 1");

            ResultSet rs = stmt.executeQuery("SELECT last_name from actor;");
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
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error : " + ex);
        }


    }
}
