package com.revature.logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import com.revature.beans.*;
import com.revature.dao.*;
import com.revature.util.ConnectionUtil;

public class BootUp {
	
	private static int MENU_OPTIONS = 3;
	private static int BANK_MENU_OPTIONS = 4;
	
	public boolean bankAccountMenu(BankAccount b) {
		
		boolean exitCondition = false;
		Integer userChoice = -1;
		// display menu for bank account options 
		while(!exitCondition) {
			System.out.println("\nCurrent Balance\t" + b.getCurrentBalance()
					+ "\n1) Withdraw"
					+ "\n2) Deposit"
					+ "\n3) View Transactions"
					+ "\n4) Return to Account Menu"
					+ "\n0) Exit Revature Bank");
			userChoice = getInput(BANK_MENU_OPTIONS);
		
			switch(userChoice) {
				case 0:
					System.out.println("Thank you for using Revature Bank!");
					return true;
				case 1:
					System.out.println("Please enter how much you would like to withdraw");
					double withdraw = getNumInput();
					double updatedBalance = b.getCurrentBalance() - withdraw;
					if (updatedBalance < 0) {
						System.out.println("Oh nooo baby what is you doing");
						break;
					}else {
						Transaction temp = new Transaction(b.getId(), b.getCurrentBalance(), updatedBalance, withdraw, "W");
						TransactionDAO withdrawDAO = new TransactionDAOImpl();
						withdrawDAO.withdraw(temp);
						if(withdrawDAO.saveTransaction(temp)) {
							b.setCurrentBalance(updatedBalance);
							BankAccountDAO updateBalance = new BankAccountDAOImpl();
							updateBalance.updateBalance(b.getId(), updatedBalance);
							System.out.println("Successful withdrawl! Please take your money.");
						}
					}
						
					break;
				case 2: 
					System.out.println("Please enter how much you would like to deposit");
					double deposit = getNumInput();
					updatedBalance = b.getCurrentBalance() + deposit;
					Transaction temp = new Transaction(b.getId(), b.getCurrentBalance(), updatedBalance, deposit, "D");
					TransactionDAO depositDAO = new TransactionDAOImpl();
					depositDAO.deposit(temp);
					if(depositDAO.saveTransaction(temp)) {
						b.setCurrentBalance(updatedBalance);
						System.out.println("Successful Deposit!");
						BankAccountDAO updateBalance = new BankAccountDAOImpl();
						updateBalance.updateBalance(b.getId(), b.getCurrentBalance());
					}
						
					break;
				case 3:
					ArrayList<Transaction>allTransactions = new ArrayList<Transaction>();
					TransactionDAO tDAO = new TransactionDAOImpl();
					allTransactions = tDAO.accountTransactionHistory(b.getId());
					System.out.println( "\nid \t\tdate \t\tpreviousBalance \tvalue \t\tnewBalance \ttype");
					for(Transaction currTransaction : allTransactions) {
						System.out.println(currTransaction);
					}
					break;
				case 4:
					return false;
					
			}
		}
		
		
		return false;
	}
	public void loginMenu(UserAccount u) {
		
		
		// get useraccount primary key id
		int userID = u.getId();
		boolean exitCondition = false;
		int userChoice = -1;
		int bankAccountChoice = -1;
		
		BankAccountDAOImpl baDAO = new BankAccountDAOImpl();
		ArrayList<BankAccount> userBankAccounts = baDAO.getAllBankAccountsForUser(userID);
		
		while(!exitCondition) {
			System.out.println("\n1) View and Access Exisitng Bank Accounts."
					+ "\n2) Create New Bank Account"
					+ "\n0) Exit");
			userChoice = getInput(MENU_OPTIONS);
			switch(userChoice) {
				case 0:
					exitCondition = true;
					System.out.println("Thank you for using Revature Bank!");
					break;
				case 1:
					int count = 1;
					
					if(userBankAccounts.isEmpty()) {
						System.out.println("No bank accounts exist\n");
						break;
					}else {
						System.out.println("\nSelection\tBank id\t\tBalance");
						for(BankAccount currAccount : userBankAccounts) {
							System.out.println(count++ +")\t\t" +currAccount);
						}
						System.out.println("\nPLEASE CHOOSE A BANK ACCOUNT TO ACCESS");
						bankAccountChoice = getInput(userBankAccounts.size() + 1);
						
						if(bankAccountMenu(userBankAccounts.get(bankAccountChoice -1)))
							exitCondition = true;
					}
					break;
				case 2: 
					BankAccount newAcc = new BankAccount(userID);
					baDAO.saveBankAccount(newAcc);
					userBankAccounts = baDAO.getAllBankAccountsForUser(userID);
					System.out.println("Bank Account successfully created!");
					break;
				
			}
		}
	}
	
	
	public UserAccount init() {
		
		boolean exit = false;
		int input = 0;
		String username = "";
		String password = "";
		boolean isLoggedIn = false;
		while(!exit && !isLoggedIn) {
			
			System.out.println("\nWelcome to revature bank");
			System.out.println("Enter numberical option on menu corresponding to your choice.");
			System.out.println("1) Login\n2) Register\n0) Exit");
		
			input = getInput(MENU_OPTIONS);
			switch(input) {
				case 0:
					exit = true;
					System.out.println("Thank you for using Revature Bank!");
					break;
				case 1:
					System.out.println("Please enter your username");
					username = getStringInput("username");
					System.out.println("Please enter your password");
					password = getStringInput("password");
					
					UserAccount login = new UserAccount(username, password);
					UserAccountDAOImpl loginDAO = new UserAccountDAOImpl();
					
					System.out.println("Checking Account Validity...");
					if(loginDAO.isExistingUser(login)){
						isLoggedIn = true;
						System.out.println("Logging in...");
						
						return login;
					}
					else {
						System.out.println("Incorrect username/password. Returning to main menu...");
					}
					break;
				case 2: 
					//register
					
					System.out.println("Please enter a username (up to 15 characters)");
					username = getStringInput("username");
					System.out.println("Please enter your password (up to 15 characters)");
					password = getStringInput("password");
						
					UserAccount register = new UserAccount(username, password);
					UserAccountDAOImpl registerDAO = new UserAccountDAOImpl();
					
					System.out.println("Checking Account Validity...");
					if(registerDAO.saveUserAccount(register)) {
						System.out.println("Thank you for registering with Revature Bank!\nLogging in...");
						registerDAO.isExistingUser(register);
						isLoggedIn = true;
						return register;
					}
					
					break;
				
			}
			
		}
		return null;
	}
	
	
	public boolean updateUserMenu(int userAccID) {
		
		UserAccountDAO ua = new UserAccountDAOImpl();
		UserAccount uaUpdate = new UserAccount();
		uaUpdate = ua.getUserAccountById(userAccID);
		UserAccountDAO updateDAO = new UserAccountDAOImpl();
		boolean exit = false;
		int input = 0;
		while(!exit) {
			
			System.out.println("\nUpdating User Account Menu");
			System.out.println("Enter numberical option on menu corresponding to your choice.");
			System.out.println("1) Change username"
					+ "\n2) Change Password"
					+ "\n3) Make Super User"
					+ "\n4) Return to Super User Menu"
					+ "\n0) Exit");
		
			input = getInput(5);
			switch(input) {
				case 0:
					exit = true;
					System.out.println("Thank you for using Revature Bank!");
					break;
				case 1:
					System.out.println("Please enter a username (up to 15 characters)");
					String previousUserName = uaUpdate.getUsername();
					String newUserName = getStringInput("username");
					if(updateDAO.updateUsername(previousUserName, newUserName)) {
						System.out.println("Updated username to: " + newUserName);
						uaUpdate.setUsername(newUserName);
					}
					break;
				case 2:
					System.out.println("Please enter a password (up to 15 characters)");
					String previousPassword = uaUpdate.getPassword();
					String newPassword = getStringInput("password");
					if(updateDAO.updatePassword(uaUpdate.getUsername(), previousPassword, newPassword)) {
						System.out.println("Updated password to: " + newPassword);
						uaUpdate.setPassword(newPassword);
					}
					break;
				case 3:
					if(updateDAO.makeSuperUser(uaUpdate.getId())) {
						System.out.println("Updated user with user account id: " + uaUpdate.getId() + " to a super user");
					}
					break;
				case 4:
					return true;
				
			}
		
		}
		
		return false;
	}
	
