package utils.test;

import org.junit.Assert;
import org.junit.Test;
import utils.DB.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBUtilsTest {
    private final String queryToGetAccountByName =
            "SELECT current_balance, date_opened FROM account " +
                    "WHERE name = ?;";
    private final String queryToGetUsersByGenderAndLocality =
            "SELECT country, first_name, last_name, gender, locality " +
                    "FROM user_profile "
                    + "Where gender = ? " +
                    "AND locality = ?;";

    @Test
    public void queryTest() {
        DBUtils.openConnection();
        List<Map<String, Object>> table =
                DBUtils.query("SELECT * FROM account;");
        for (Map<String, Object> row : table) {
            System.out.println(row);
        }
    }

    @Test
    public void queryTest1() {
        DBUtils.openConnection();
        List<Map<String, Object>> table = DBUtils
                .query(queryToGetAccountByName, "DB Automation Account");
        System.out.println(table);

        Assert.assertEquals(table.size(), 1);
        Assert.assertEquals("25.50",
                String.valueOf(table.get(0).get("current_balance")));
        Assert.assertEquals(
                "2021-09-21 18:45:02.0",
                table.get(0).get("date_opened").toString());
    }

    @Test
    public void queryTest2() {
        DBUtils.openConnection();
        List<Map<String, Object>> table = DBUtils
                .query(queryToGetUsersByGenderAndLocality,
                        "M", "Chicago");
        table.forEach(System.out::println);
    }

    @Test
    public void queryTest3() {
        DBUtils.openConnection();
        List<Map<String, Object>> table =
                DBUtils.query(queryToGetUsersByGenderAndLocality,
                        "F", "Chicago");
        table.forEach(System.out::println);
    }
}
