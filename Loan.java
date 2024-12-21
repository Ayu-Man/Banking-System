public class Loan {
    private double loanAmount;
    private double interestRate;
    private int loanTerm; // in years

    public Loan() {
        this.loanAmount = 0;
        this.interestRate = 0.05; // default interest rate of 5%
        this.loanTerm = 1; // default loan term of 1 year
    }

    public void takeLoan(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Loan amount must be greater than zero.");
        }
        this.loanAmount += amount; // Add to existing loan amount
    }

    public void payLoan(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Payment amount must be positive.");
        }
        if (amount > loanAmount) {
            throw new BankingException("Payment exceeds remaining loan amount.");
        }
        loanAmount -= amount; // Deduct from loan amount
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double calculateInterest() {
        return loanAmount * interestRate * loanTerm; // Simple interest calculation
    }
}