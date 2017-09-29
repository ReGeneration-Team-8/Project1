package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnect {

    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Insurances";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String MAX_POOL = "100"; // return results limit

    private Connection connection;
    private Properties properties;

    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }


    public Connection connect() {    // connect to database
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                // Java 7+
                e.printStackTrace();
            }
        }
        return connection;
    }


    public void disconnect() {    // disconnect database
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
