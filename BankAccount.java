public class BankAccount extends Account {
    public BankAccount(double balance, String password) {
        super(balance, password);
    }

    @Override
    public void view() {
        System.out.println("Account Type: Basic Bank Account");
        System.out.println("Account ID: " + getAccountId());
        System.out.println("Balance: " + getBalance());
    }
}