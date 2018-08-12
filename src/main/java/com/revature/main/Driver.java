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
		
		if (user != null){
			boot.loginMenu(user);
		}
		
		//System.out.println(boot.getNumInput());
		
	}

}
