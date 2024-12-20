/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Scanner;


public class BankingSystem
{

    static ArrayList<Client> allClients;
    static ArrayList<BankAccount> allAccounts;
//    static ArrayList<SavingsBankAccount> allSavingAccount;
    static Scanner input;
    private static int size;

    private static int getSize(){
        return allAccounts.size();
    }
    static void showAllAccounts(){
        for(int i =0;i< getSize();i++){
            allAccounts.get(i).view();
            System.out.println("*******************************");
        }
    }

    static BankAccount search(int accountId){
        for(int i=0;i<getSize();i++){
            if(accountId==allAccounts.get(i).getAccountId()){
                return allAccounts.get(i);
            }
        }
        return null;
    }

    static int searchById(int accountId){
        for(int i=0;i<getSize();i++){
            if(accountId==allAccounts.get(i).getAccountId()){
                return i;
            }
        }
        return -1;
    }
//    static int searchSavingAcountById(int accountId){
//        for(int i=0;i<getSize();i++){
//            if(accountId==allSavingAccount.get(i).getAccountId()){
//                return i;
//            }
//        }
//        return -1;
//    }
    static void searchForAccount(int id){

        BankAccount acc = search(id);
        if(acc==null){
            System.err.println("account not found");
            return;
        }
        acc.view();
    }

    static void removeAccount(){
         System.out.println("enter account id");
        int id = input.nextInt();
//        BankAccount acc = search(id);
//        if(acc==null){
//            System.err.println("account not found");
//            return;
//        }
//        allAccounts.remove(acc);
//        allClients.remove(acc.getOwner());

        int index = searchById(id);
        if(index==-1){
            System.err.println("account not found");
            return;
        }
        /*
        Todo: search for memory leak and how to avoid it
        */
        allClients.remove(allAccounts.get(index).getOwner());
        allAccounts.remove(index);
        System.out.println("account removed successfully");
    }

    static void addAccount(){
        input.nextLine();
        System.out.println("enter client name");
        String name = input.nextLine();
        System.out.println("enter client address");
        String address = input.nextLine();
        System.out.println("enter client phone");
        String phone = input.nextLine();

        Client newClient = new Client(name, address, phone);
        System.out.println("please choose account type");
        System.out.println("1- basic bank account");
        System.out.println("2- savings bank account");
        int accType = input.nextInt();
        if(accType!=1&&accType!=2){
            System.err.println("invalid input");
            return;
        }
        BankAccount newAccount = null;
        System.out.println("enter account balance");
        double balance = input.nextDouble();
        if(accType==1){
            newAccount = new BankAccount(balance);
        }else if(accType==2){
            newAccount = new SavingsBankAccount(balance);
        }
        newAccount.setOwner(newClient);
        newClient.setAccount(newAccount);
        allAccounts.add(newAccount);
//        if(accType == 2)
//            allSavingAccount.add((SavingsBankAccount)newAccount);
        allClients.add(newClient);
        System.out.println("account created successfully");
    }

    static void deposit(int id){

        int index = searchById(id);
       if(index==-1){
           System.err.println("account not found");
           return;
       }
       System.out.print("enter amount to deposit: ");
       double amountOfMoney=input.nextDouble();
       if(allAccounts.get(index).deposit(amountOfMoney)){
           System.out.println("-----successfull operation-------");
       }
       balance(id);
   }

    static void balance(int id){
        BankAccount acc = search(id);
        if(acc==null){
            System.err.println("account not found");
            return;
        }
        System.out.print("your Balance : ");
        System.out.println(acc.getBalance());
    }


    static void withdraw(int id){

        int index = searchById(id);
        if(index==-1){
            System.err.println("account not found");
            return;
        }
        System.out.print("enter amount to withdraw: ");
        double amountOfMoney=input.nextDouble();
        if(allAccounts.get(index).withdraw(amountOfMoney)){
            System.out.println("-----successfull operation-------");
        }
            balance(id);
   }

