package utils.test;

import utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtilsTest {
    public static void main(String[] args) throws SQLException {
        DBUtils.openConnection();
        ResultSet rs = DBUtils.query("SELECT * FROM account;");
        System.out.println(DBUtils.getColumnNames(rs));
    }
}
