package database;

import java.sql.*;

public class ConnectionPractise {
    public static void main(String[] args) throws SQLException {
        String jdbcLink =
                "jdbc:mysql://3.131.35.165:3306/digitalbank?user=dbank&password=SDET123$$";
        String query = "SELECT * FROM users;";
        Connection connection = DriverManager.getConnection(jdbcLink); // 1) Establish connection
        // Statement is executing queries.
        Statement statement = connection.createStatement(); // 2) Create a statement
        ResultSet resultSet = statement.executeQuery(query); // 3) Send a query.
        while (resultSet.next()) {
            // Processing Result Set
            System.out.println(resultSet.getString("username"));
            System.out.println(resultSet.getInt("id"));
        }
        resultSet.close();
        statement.close();
        connection.close(); // 4) closing the connection
    }
}
