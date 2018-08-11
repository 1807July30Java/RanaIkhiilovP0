package com.revature.logic;

import java.util.Scanner;
import com.revature.beans.*;
import com.revature.dao.*;

public class BootUp {
	
//	public void loginMenu(UserAccount u) {
//		
//	}
//	
	
	public UserAccount init() {
		
		boolean exit = false;
		int input = 0;
		String username = "";
		String password = "";
		boolean isLoggedIn = false;
		while(!exit && !isLoggedIn) {
			
			System.out.println("Welcome to revature bank");
			System.out.println("Enter numberical option on menu corresponding to your choice.");
			System.out.println("1.Login\n2.Register\n0.Exit");
		
			input = getInput();
			
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
						System.out.println(login);
						
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
						isLoggedIn = true;
						return register;
					}
					
					break;
				
			}
			
		}
		return null;
	}
	
	
	public int getInput() {
		
		String input = "";
		boolean inputStatus = false;
		int inputCheck = 0;
		
		while(!inputStatus) {
			try {
				Scanner scanner = new Scanner(System.in);
				input = scanner.nextLine();
				
				if(input.length() ==1 ) {
					inputStatus = true;
					inputCheck = Integer.parseInt(input);
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
