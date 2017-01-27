package tc_Executions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.testng.annotations.Test;

import commonUtilities.Config;

/*Inputs required before executing
  1.Screenshots folder name change - Depends upon version change this   :: in property file
  2.Please check Main sheet properly - Please mark status as a 'run' for merchant
  */



public class Execution {
	
	 Config cg = new Config();

	
	//Executing all test cases against merchant
  @Test
  public void TestCaseExecution() throws InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, IOException {
	 
	
	  cg.executeTestCases();
	  
  }
  
  
}
