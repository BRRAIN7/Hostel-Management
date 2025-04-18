package Services;

import java.sql.*;


public class DBController {
    public static Connection connect() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver"); // this is the name of the driver
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel","root","Brrain7@mysql");
        return con;
    }
}
