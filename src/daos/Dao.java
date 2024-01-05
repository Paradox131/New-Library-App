package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {


    private String databaseName;
    // Included to allow for dependency injection - connection can be injected in and reused
    private Connection con;

    public Dao(String databaseName) {
        this.databaseName = databaseName;
    }

    public Dao(Connection con) {
        this.con = con;
    }

    public Connection getConnection() {
        // If there is a connection already present in the object, use that
        if (con != null) {
            return con;
        }
        // Otherwise, create one and return it
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;
        String username = "root";
        String password = "";
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex1) {
            System.out.println("Failed to find driver class " + ex1.getMessage());
            System.exit(1);
        } catch (SQLException ex2) {
            System.out.println("Connection failed " + ex2.getMessage());
        }
        return con;
    }

    public void freeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }
}