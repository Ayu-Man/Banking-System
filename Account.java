import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization
    private double balance;
    private String password;
    private Client owner;
    private int accountId;
    private List<Transaction> transactions;

    public Account(double balance, String password, int accountId) {
        this.balance = balance;
        this.password = password;
        this.accountId = accountId;
        this.transactions = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountId() {
        return accountId;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void deposit(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Deposit amount must be positive.");
        }
        this.balance += amount;
        logTransaction("Deposit", amount); // Log transaction
    }

    public boolean withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdrawal amount must be positive.");
        }
        if (amount > this.balance) {
            throw new BankingException("Insufficient funds.");
        }
        this.balance -= amount;
        logTransaction("Withdrawal", amount); // Log transaction
        return true;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Client getOwner() {
        return owner;
    }

    public void logTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount, accountId));
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public abstract void view();
}