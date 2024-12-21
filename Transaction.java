import java.time.LocalDateTime;

public class Transaction {
    private String type; // "Deposit", "Withdrawal", "Transfer"
    private double amount;
    private LocalDateTime dateTime;
    private int accountId;

    public Transaction(String type, double amount, int accountId) {
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("%s of %.2f on %s for Account ID: %d",
                type, amount, dateTime, accountId);
    }
}