package utils.DB;

import org.junit.Assert;
import utils.ConfigReader;

import java.sql.*;


public class DBUtilsV2 {
    private static Connection connection;
    private static Statement statement;

    // Method to open connection to a specified DB
    public static Connection openConnection(String db) {
        if (connection == null) {
            // if connection is not open yet, open new connection
            String jdbcLink = ConfigReader.getProperty("jdbcLink")
                    //set database name for jdbc driver.
                    .replace("<db>", db)
                    // set username in jdbc link
                    .replace("<user>", System.getenv("DbUser"))
                    // set password in jdbc link
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
     */

    // Varargs
    public static ResultSetHandler query(String query, Object... params) {
        if (connection == null) openConnection();
        try {
            if(params.length == 0)
                return new ResultSetHandler(statement.executeQuery(query));
            // Prepared statement requires a query in order to be created
            PreparedStatement preparedStatement
                    = connection.prepareStatement(query);
            // loop trough the list of params in order to set them in the query
            // first ? will be replaced with the 1st param from params list
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            // execute the query
            return new ResultSetHandler(preparedStatement.executeQuery());
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean executeStatement(String query) {
        try {
            return statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        return false;
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