	public void SuperUserMenu() {
		
		boolean exit = false;
		int input = 0;
		while(!exit) {
			
			System.out.println("\nWelcome to revature bank");
			System.out.println("Enter numberical option on menu corresponding to your choice.");
			System.out.println("1) View all Existing Users"
					+ "\n2) Update User Account"
					+ "\n3) Create User Account"
					+ "\n4) Delete User Account"
					+ "\n0) Exit");
		
			input = getInput(5);
			switch(input) {
				case 0:
					exit = true;
					System.out.println("Thank you for using Revature Bank!");
					break;
				case 1:
					UserAccountDAO user = new UserAccountDAOImpl();
					ArrayList<UserAccount> allUsers = new ArrayList<UserAccount>();
					allUsers = (ArrayList<UserAccount>) user.getUsers();
					
					int count = 1;
					if(allUsers.isEmpty()) {
						System.out.println("No existing users currently exist.");
					}
					else {
						
						for(UserAccount u : allUsers) {
							System.out.println(count++ +") " + u);
						}
					}
					break;
				case 2:
					System.out.println("Enter the User ID of the user account you would like to update: ");
					int userIDUpdate = getInput(99999);
					if(updateUserMenu(userIDUpdate)) {
						
					}
					break;
				case 3: 
					System.out.println("Please enter a username (up to 15 characters)");
					String username = getStringInput("username");
					System.out.println("Please enter your password (up to 15 characters)");
					String password = getStringInput("password");
						
					UserAccount register = new UserAccount(username, password);
					UserAccountDAOImpl registerDAO = new UserAccountDAOImpl();
					
					System.out.println("Checking Account Validity...");
					if(registerDAO.saveUserAccount(register)) {
						System.out.println("New user created.");
						registerDAO.isExistingUser(register);
					}
					break;
				case 4:
					System.out.println("Enter the User ID of the user account you would like to delete: ");
					int userID = getInput(99999);
					UserAccountDAO ua = new UserAccountDAOImpl();
					ua.deleteUser(userID);
					break;
			}
		
		}
		
	}
	
	
	public int getInput(int maxInput) {
		
		String input = "";
		boolean inputStatus = false;
		int inputCheck = 0;
		
		while(!inputStatus) {
			try {
				Scanner scanner = new Scanner(System.in);
				if (scanner.hasNextLine()) {
					input = scanner.nextLine();
				}
				else {
					System.out.println("bug is here");
					return 0;
				}
				
				inputCheck = Integer.parseInt(input);
				if(inputCheck < maxInput && inputCheck >= 0 ) {
					inputStatus = true;
				}
				else {
					System.out.println("Please enter a numerical input");
				}
			}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("Please enter a valid numerical input");
			}
			
		}
		return inputCheck;
	}
	
	public String getStringInput(String type) {
	
		String input = "";
		boolean inputStatus = false;
		
		while(!inputStatus) {
			try {
				Scanner scanner = new Scanner(System.in);
				input = scanner.nextLine();
				
				if(input.length() > 0 &&  input.length() < 16 ) {
					inputStatus = true;
				}
				else {
					System.out.println("Please enter a valid " + type);
				}
				
			}catch(Exception e) {
				System.out.println("Please enter a valid " + type);
				//e.printStackTrace();
			}

			
			
		}
		return input;
	}
	
	public double getNumInput() {
		String input = "";
		boolean inputStatus = false;
		double inputCheck = 0.0;
		
		while(!inputStatus) {
			try {
				Scanner scanner = new Scanner(System.in);
				if (scanner.hasNextLine()) {
				input = scanner.nextLine();
				}
				else {
					System.out.println("bug is here");
					return 0;
				}
				
				inputCheck = Double.parseDouble(input);
				if(inputCheck >= 0 ) {
					inputStatus = true;
				}
				else {
					System.out.println("Please enter a valid numerical input");
				}
			}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("Please enter a valid numerical input");
			}
			
		}
		return inputCheck;
	}
}
