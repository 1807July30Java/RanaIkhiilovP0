package com.revature.logic;

import java.util.ArrayList;
import java.util.Scanner;
import com.revature.beans.*;
import com.revature.dao.*;

public class BootUp {
	
	private static int MENU_OPTIONS = 3;
	private static int BANK_MENU_OPTIONS = 4;
	
	public boolean bankAccountMenu(BankAccount b) {
		
		boolean exitCondition = false;
		int userChoice = -1;
		// display menu for bank account options 
		while(!exitCondition) {
			System.out.println("Current Balance\t" + b.getCurrentBalance()
					+ "1) Withdraw"
					+ "\n2) Deposit"
					+ "\n3) View Transactions"
					+ "\n4) Return to Account Menu"
					+ "\n0) Exit Revature Bank");
			userChoice = getInput(BANK_MENU_OPTIONS);
			switch(userChoice) {
				case 0:
					return true;
				case 1:
					
					break;
				case 2: 
					
					break;
				
			}
		}
		
		// view transactions
		
		// withdraw
		
		// deposit
		
		//exit
		
		
		
		
		
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
			System.out.println("1) View and Access Exisitng Bank Accounts."
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
						System.out.println("No bank accounts Exist\n");
						break;
					}else {
						System.out.println("Selection\tBank id\t\tBalance");
						for(BankAccount currAccount : userBankAccounts) {
							System.out.println(count++ +")\t\t" +currAccount);
						}
						System.out.println("\nPLEASE CHOOSE A BANK ACCOUNT TO ACCESS");
						//bankAccountChoice = getInput(userBankAccounts.size());
						
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
			
			System.out.println("Welcome to revature bank");
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
	
	
	public int getInput(int maxInput) {
		
		String input = "";
		boolean inputStatus = false;
		int inputCheck = 0;
		
		while(!inputStatus) {
			try {
				Scanner scanner = new Scanner(System.in);
				input = scanner.nextLine();
				
				inputCheck = Integer.parseInt(input);
				if(inputCheck < maxInput && inputCheck >= 0 ) {
					inputStatus = true;
				}
				else {
					System.out.println("Please enter a valid numerical input");
				}
				
			}catch(Exception e) {
				System.out.println("Please enter a valid numerical input");
			}
//			System.out.println(input);
			
			
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
			}
//			System.out.println(input);
			
			
		}
		return input;
	}
}
