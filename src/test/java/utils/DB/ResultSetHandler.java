package utils.DB;

import org.apache.commons.dbutils.BeanProcessor;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetHandler {
    private BeanProcessor beanProcessor;
    private ResultSet resultSet;

    public ResultSetHandler(ResultSet resultSet) {
        this.resultSet = resultSet;
        this.beanProcessor = new BeanProcessor();
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    // Method that will convert our result set into a list of beans
    // We need to provide a type of java class.
    // generecis
    // using method will be like getBeans(HomePage.class)
    // * we need to specify the class that we want use as a model
    public <T> List<T> getBeans(Class<T> tClass) {
        try {
            return beanProcessor.toBeanList(getResultSet(), tClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    // Method that converts result set into a list of maps
    public List<Map<String, Object>> getTable() {
        List<Map<String, Object>> table = new ArrayList<>();
        // get column names by using our custom method
        List<String> columnNames = getColumnNames(getResultSet());
        // loop through each row of the result set
        try {
            while (getResultSet().next()) {
                // for each row we will create a new map
                // key = column name
                // value = data from that column
                Map<String, Object> row = new HashMap<>();
                // in order to populate map we are looping through column names
                for (String columnName : columnNames) {
                    // insert each column name plus the value from that column in a row map
                    row.put(columnName, getResultSet().getObject(columnName));
                }
                // insert our map into our list
                table.add(row);
            }
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
        return table;
    }

    // Method will return a list of column names from a result set
    private List<String> getColumnNames(ResultSet rs) {
        // list where column names will be stored
        List<String> columns = new ArrayList<>();
        // get metadata from a result set
        ResultSetMetaData metaData;
        try {
            metaData = rs.getMetaData();
            // using column count, loop through column indexes
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // add every column name into our list
                columns.add(metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
        return columns;
    }
}
