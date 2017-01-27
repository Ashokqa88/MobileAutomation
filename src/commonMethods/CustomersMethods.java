package commonMethods;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import commonUtilities.Config;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class CustomersMethods  {
	Config cg = new Config();
    AndroidDriver<AndroidElement> driver; 
    
  //Getting the screenshot method for saving screenshot for test case idwith Pass and fail status(includes date and time also)
	public void screenshot(String status,String TC_ID) throws IOException{  //Status P : Pass  , F: Fail
		if(status.equals("P")){
		  cg.takeScreenShot(cg.pv("Screenshots_Folder_Pass"),TC_ID);

	}
		else if(status.equals("F")){
			 cg.takeScreenShot(cg.pv("Screenshots_Folder_Fail"),TC_ID);
		}
	}
	
	//Getting the driver from config class
	public AndroidDriver<AndroidElement> getDriver() throws IOException{  //Status New: New App Installation , E: Existing (Already installed app)  
		driver=cg.readDriver();
		return driver;
	}
    	
	//Common method for login with invalid credentials scenario
	public void login_invalid(String un, String pwd,String expectedValue,String TC_ID)  throws IOException{
		driver.findElementByXPath(cg.pv("uid_locator")).sendKeys(cg.pv(un));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		driver.findElementByXPath(cg.pv("pwd_locator")).sendKeys(cg.pv(pwd));
		driver.findElementByXPath(cg.pv("loginBtn_locator")).click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		if(driver.findElementByXPath(cg.pv("loginValidationMessage_locator")).getText().equals(cg.pv(expectedValue)))
		  {
			screenshot("P",TC_ID);
	   	  //System.out.println(TC_ID +"\t"+"Passed");
	   	  
	     }
		  else{
			  screenshot("F",TC_ID);
	   	  System.out.println(TC_ID +"\t"+"Failed");

		  }
	    driver.findElementByXPath(cg.pv("okBtnInValidationMessage_locator")).click();
	
  }
	
	//Common method for login with valid credentials scenario
	public void login_valid(String un, String pwd,String expectedValue,String TC_ID)  throws IOException{
		driver.findElementByXPath(cg.pv("uid_locator")).sendKeys(cg.pv(un));
		driver.findElementByXPath(cg.pv("pwd_locator")).sendKeys(cg.pv(pwd));
		driver.findElementByXPath(cg.pv("loginBtn_locator")).click();
		if(driver.findElementByXPath(cg.pv("loginSuccessMessage_locator")).getText().equals(cg.pv(expectedValue)))
		  {
			screenshot("P",TC_ID);
	   	  //System.out.println(TC_ID +"\t"+"Passed");
	     }
		  else{
			  screenshot("F",TC_ID);
	   	  System.out.println(TC_ID +"\t"+"Failed");
		  }
  }	

	
	
}