    static void transfer(int ID){
       System.out.print("Enter transfer acount ID: ");
       int transferId = input.nextInt();
       int index1 = searchById(transferId);
       if(index1==-1){
           System.err.println("account not found");
           return;
       }
       int index = searchById(ID);
       if(index==-1){
           System.err.println("acount not found");
           return;
       }
       System.out.println("Enter Transfer Amount");
       double amountOfMoney   = input.nextInt();
       if(allAccounts.get(index).withdraw((amountOfMoney))&&allAccounts.get(index1).deposit((amountOfMoney))){
           System.out.println("completed! thanks for using our product");
       }
       balance(ID);
   }

    static void loan(BankAccount acc) {
//        if (!(acc instanceof SavingsBankAccount)) {
//            System.out.println("You can't take a loan with a Basic Account.");
//            return;
//        }

        SavingsBankAccount savingsAcc = (SavingsBankAccount) acc;

        // Ask for loan amount
        System.out.print("Enter loan amount: ");
        double loanAmount = input.nextDouble();

        // Validate loan amount
        if (loanAmount <= 0) {
            System.err.println("Loan amount must be greater than 0.");
            return;
        }

        // Get the interest rate and calculate the loan repayment details
        double interestRate = 0.05;  // Assume 5% interest rate
        int loanTermMonths = 12;  // Loan term in months
        double monthlyPayment = calculateMonthlyPayment(loanAmount, interestRate, loanTermMonths);

        System.out.println("Loan Amount: " + loanAmount);
        System.out.println("Interest Rate: " + (interestRate * 100) + "%");
        System.out.println("Loan Term: " + loanTermMonths + " months");
        System.out.println("Monthly Payment: " + monthlyPayment);

        // Add loan to the account
        savingsAcc.getLoan().takeLoan(loanAmount);

        // Update account balance after loan is added
        savingsAcc.deposit(loanAmount);
        System.out.println("Loan granted successfully!");

        // Provide repayment schedule
        System.out.println("Repayment Schedule:");
        for (int month = 1; month <= loanTermMonths; month++) {
            double totalAmountDue = monthlyPayment * month;
            System.out.printf("Month %d: Total Amount Due: %.2f\n", month, totalAmountDue);
        }
    }

    static void payLoan(BankAccount acc) {
//        if (!(acc instanceof SavingsBankAccount)) {
//            System.out.println("Only Savings Account holders can pay loans.");
//            return;
//        }

        SavingsBankAccount savingsAcc = (SavingsBankAccount) acc;
        Loan loan = savingsAcc.getLoan();

        // Check if there's an outstanding loan
        if (loan.getLoanAmount() <= 0) {
            System.out.println("No outstanding loan to pay.");
            return;
        }

        // Display loan details
        System.out.println("Outstanding Loan Amount: " + loan.getLoanAmount());
        System.out.print("Enter payment amount: ");
        double paymentAmount = input.nextDouble();

        // Validate payment amount
        if (paymentAmount <= 0) {
            System.err.println("Payment amount must be greater than 0.");
            return;
        }

        if (paymentAmount > savingsAcc.getBalance()) {
            System.err.println("Insufficient funds in your account to make this payment.");
            return;
        }

        // Deduct payment from account balance and loan amount
        savingsAcc.withdraw(paymentAmount);
        loan.payLoan(paymentAmount);

        System.out.println("Payment successful!");
        System.out.println("Remaining Loan Amount: " + loan.getLoanAmount());

        // Check if the loan is fully paid
        if (loan.getLoanAmount() <= 0) {
            System.out.println("Congratulations! You have fully repaid your loan.");
        }
    }

