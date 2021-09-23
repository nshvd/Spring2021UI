package pojo;

public class UserAccount {
    private String accountNumber;
    private double currentBalance;
    private String username;
    private String firstName;
    private String lastName;

    public UserAccount(){}

    public UserAccount(String accountNumber, double currentBalance, String username, String firstName, String lastName) {
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
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
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
