import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type;
    private double amount;
    private int accountId;
    private LocalDateTime timestamp;

    public Transaction(String type, double amount, int accountId) {
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
        this.timestamp = LocalDateTime.now(); // Capture the current date and time
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s,%s,%.2f,%s", accountId, type, amount, timestamp.format(formatter));
    }

    // Getters for the fields if needed
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}