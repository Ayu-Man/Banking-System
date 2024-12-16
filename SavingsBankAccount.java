/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//  package Banking;


 public class SavingsBankAccount extends BankAccount
 {
     
     private double minimumBalance;
     private Loan loan;
     private double balance;
 
     public SavingsBankAccount(){
         super(1000);
         minimumBalance = 1000;
         loan = new Loan();
     }
 
     public SavingsBankAccount(double balance){
         super(balance);
         this.balance =  balance;
         minimumBalance=1000;
         loan = new Loan();
     }
 
     public Loan getLoan() {
         return loan;
     }
 
     public void setLoan(Loan loan) {
         this.loan = loan;
     }
 
     public double getMinimumBalance(){
         return minimumBalance;
     }
 
     public void setMinimumBalance(double minimumBalance){
         this.minimumBalance = minimumBalance;
     }
 
     @Override
     public boolean withdraw(double amountOfMoney)
     {
         if(balance-amountOfMoney>=minimumBalance){
             balance-=amountOfMoney;
             return true;
         }
         System.err.println("invalid amount");
         return false;
     }
 
     @Override
     public boolean deposit(double amountOfMoney)
     {
         if(amountOfMoney<100){
             System.err.println("invalid amount");
             return false;
         }
         balance+=amountOfMoney;
         return true;
     }
 }
 