public class SavingsBankAccount extends Account {
    private static final long serialVersionUID = 1L; // Added for serialization

    public SavingsBankAccount(double balance, String password, int accountId) {
        super(balance, password, accountId);
    }

    @Override
    public void view() {
        System.out.println("Account Type: Savings Bank Account");
        System.out.println("Account ID: " + getAccountId());
        System.out.println("Balance: " + getBalance());
    }
}