package com.revature.main;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

import com.revature.beans.*;
import com.revature.dao.*;
import com.revature.logic.BootUp;


public class Driver {

	public static void main(String[] args) {
	
		BootUp boot = new BootUp();
		UserAccount user = new UserAccount();
		user = boot.init();
		
		if (user != null && user.getUserSuper() == 0){
			boot.loginMenu(user);
		}
		else if(user != null && user.getUserSuper() == 1) {
			boot.SuperUserMenu();
		}
//		
//		UserAccountDAO temp = new UserAccountDAOImpl();
//		temp.deleteUser(65);
		//System.out.println(boot.getNumInput());
		
	}

}
