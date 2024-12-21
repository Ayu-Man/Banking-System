import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private double balance;
    private String password;
    private Client owner;
    private static int idCounter = 1;
    private int accountId;
    private List<Transaction> transactions; // List to store transactions

    public Account(double balance, String password) {
        this.balance = balance;
        this.password = password;
        this.accountId = idCounter++;
        this.transactions = new ArrayList<>(); // Initialize transaction list
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