package commonFunctionalities;

import java.io.IOException;

import commonMethods.CustomersMethods;
import commonUtilities.Config;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Login {
//change
    //Writing test cases for only login functionality only
    AndroidDriver<AndroidElement> driver; 
	 CustomersMethods cs = new CustomersMethods();
	 Config cg = new Config();
	public void scr(String status,String TC_ID) throws IOException{  //Status P : Pass  , F: Fail
		cs.screenshot(status, TC_ID);
	}
	
	public void getDriver() throws IOException{  //Status N: New App Installation , E: Existing (Already installed app)  
		     
		driver=cs.getDriver();
		
	}

     //Writing the code for test cases with functionname_TFSid(tracking purpose) 
	//Saving screenshot with functionname_TFSid(tracking purpose)_Sub steps id 
	
	public void Login_23795() throws IOException {  
	  getDriver();  
	  // utilizing common method from customerMethods class (User id, pwd, expected value, saving screenshot with test case id(Pass/fail)
	  cs.login_invalid("uid_valid1","pwd_invalid1","pwd_invalid_message","Login_TC_23795_2");
	  cs.login_invalid("uid_invalid1","pwd_valid1", "uid_invalid_message","Login_TC_23795_3");
	  cs.login_invalid("uid_invalid1","pwd_invalid1", "uid_invalid_message","Login_TC_23795_4");
	  cs.login_invalid("uid_valid1","", "emptydata_validation_message","Login_TC_23795_5");
	  cs.login_invalid("","pwd_valid1","emptydata_validation_message","Login_TC_23795_6");
	  cs.login_invalid("uid_valid3","pwd_invalid2", "pwd_invalid_message","Login_TC_23795_7");
	  driver.quit();      
  }
  
	public void Login_31399() throws IOException {
	  getDriver();   // (User id, pwd, expected value, saving screenshot with test case id)
	  //cs.login_invalid("uid_valid1","pwd_invalid1","pwd_invalid_message","Login_TC_23796_2");
	  cs.login_invalid("uid_invalid1","pwd_valid1", "uid_invalid_message","Login_TC_23795_3");
	 // cs.login_invalid("uid_invalid1","pwd_invalid1", "uid_invalid_message","Login_TC_23795_4");
	  cs.login_invalid("uid_valid1","", "emptydata_validation_message1","Login_TC_23796_5");
	 // cs.login_invalid("","pwd_valid1","emptydata_validation_message","Login_TC_23795_6");
	 // cs.login_invalid("uid_valid3","pwd_invalid2", "pwd_invalid_message","Login_TC_23795_7");
	  driver.quit();      

  }

  
}