    // Helper method to calculate the monthly payment using the formula for loan amortization
    private static double calculateMonthlyPayment(double loanAmount, double interestRate, int loanTermMonths) {
        double monthlyRate = interestRate / 12;
        return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -loanTermMonths));
    }


    static void listUnpaidLoans() {
        System.out.println("Listing Unpaid Loans...");
        boolean found = false;
        for (BankAccount account : allAccounts) {
            if (account instanceof SavingsBankAccount) {
                SavingsBankAccount sba = (SavingsBankAccount) account;
                Loan loan = new Loan();
                if (loan.getLoanAmount() > 0) {
                    System.out.println("Account ID: " + account.getAccountId() + ", Loan Amount: " + loan.getLoanAmount());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No unpaid loans found.");
        }
    }

    static void user(){
        int id;

        while (true) {
            String pwd;

            System.out.print("Enter your account ID( 0 - back to main menu ): ");
            id = input.nextInt();
            if(id == 0) break;

            System.out.print("Enter your password: ");
            input.nextLine();
            pwd = input.nextLine();

            if(search(id)==null && !search(id).getPassword().equals(pwd)){
                System.out.println("Authentication failed, try again");
                System.out.println(search(id).getPassword());
                continue;
            }
            System.out.println("Authnicated Successfully.");
            break;
        }

        var userAccount = search(id);

        if(search(id) instanceof SavingsBankAccount){
            userAccount = (SavingsBankAccount) search(id);
        }else{
            userAccount = search(id);
        }


        while (true) {

            boolean back = false;

            System.out.println();
            System.out.println("1. Deposit");
            System.out.println("2. Transfer");
            System.out.println("3. Withdraw");
            System.out.println("4. Balance");
            System.out.println("5. Get Account Information");
            System.out.println("6. Loan");
            System.out.println("7. Pay Loan");
            System.out.println("0. back");

            System.out.println();
            System.out.print("Enter your choice: ");
            int option = input.nextInt();

            switch (option) {
                case 1:
                    deposit(id);
                    break;
                case 2:
                    transfer(id);
                    break;
                case 3:
                    withdraw(id);
                    break;
                case 4:
                    balance(id);
                    break;
                case 5:
                    searchForAccount(id);
                    break;
                case 6:

                    if(!(userAccount instanceof SavingsBankAccount)){
                        System.out.println("You can't take loan with Basic Account.");
                        return;
                    }
                    loan(userAccount);
                    break;
                case 7:
                    if(!(userAccount instanceof SavingsBankAccount)){
                        System.out.println("Only Savings Account holders can pay loans.");
                        return;
                    }
                    payLoan(userAccount);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }
            if(back) break;
        }

    }

static void administer() {
    final String adminPass = "1234";


    while (true) {
        input.nextLine();
        System.out.print("Enter admin password: ");
        String pass = input.nextLine();
        if (!adminPass.equals(pass)) {
            System.out.println("Invalid password, try again!!");
            continue;
        }
        break;
    }


    while (true) {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Add Account");
        System.out.println("2. List All Accounts");
        System.out.println("3. Search Account by ID");
        System.out.println("4. Remove Account");
        System.out.println("5. List Unpaid Loans");
        System.out.println("0. Exit Admin Mode");
        System.out.print("Enter your choice: ");
        int option = input.nextInt();

        switch (option) {
            case 1:
                addAccount();
                break;
            case 2:
                showAllAccounts();
                break;
            case 3:
                System.out.print("Enter account ID to search: ");
                int id = input.nextInt();
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
    public static void main(String[] args)
    {
        allAccounts = new ArrayList<BankAccount>();
        allClients = new ArrayList<Client>();
        input = new Scanner(System.in);
        addTestData();

        while (true)
        {
            System.out.println("1. User");
            System.out.println("2. Administer");
            System.out.println();
            System.out.print("Enter your choice: ");
            int option = input.nextInt();

            switch (option) {
                case 1:
                    user();
                    break;

                case 2:
                    administer();
                    break;

                default:
                    System.out.println("Invalid choice, please try again!!!");
                    break;
            }
        }
    }

    static void addTestData(){
        for(int i=0;i<20;i++){

            BankAccount ba = new BankAccount(i*50000, "1234");
            Client c = new Client("name "+i,"address "+i, "123456");

            c.setAccount(ba);
            ba.setOwner(c);

            allAccounts.add(ba);
            allClients.add(c);
        }
    }
}
