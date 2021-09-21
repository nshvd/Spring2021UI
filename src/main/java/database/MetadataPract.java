package database;

import java.sql.*;

public class MetadataPract {
    public static void main(String[] args) throws SQLException {
        String jdbcLink =
                "jdbc:mysql://3.131.35.165:3306/digitalbank?user=dbank&password=SDET123$$";
        Connection connection = DriverManager.getConnection(jdbcLink);
        Statement statement = connection.createStatement();
        // Executing query
        ResultSet resultSet = statement.executeQuery("SELECT * FROM account;");
        // Get metadata from a result set
        ResultSetMetaData metaData = resultSet.getMetaData();
        // use total column count in order to loop
        int columnCount = metaData.getColumnCount();
        // loop using index and retrieve column name
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metaData.getColumnName(i));
        }
    }
}
