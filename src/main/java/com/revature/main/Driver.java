package com.revature.main;

import com.revature.beans.*;
import com.revature.logic.BootUp;


public class Driver {

	public static void main(String[] args) {
	
		BootUp boot = new BootUp();
		UserAccount user = new UserAccount();
		user = boot.init();
		
//		if (user != null){
//			boot.loginMenu(user);
//		}
		
	}

}
