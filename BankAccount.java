public class BankAccount extends Account {
    private static final long serialVersionUID = 1L; // Added for serialization

    public BankAccount(double balance, String password, int accountId) {
        super(balance, password, accountId);
    }

    @Override
    public void view() {
        System.out.println("Account Type: Basic Bank Account");
//        System.out.println();
        System.out.println("Account ID: " + getAccountId());
        System.out.println("Balance: " + getBalance());
    }
}