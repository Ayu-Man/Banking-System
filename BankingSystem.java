import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystem {
    static ArrayList<Client> allClients = new ArrayList<>();
    static ArrayList<Account> allAccounts = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static int accountIdCounter = 1; // Track the account ID counter

    // ANSI escape codes for colors
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";
    static final String ANSI_BG_YELLOW = "\u001B[43m";

    public static void main(String[] args) {
        printWelcomeMessage();
        loadData(); // Load data at startup
        while (true) {
            printSeparator();
//            System.out.println(ANSI_YELLOW + "Welcome to the Banking System!" + ANSI_RESET);
            System.out.println("1. User");
            System.out.println("2. Administer");
            System.out.print("Enter your choice (0 to exit): ");
            int option = getIntInput();
            if (option == 0) {
                System.out.println("Exiting the application. Goodbye!");
                return;
            }
            switch (option) {
                case 1:
                    user();
                    break;
                case 2:
                    administer();
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid choice, please try again!" + ANSI_RESET);
                    break;
            }
        }
    }

    static void printWelcomeMessage() {
        System.out.println(ANSI_BLUE +
                "\t\t\t\t\t\t\t\t*****************************************************" + ANSI_RESET);
        System.out.println(ANSI_CYAN +
                "\t\t\t\t\t\t\t\t*\t             WELCOME TO THE                 \t*" + ANSI_RESET);
        System.out.println(ANSI_CYAN +
                "\t\t\t\t\t\t\t\t*\t             BANKING SYSTEM                 \t*" + ANSI_RESET);
        System.out.println(ANSI_BLUE +
                "\t\t\t\t\t\t\t\t*****************************************************" + ANSI_RESET);
        System.out.println(ANSI_GREEN +
                "\t\t\t\t\t\t\t\t*   Manage your finances with ease and security!\t*" + ANSI_RESET);
        System.out.println(ANSI_GREEN +
                "\t\t\t\t\t\t\t\t*   Enjoy our 24/7 services at your fingertips! \t*" + ANSI_RESET);
        System.out.println(ANSI_BLUE +
                "\t\t\t\t\t\t\t\t*****************************************************" + ANSI_RESET);
    }

    static void user() {
        int id;
        while (true) {
            printSeparator();
            System.out.print("Enter your account ID (0 - back to main menu): ");
            id = getIntInput();
            if (id == 0) return;

            Account account = search(id);
            if (account == null) {
                System.out.println(ANSI_RED + "Account not found." + ANSI_RESET);
                continue;
            }

            System.out.print("Enter your password: ");
            input.nextLine(); // consume the newline
            String pwd = input.nextLine();
            if (!account.getPassword().equals(pwd)) {
                System.out.println(ANSI_RED + "Authentication failed, try again." + ANSI_RESET);
                continue;
            }

            // Display user information
            System.out.println(ANSI_GREEN + "Logged in successfully!" + ANSI_RESET);
            System.out.println("Name: " + account.getOwner().getName());
            System.out.println("Account ID: " + account.getAccountId());

            while (true) {
                printSeparator();
                System.out.println(ANSI_YELLOW + "User Menu" + ANSI_RESET);
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Transfer");
                System.out.println("4. View Balance");
                System.out.println("5. View Transactions");
                System.out.println("6. Change Password");
                System.out.println("7. Take Loan");
                System.out.println("8. View Remaining Credit");
                System.out.println("9. Pay Loan");
                System.out.println("0. Back to Main Menu");
                System.out.println("99. Exit Program"); // New option to exit the program
                System.out.print("Enter your choice: ");
                int option = getIntInput();

                try {
                    if (option == 0) return; // Back to main menu
                    if (option == 99) {
                        System.out.println("Exiting the program. Goodbye!");
                        System.exit(0); // Exit the program
                    }
                    switch (option) {
                        case 1:
                            deposit(account);
                            break;
                        case 2:
                            withdraw(account);
                            break;
                        case 3:
                            transfer(account);
                            break;
                        case 4:
                            System.out.println("Your Balance: " + account.getBalance());
                            break;
                        case 5:
                            viewTransactions(account);
                            break;
                        case 6:
                            changePassword(account);
                            break;
                        case 7:
                            takeLoan(account);
                            break;
                        case 8:
                            viewRemainingCredit(account);
                            break;
                        case 9:
                            payLoan(account);
                            break;
                        default:
                            System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
                }
            }
        }
    }
    static void administer() {
        final String adminPass = "1234";

        while (true) {
            System.out.print("Enter admin password: ");
            String pass = input.next();
            if (!adminPass.equals(pass)) {
                System.out.println(ANSI_RED + "Invalid password, try again." + ANSI_RESET);
                continue;
            }
            break;
        }

        while (true) {
            printSeparator();
            System.out.println(ANSI_YELLOW + "Admin Menu:" + ANSI_RESET);
            System.out.println("1. Add Account");
            System.out.println("2. List All Accounts");
            System.out.println("3. Search Account by ID");
            System.out.println("4. Remove Account");
            System.out.println("5. List Unpaid Loans");
            System.out.println("0. Back to Main Menu");
            System.out.println("99. Exit Program"); // New option to exit the program
            System.out.print("Enter your choice: ");
            int option = getIntInput();

            if (option == 0) return; // Exit admin mode
            if (option == 99) {
                System.out.println("Exiting the program. Goodbye!");
                System.exit(0); // Exit the program
            }

            switch (option) {
                case 1:
                    addAccount();
                    break;
                case 2:
                    showAllAccounts();
                    break;
                case 3:
                    System.out.print("Enter account ID to search: ");
                    int id = getIntInput();
                    searchForAccount(id);
                    break;
                case 4:
                    removeAccount();
                    break;
                case 5:
                    listUnpaidLoans();
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid choice, try again!" + ANSI_RESET);
                    break;
            }
        }
    }
    static void printSeparator() {
        System.out.println(ANSI_GREEN + "============================================================" + ANSI_RESET);
    }

    static int getIntInput() {
        while (true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Invalid input type. Please enter an integer." + ANSI_RESET);
                input.next(); // clear the invalid input
            }
        }
    }

    static void addAccount() {
        input.nextLine(); // consume the newline
        System.out.print("Enter client name: ");
        String name = input.nextLine();
        System.out.print("Enter client address: ");
        String address = input.nextLine();
        System.out.print("Enter client phone: ");
        String phone = input.nextLine();

        Client newClient = new Client(name, address, phone);
        System.out.println("Please choose account type:");
        System.out.println("1- Basic Bank Account");
        System.out.println("2- Savings Bank Account");
        int accType = getIntInput();
        if (accType != 1 && accType != 2) {
            System.err.println("Invalid input");
            return;
        }

        System.out.print("Enter account balance: ");
        double balance = getDoubleInput();
        System.out.print("Enter password for the account: ");
        input.nextLine(); // consume the newline
        String password = input.nextLine();

        Account newAccount;
        if (accType == 1) {
            newAccount = new BankAccount(balance, password, accountIdCounter++);
        } else {
            newAccount = new SavingsBankAccount(balance, password, accountIdCounter++);
        }

        // Set owner and account association
        newAccount.setOwner(newClient);
        newClient.setAccount(newAccount);

        allAccounts.add(newAccount);
        allClients.add(newClient);
        saveData(); // Save data after adding an account

        System.out.println(ANSI_GREEN + "Account created successfully!" + ANSI_RESET);
        displayAccountInfo(newAccount);
    }
    static double getDoubleInput() {
        while (true) {
            try {
                return input.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Invalid input type. Please enter a valid number." + ANSI_RESET);
                input.next(); // clear the invalid input
            }
        }
    }

    static void changePassword(Account account) {
        System.out.print("Enter new password: ");
        input.nextLine(); // consume the newline
        String newPassword = input.nextLine();
        account.changePassword(newPassword);
        saveData(); // Save data after changing password
        System.out.println(ANSI_GREEN + "Password changed successfully." + ANSI_RESET);
    }

    static void deposit(Account account) throws Exception {
        System.out.print("Enter amount to deposit: ");
        double amount = getDoubleInput();
        account.deposit(amount);
        saveData(); // Save data after deposit
        System.out.println(ANSI_GREEN + "Deposit successful. New Balance: " + account.getBalance() + ANSI_RESET);
    }

    static void withdraw(Account account) throws Exception {
        System.out.print("Enter amount to withdraw: ");
        double amount = getDoubleInput();
        account.withdraw(amount);
        saveData(); // Save data after withdrawal
        System.out.println(ANSI_GREEN + "Withdrawal successful. New Balance: " + account.getBalance() + ANSI_RESET);
    }

    static void transfer(Account account) {
        System.out.print("Enter transfer account ID: ");
        int transferId = getIntInput();
        Account targetAccount = search(transferId);
        if (targetAccount == null) {
            System.err.println(ANSI_RED + "Target account not found." + ANSI_RESET);
            return;
        }
        System.out.print("Enter transfer amount: ");
        double amount = getDoubleInput();
        try {
            if (account.withdraw(amount)) {
                targetAccount.deposit(amount);
                System.out.println(ANSI_GREEN + "Transfer completed! New Balance: " + account.getBalance() + ANSI_RESET);
            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
        }
    }

    static Account search(int accountId) {
        for (Account account : allAccounts) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }
        return null;
    }

    static void showAllAccounts() {
        printSeparator();
        System.out.println(ANSI_YELLOW + "List of All Accounts" + ANSI_RESET);
        for (Account account : allAccounts) {
            Client owner = account.getOwner();
            String ownerName = (owner != null) ? owner.getName() : "Unknown Owner";
            System.out.println("Account ID: " + account.getAccountId());
            System.out.println("Client Name: " + ownerName);
            System.out.println("Account Type: " + (account instanceof BankAccount ? "Basic Bank Account" : "Savings Bank Account"));
            System.out.println("Balance: " + account.getBalance());
            System.out.println(ANSI_BLUE + "*******************************" + ANSI_RESET);
        }
    }

    static void removeAccount() {
        System.out.print("Enter account ID: ");
        int id = getIntInput();
        Account account = search(id);
        if (account == null) {
            System.err.println(ANSI_RED + "Account not found." + ANSI_RESET);
            return;
        }

        Client owner = account.getOwner(); // Store owner information before removing
        allClients.remove(owner);
        allAccounts.remove(account);
        saveData(); // Save data after removing an account

        // Displaying removed account info
        System.out.println(ANSI_GREEN + "Account removed successfully!" + ANSI_RESET);
        System.out.println("Removed Account Info:");
        displayAccountInfo(account); // Pass the account to display
    }

    static void searchForAccount(int id) {
        Account acc = search(id);
        if (acc == null) {
            System.err.println(ANSI_RED + "Account not found." + ANSI_RESET);
            return;
        }
        Client owner = acc.getOwner();
        String ownerName = (owner != null) ? owner.getName() : "Unknown Owner";
        System.out.println("Account ID: " + acc.getAccountId());
        System.out.println("Client Name: " + ownerName);
        System.out.println("Account Type: " + (acc instanceof BankAccount ? "Basic Bank Account" : "Savings Bank Account"));
        System.out.println("Balance: " + acc.getBalance());
        System.out.println("*******************************");
    }
    static void viewTransactions(Account account) {
        System.out.println("Transaction History for Account ID: " + account.getAccountId());
        try (BufferedReader reader = new BufferedReader(new FileReader("all_transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int accId = Integer.parseInt(data[0]);
                String type = data[1];
                double amount = Double.parseDouble(data[2]);
                String timestamp = data[3]; // Get the timestamp
    
                if (accId == account.getAccountId()) {
                    System.out.println(type + ": " + amount + " on " + timestamp);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
    }
    static void takeLoan(Account account) {
        System.out.print("Enter loan amount: ");
        double amount = getDoubleInput();
        System.out.print("Enter loan term in months: ");
        int months = getIntInput();
        double interest = account.getLoan().calculateInterest(amount, months);
        account.getLoan().takeLoan(amount, interest);
        saveData(); // Save data after taking a loan
        System.out.println(ANSI_GREEN + "Loan taken successfully!" + ANSI_RESET);
        System.out.println("Total Loan Amount (including interest): " + (amount + interest));
    }
    static void payLoan(Account account) {
        Loan loan = account.getLoan();
        double remainingAmount = loan.getRemainingCredit();

        if (remainingAmount <= 0) {
            System.out.println(ANSI_RED + "You have no outstanding loans." + ANSI_RESET);
            return;
        }

        System.out.print("Enter amount to pay: ");
        double payment = getDoubleInput();

        if (payment <= 0) {
            System.out.println(ANSI_RED + "Payment must be positive." + ANSI_RESET);
            return;
        }

        if (payment > remainingAmount) {
            System.out.println(ANSI_RED + "Payment exceeds the remaining loan amount." + ANSI_RESET);
            return;
        }

        remainingAmount -= payment; // Reduce the remaining amount
        loan.takeLoan(-payment, 0); // Adjust loan amount

        System.out.println(ANSI_GREEN + "Payment successful!" + ANSI_RESET);
        System.out.println("Remaining loan amount: " + remainingAmount);
        saveData(); // Save data after payment
    }
    static void listUnpaidLoans() {
        printSeparator();
        System.out.println(ANSI_YELLOW + "Unpaid Loans:" + ANSI_RESET);
        boolean hasUnpaidLoans = false;

        for (Account account : allAccounts) {
            Loan loan = account.getLoan();
            if (loan.getRemainingCredit() > 0) {
                hasUnpaidLoans = true;
                Client owner = account.getOwner();
                System.out.println("Account ID: " + account.getAccountId() +
                        ", Client Name: " + owner.getName() +
                        ", Remaining Loan Amount: " + loan.getRemainingCredit());
            }
        }

        if (!hasUnpaidLoans) {
            System.out.println(ANSI_GREEN + "All loans are paid off." + ANSI_RESET);
        }
    }
    static void viewRemainingCredit(Account account) {
        double remainingCredit = account.getLoan().getRemainingCredit();
        System.out.println("Remaining Credit Amount: " + remainingCredit);
    }

    static void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("clients_accounts.txt"))) {
            for (Client client : allClients) {
                Account account = client.getAccount();
                writer.println(client.getName() + "," + client.getAddress() + "," + client.getPhone() + ","
                        + account.getAccountId() + "," + account.getBalance() + "," + account.getPassword());
            }
            System.out.println(ANSI_GREEN + "Data saved successfully." + ANSI_RESET);
        } catch (IOException e) {
            System.err.println(ANSI_RED + "Error saving data: " + e.getMessage() + ANSI_RESET);
        }
    }

    static void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("clients_accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                String address = data[1];
                String phone = data[2];
                int accountId = Integer.parseInt(data[3]);
                double balance = Double.parseDouble(data[4]);
                String password = data[5];

                Client client = new Client(name, address, phone);
                Account account = new BankAccount(balance, password, accountId); // Change this if you have SavingsBankAccount
                client.setAccount(account);
                account.setOwner(client); // Link account to client

                allClients.add(client);
                allAccounts.add(account);
                if (accountId >= accountIdCounter) {
                    accountIdCounter = accountId + 1; // Update accountIdCounter for next account
                }
            }
            System.out.println(ANSI_GREEN + "Data loaded successfully." + ANSI_RESET);
        } catch (FileNotFoundException e) {
            System.out.println(ANSI_YELLOW + "No data file found, starting fresh." + ANSI_RESET);
        } catch (IOException e) {
            System.err.println(ANSI_RED + "Error loading data: " + e.getMessage() + ANSI_RESET);
        }
    }
    // Method to display account information
    static void displayAccountInfo(Account account) {
        if (account == null) {
            System.out.println(ANSI_RED + "Account not found." + ANSI_RESET);
            return;
        }
        Client owner = account.getOwner();
        String ownerName = (owner != null) ? owner.getName() : "Unknown Owner"; // Handle null owner
        System.out.println("Account ID: " + account.getAccountId());
        System.out.println("Client Name: " + ownerName);
        System.out.println("Account Type: " + (account instanceof BankAccount ? "Basic Bank Account" : "Savings Bank Account"));
        System.out.println("Balance: " + account.getBalance());
        System.out.println("*******************************");
    }
}