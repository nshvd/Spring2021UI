package utils.DB;

import org.junit.Assert;
import utils.ConfigReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
    private static Connection connection;
    private static Statement statement;

    // Method to open connection to a specified DB
    public static Connection openConnection(String db) {
        if (connection == null) {
            // if connection is not open yet, open new connection
            String jdbcLink = ConfigReader.getProperty("jdbcLink")
                    //set database name for jdbc driver.
                    .replace("<db>", db)
                    .replace("<user>", System.getenv("DbUser"))
                    .replace("<password>", System.getenv("DbPassword"));
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

    /**
     * String q = "SELECT * FROM account WHERE name = ? AND id = ?";
     * String name = "new account";
     * int id = 1;
     * query(q, name, id);
     *
     */

                                                // Varargs
    public static List<Map<String, Object>> query(String query, Object... params) {
        try {
            // Prepared statement requires a query in order to be created
            PreparedStatement preparedStatement
                    = connection.prepareStatement(query);
            // loop trough the list of params in order to set them in the query
            // first ? will be replaced with the 1st param from params list
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            // execute the query
            return convertRsToTable(preparedStatement.executeQuery());
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Method will return a list of column names from a result set
    private static List<String> getColumnNames(ResultSet rs) throws SQLException {
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


    // Method that converts result set into a list of maps
    private static List<Map<String, Object>> convertRsToTable(ResultSet resultSet)
            throws SQLException {
        List<Map<String, Object>> table = new ArrayList<>();
        // get column names by using our custom method
        List<String> columnNames = getColumnNames(resultSet);
        // loop through each row of the result set
        while (resultSet.next()) {
            // for each row we will create a new map
            // key = column name
            // value = data from that column
            Map<String, Object> row = new HashMap<>();
            // in order to populate map we are looping through column names
            for (String columnName : columnNames) {
                // insert each column name plus the value from that column in a row map
                row.put(columnName, resultSet.getObject(columnName));
            }
            // insert our map into our list
            table.add(row);
        }
        return table;
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
