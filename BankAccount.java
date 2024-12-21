public class BankAccount extends Account {
    public BankAccount(double balance, String password , int accountId) {
        super(balance, password , accountId);
    }

    @Override
    public void view() {
        System.out.println("Account Type: Basic Bank Account");
        System.out.println("Account ID: " + getAccountId());
        System.out.println("Balance: " + getBalance());
    }
}