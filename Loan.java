public class Loan {

    private double loanAmount;

    public Loan() {
        loanAmount = 0;
    }

    public Loan(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }
    public void payLoan(double amount){
        loanAmount-=amount;
    }

    public void takeLoan(double amount){
        loanAmount+=amount;
    }

}