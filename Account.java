import java.io.*;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization
    private double balance;
    private String password;
    private Client owner;
    private int accountId;
    private Loan loan;

    public Account(double balance, String password, int accountId) {
        this.balance = balance;
        this.password = password;
        this.accountId = accountId;
        this.loan = new Loan(); // Initialize a loan object
        loadTransactions(); // Load existing transactions
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

    public Loan getLoan() {
        return loan;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void deposit(double amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Deposit amount must be positive.");
        }
        this.balance += amount;
        logTransaction("Deposit", amount); // Log transaction
    }

    public boolean withdraw(double amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Withdrawal amount must be positive.");
        }
        if (amount > this.balance) {
            throw new Exception("Insufficient funds.");
        }
        this.balance -= amount;
        logTransaction("Withdrawal", amount); // Log transaction
        return true; // Indicate successful withdrawal
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Client getOwner() {
        return owner;
    }

    public void logTransaction(String type, double amount) {
        String transaction = new Transaction(type, amount, accountId).toString();
        saveTransactionToFile(transaction); // Save to a single file
    }

    private void saveTransactionToFile(String transaction) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("all_transactions.txt", true))) {
            writer.println(transaction);
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }

    private void loadTransactions() {
        try (BufferedReader reader = new BufferedReader(new FileReader("all_transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int accId = Integer.parseInt(data[0]);
                String type = data[1];
                double amount = Double.parseDouble(data[2]);

                // Only load transactions related to this account
                if (accId == accountId) {
                    // Handle loading logic if needed
                }
            }
        } catch (FileNotFoundException e) {
            // No previous transactions
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
    }

    public abstract void view();
}