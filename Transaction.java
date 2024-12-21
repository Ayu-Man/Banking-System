import java.io.Serializable;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization
    private String type;
    private double amount;
    private int accountId;

    public Transaction(String type, double amount, int accountId) {
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", accountId=" + accountId +
                '}';
    }
}