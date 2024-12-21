public class SavingsBankAccount extends Account {
    private Loan loan;

    public SavingsBankAccount(double balance, String password) {
        super(balance, password);
        this.loan = new Loan();
    }

    public Loan getLoan() {
        return loan;
    }

    @Override
    public void view() {
        System.out.println("Account Type: Savings Bank Account");
        System.out.println("Account ID: " + getAccountId());
        System.out.println("Balance: " + getBalance());
        System.out.println("Current Loan Amount: " + loan.getLoanAmount());
    }
}