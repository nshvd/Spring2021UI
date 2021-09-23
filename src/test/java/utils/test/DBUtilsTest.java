package utils.test;

import org.junit.Assert;
import org.junit.Test;
import pojo.AccountStanding;
import pojo.UserAccount;
import utils.DB.DBUtils;
import utils.DB.DBUtilsV2;

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

    public final String queryToGetAccountStanding =
            "SELECT * FROM account_standing;";

    public final String queryToGetCustomerAccounts =
            "SELECT T1.account_number AS accountNumber," +
                    " T1.current_balance AS currentBalance," +
                    " T3.username," +
                    " T4.first_name AS firstName," +
                    " T4.last_name AS lastName\n" +
                    "FROM account T1\n" +
                    "LEFT JOIN users T3\n" +
                    "ON T1.owner_id = T3.id\n" +
                    "LEFT JOIN user_profile T4\n" +
                    "ON profile_id = T4.id";

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
        List<Map<String, Object>> table = DBUtilsV2
                .query(queryToGetUsersByGenderAndLocality,
                        "M", "Chicago").getTable();
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

    @Test
    public void beanTest() {
        List<AccountStanding> accountStandings =
                // query gets us a ResultSet
                DBUtilsV2.query(queryToGetAccountStanding)
                        //getBeans converts our resultSet into
                        // a list of beans
                        .getBeans(AccountStanding.class);

        System.out.println(accountStandings);
        for (AccountStanding accountStanding : accountStandings)
            System.out.println(accountStanding);
    }

    @Test
    public void testCustAcc() {
        List<UserAccount> userAccounts =
                DBUtilsV2.query(queryToGetCustomerAccounts)
                        .getBeans(UserAccount.class);

        System.out.println(userAccounts);
        System.out.println();
        for (UserAccount userAccount : userAccounts) {
            System.out.println(userAccount);
        }
    }

}
