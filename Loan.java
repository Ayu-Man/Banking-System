import java.io.Serializable;

public class Loan implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization
    private double loanAmount;
    private double interestRate;

    public Loan() {
        this.loanAmount = 0;
        this.interestRate = 0.05; // Example interest rate of 5%
    }

    public void takeLoan(double amount, double interest) {
        this.loanAmount += amount + interest; // Total loan is amount plus interest
    }

    public double calculateInterest(double amount, int months) {
        double totalInterest = (amount * interestRate) * months; // Simple interest calculation
        return totalInterest;
    }

    public double getRemainingCredit() {
        return loanAmount; // Return the total loan amount including interest
    }
}