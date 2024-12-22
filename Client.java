import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization
    private String name;
    private String address;
    private String phone;
    private Account account;

    public Client(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}