package utils;

import org.junit.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static Connection connection;
    private static Statement statement;

    // Method to open connection to a specified DB
    public static Connection openConnection(String db) {
        if (connection == null) {
            // if connection is not open yet, open new connection
            String jdbcLink = ConfigReader.getProperty("jdbcLink")
                    //set database name for jdbc driver.
                    .replace("<db>", db);
            try {
                connection = DriverManager.getConnection(jdbcLink);
                statement = connection.createStatement();
            } catch (SQLException e) {
                Assert.fail("Unable to establish connection with DB:\n"
                        + e.getMessage());
            }
        }
        return connection;
    }

    public static Connection openConnection() {
        return openConnection("digitalbank");
    }

    // Method will return a list of column names from a result set
    public static List<String> getColumnNames(ResultSet rs) throws SQLException {
        // get metadata from a result set
        ResultSetMetaData metaData = rs.getMetaData();
        // list where column names will be stored
        List<String> columns = new ArrayList<>();
        // using column count, loop through column indexes
        for (int i = 1; i <= metaData.getColumnCount() ; i++) {
            // add every column name into our list
            columns.add(metaData.getColumnName(i));
        }
        return columns;
    }

    public static ResultSet query(String query) {
        // if connection wasn't open open new connection
        if (statement == null) openConnection();
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // closing connection method
    public static void close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
