import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystem {
    static ArrayList<Client> allClients = new ArrayList<>();
    static ArrayList<Account> allAccounts = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        addTestData();
        while (true) {
            printSeparator();
            System.out.println("Welcome to the Banking System!");
            System.out.println("1. User");
            System.out.println("2. Administer");
            System.out.print("Enter your choice: ");
            int option = getIntInput();
            switch (option) {
                case 1:
                    user();
                    break;
                case 2:
                    administer();
                    break;
                default:
                    System.out.println("Invalid choice, please try again!");
                    break;
            }
        }
    }

    static void user() {
        int id;
        while (true) {
            printSeparator();
            System.out.print("Enter your account ID (0 - back to main menu): ");
            id = getIntInput();
            if (id == 0) break;

            Account account = search(id);
            if (account == null) {
                System.out.println("Account not found.");
                continue;
            }

            System.out.print("Enter your password: ");
            input.nextLine(); // consume the newline
            String pwd = input.nextLine();
            if (!account.getPassword().equals(pwd)) {
                System.out.println("Authentication failed, try again.");
                continue;
            }

            while (true) {
                printSeparator();
                System.out.println("User Menu");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Transfer");
                System.out.println("4. View Balance");
                System.out.println("5. View Transactions");
                System.out.println("6. Take Loan");
                System.out.println("7. Pay Loan");
                System.out.println("8. Change Password");
                System.out.println("0. Back");
                System.out.print("Enter your choice: ");
                int option = getIntInput();

                try {
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
                            if (account instanceof SavingsBankAccount) {
                                takeLoan((SavingsBankAccount) account);
                            } else {
                                System.out.println("Only Savings Account holders can take loans.");
                            }
                            break;
                        case 7:
                            if (account instanceof SavingsBankAccount) {
                                payLoan((SavingsBankAccount) account);
                            } else {
                                System.out.println("Only Savings Account holders can pay loans.");
                            }
                            break;
                        case 8:
                            changePassword(account);
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Invalid input!");
                            break;
                    }
                } catch (BankingException e) {
                    System.out.println(e.getMessage());
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
                System.out.println("Invalid password, try again.");
                continue;
            }
            break;
        }

        while (true) {
            printSeparator();
            System.out.println("Admin Menu:");
            System.out.println("1. Add Account");
            System.out.println("2. List All Accounts");
            System.out.println("3. Search Account by ID");
            System.out.println("4. Remove Account");
            System.out.println("5. List Unpaid Loans");
            System.out.println("0. Exit Admin Mode");
            System.out.print("Enter your choice: ");
            int option = getIntInput();

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
                case 0:
                    System.out.println("Exiting Admin Mode...");
                    return;
                default:
                    System.out.println("Invalid choice, try again!");
                    break;
            }
        }
    }

    static int getIntInput() {
        while (true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please enter an integer.");
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
            newAccount = new BankAccount(balance, password);
        } else {
            newAccount = new SavingsBankAccount(balance, password);
        }

        newAccount.setOwner(newClient);
        newClient.setAccount(newAccount);
        allAccounts.add(newAccount);
        allClients.add(newClient);
        System.out.println("Account created successfully.");
    }

    static double getDoubleInput() {
        while (true) {
            try {
                return input.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please enter a valid number.");
                input.next(); // clear the invalid input
            }
        }
    }

    static void changePassword(Account account) {
        System.out.print("Enter new password: ");
        input.nextLine(); // consume the newline
        String newPassword = input.nextLine();
        account.changePassword(newPassword);
        System.out.println("Password changed successfully.");
    }

    static void deposit(Account account) throws BankingException {
        System.out.print("Enter amount to deposit: ");
        double amount = getDoubleInput();
        account.deposit(amount);
        System.out.println("Deposit successful.");
    }

    static void withdraw(Account account) throws BankingException {
        System.out.print("Enter amount to withdraw: ");
        double amount = getDoubleInput();
        account.withdraw(amount);
        System.out.println("Withdrawal successful.");
    }

    static void transfer(Account account) {
        System.out.print("Enter transfer account ID: ");
        int transferId = getIntInput();
        Account targetAccount = search(transferId);
        if (targetAccount == null) {
            System.err.println("Target account not found.");
            return;
        }
        System.out.print("Enter transfer amount: ");
        double amount = getDoubleInput();
        try {
            if (account.withdraw(amount)) {
                targetAccount.deposit(amount);
                account.logTransaction("Transfer", amount); // Log transaction
                targetAccount.logTransaction("Transfer", amount); // Log transaction
                System.out.println("Transfer completed!");
            }
        } catch (BankingException e) {
            System.out.println(e.getMessage());
        }
    }

    static void takeLoan(SavingsBankAccount account) {
        System.out.print("Enter loan amount: ");
        double loanAmount = getDoubleInput();
        try {
            account.getLoan().takeLoan(loanAmount);
            account.deposit(loanAmount); // Update balance after loan
            System.out.println("Loan granted successfully!");
            System.out.println("Total Interest on Loan: " + account.getLoan().calculateInterest());
        } catch (BankingException e) {
            System.out.println(e.getMessage());
        }
    }

    static void payLoan(SavingsBankAccount account) {
        System.out.print("Enter payment amount: ");
        double paymentAmount = getDoubleInput();
        try {
            account.getLoan().payLoan(paymentAmount);
            account.withdraw(paymentAmount);
            System.out.println("Payment successful!");
        } catch (BankingException e) {
            System.out.println(e.getMessage());
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
        System.out.println("List of All Accounts");
        for (Account account : allAccounts) {
            account.view();
            System.out.println("*******************************");
        }
    }

    static void removeAccount() {
        System.out.print("Enter account ID: ");
        int id = getIntInput();
        Account account = search(id);
        if (account == null) {
            System.err.println("Account not found.");
            return;
        }
        allClients.remove(account.getOwner());
        allAccounts.remove(account);
        System.out.println("Account removed successfully.");
    }

    static void searchForAccount(int id) {
        Account acc = search(id);
        if (acc == null) {
            System.err.println("Account not found.");
            return;
        }
        acc.view();
    }

    static void listUnpaidLoans() {
        printSeparator();
        System.out.println("Listing Unpaid Loans...");
        boolean found = false;
        for (Account account : allAccounts) {
            if (account instanceof SavingsBankAccount) {
                SavingsBankAccount sba = (SavingsBankAccount) account;
                if (sba.getLoan().getLoanAmount() > 0) {
                    System.out.println("Account ID: " + account.getAccountId() + ", Unpaid Loan Amount: " + sba.getLoan().getLoanAmount());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No unpaid loans found.");
        }
    }

    static void viewTransactions(Account account) {
        System.out.println("Transaction History for Account ID: " + account.getAccountId());
        for (Transaction transaction : account.getTransactions()) {
            System.out.println(transaction);
        }
    }

    static void printSeparator() {
        System.out.println("====================================");
    }

    static void addTestData() {
        for (int i = 0; i < 20; i++) {
            Account ba = new BankAccount(i * 50000, "1234");
            Client c = new Client("Name " + i, "Address " + i, "123456");
            c.setAccount(ba);
            ba.setOwner(c);
            allAccounts.add(ba);
            allClients.add(c);
        }
    }
}