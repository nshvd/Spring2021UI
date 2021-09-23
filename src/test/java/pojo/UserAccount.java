package pojo;

import utils.DB.DBUtilsV2;

import java.util.List;

public class UserAccount {
    private String accountNumber;
    private double currentBalance;
    private double interestRate;
    private String username;
    private String firstName;
    private String lastName;

    private static final String GET_ALL_QUERY =
            "SELECT T1.account_number AS accountNumber," +
                    " T1.current_balance AS currentBalance," +
                    " T1.interest_rate AS interestRate," +
                    " T3.username," +
                    " T4.first_name AS firstName," +
                    " T4.last_name AS lastName\n" +
                    "FROM account T1\n" +
                    "LEFT JOIN users T3\n" +
                    "ON T1.owner_id = T3.id\n" +
                    "LEFT JOIN user_profile T4\n" +
                    "ON profile_id = T4.id";

    private static final String GET_BY_ACCOUNT_NUMBER =
            "SELECT T1.account_number AS accountNumber," +
                    " T1.current_balance AS currentBalance," +
                    " T1.interest_rate AS interestRate," +
                    " T3.username," +
                    " T4.first_name AS firstName," +
                    " T4.last_name AS lastName\n" +
                    "FROM account T1\n" +
                    "LEFT JOIN users T3\n" +
                    "ON T1.owner_id = T3.id\n" +
                    "LEFT JOIN user_profile T4\n" +
                    "ON profile_id = T4.id\n" +
                    "WHERE account_number = ?;";


    public UserAccount(){}

    public UserAccount(String accountNumber, double currentBalance, String username, String firstName, String lastName) {
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static List<UserAccount> getFromDB() {
        return DBUtilsV2
                .query(GET_ALL_QUERY)
                .getBeans(UserAccount.class);
    }

    public static UserAccount getFromDB(String accountNumber) {
        List<UserAccount> userAccounts = DBUtilsV2
                .query(GET_BY_ACCOUNT_NUMBER, accountNumber)
                .getBeans(UserAccount.class);
        return userAccounts.isEmpty() ? null : userAccounts.get(0);
    }

    public double calculateInterest() {
        return currentBalance * interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", currentBalance=" + currentBalance +
                ", interestRate=" + interestRate +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
