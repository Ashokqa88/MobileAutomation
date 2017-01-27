package commonUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class Config {
    AndroidDriver<AndroidElement> driver; 

		String excelPath= "C:\\Users\\Ashok\\workspace\\Android_App_Info.xlsx";
        String MainSheet = "MainSheet";  //MS : MainSheet
  
        //Getting sheet from excel file
    public Sheet getSheet(String excelPath, String sheetName) throws IOException
	  {
		  File get_file=new File(excelPath);
	        FileInputStream fis = new FileInputStream(get_file);
	        Workbook  workbook = new XSSFWorkbook(fis);
	        Sheet sh=workbook.getSheet(sheetName);  
	        return sh;
	  }
    
      //Reading row number which is having 'run' status
    public int readMerchantRowNumber() throws IOException{
		 int MSrowLength = getSheetRowCount(MainSheet);
		// int colLength = getSheetColumnCount(sheetName);
		  int rowValue = 0;
		 
		  for(int i=0;i<=MSrowLength;i++)
		        {
		        	String EV = readExcelValue(MainSheet,i,1);
		        	if(EV.equals("run"))
		        	{//System.out.println(EV);
		        		rowValue=i;
		        	}
		        } return rowValue;
	}
      
    //Reading sheet name which is having 'T'(True) to execute  
    public String readSheetName() throws IOException{
    	String sheetname=null;
    	int MSrowLength = getSheetRowCount(MainSheet);
    	int MScolLength = getSheetColumnCount(MainSheet);
    	int row = readMerchantRowNumber();
    	for(int i=0;i<MScolLength;i++)
    	{
    		String EV = readExcelValue(MainSheet,row,i);
    		if(EV.equals("T")){
    			
    			sheetname = readExcelValue(MainSheet,0,i);
    		}
    		
    	}
		return sheetname;  	 	
    	
    	
    }
      
    //reading sheet row count by passing sheet name.
    public  int getSheetRowCount(String sheetName) throws IOException {
        int rowCount = 0;

        Iterator<Row> iterator = getSheet(excelPath,sheetName).iterator();
        Row nextRow = iterator.next();
        rowCount = getSheet(excelPath,sheetName).getLastRowNum();
        return rowCount;  
    }
    
    //reading sheet column count by passing sheet name.
    public  int getSheetColumnCount(String sheetName) throws IOException {
        int columnCount=0;

        Iterator<Row> iterator = getSheet(excelPath,sheetName).iterator();
        Row nextRow = iterator.next();
        columnCount = nextRow.getLastCellNum();
        return columnCount;  
    }
	
    // Reading CELL value from sheet by passing sheet name,row value and column value  	
    public String readExcelValue(String sheetName,int x,int y) throws IOException{     

    	 int rowLength = getSheetRowCount(sheetName);
		 int colLength = getSheetColumnCount(sheetName);
	        String[][] data = new String[rowLength+1][colLength];
	        int i,j;
	        String val = null;

	        for (i=0;i<=rowLength;i++){     //row
	             for (j=0; j<colLength; j++) //column
	             {  
	                	val = getSheet(excelPath,sheetName).getRow(i).getCell(j).getStringCellValue();
	                	data[i][j]=val;
	    	     }	                                           
	           }
	       String CellValue = data[x][y];
             
	         return CellValue;
	    }
	
	
    // Reading merchant name which is having 'run' status from main sheet
    public String readMerchant() throws IOException{
		 int MSrowLength = getSheetRowCount(MainSheet);
		// int colLength = getSheetColumnCount(sheetName);
		  String merchantName = null;
		 
		  for(int i=0;i<=MSrowLength;i++)
		        {
		        	String EV = readExcelValue(MainSheet,i,1);
		        	//System.out.println(EV);
		        	if(EV.equals("run"))
		        	{//System.out.println(EV);
		        		merchantName=readExcelValue(MainSheet,i,0);
		        	}
		        } return merchantName;
	}
	
	
    //Reading property file value by passing key
    public String pv(String propertyFileKey) throws IOException{     
		 int MSrowLength = getSheetRowCount(MainSheet);
        String propertyFileValue = "Please check property file key";
        for(int i=0;i<=MSrowLength;i++)
        {
        	String EV = readExcelValue(MainSheet,i,1);
        	if(EV.equals("run"))
        	{
        		InputStream is = null;
	            Properties prop = null;
	            try {
	                prop = new Properties();
	                is = new FileInputStream(new File(readExcelValue(MainSheet,i,2)));
	                prop.load(is);
	                propertyFileValue = prop.getProperty(propertyFileKey);
	                
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
        	}
        }
	 return propertyFileValue;
    }


	
    //App launching and it will return the driver which is having session id.
    //For app launch required information will read from property file i.e.,app package, app name ,activities list ..
    public AndroidDriver<AndroidElement> readDriver() throws IOException{
   	  File appDir = new File(pv("APKPath"));
      File app = new File(appDir, pv("APKName"));
      DesiredCapabilities cap = new DesiredCapabilities();
      cap.setCapability(MobileCapabilityType.DEVICE_NAME,pv("Device_Name"));            
      cap.setCapability(AndroidMobileCapabilityType.PLATFORM,pv("Platform_Name")); 
      cap.setCapability("appPackage", pv("appPackage"));
      cap.setCapability("appActivity",pv("appActivity"));
      cap.setCapability("appWaitActivity",pv("appWaitActivity"));       
      cap.setCapability("app", app.getAbsolutePath());
      driver =  new AndroidDriver<AndroidElement>(new URL(pv("appiumServerAddress")), cap);
         return driver;
     }
	
	
    //Taking screenshot for test case id and saving in folder name(folder name we can give from property file)
    public void takeScreenShot(String destDir,String TC_ID) {
		 
		  // Capture screenshot.
		  File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		  // Set date format to set It as screenshot file name.
		  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		  // Create folder under project with name "screenshots" provided to destDir.
		  new File(destDir).mkdirs();
		  // Set file name using current date time.
		  String destFile = TC_ID+" "+dateFormat.format(new Date()) + ".png";

		  try {
		   // Copy paste file at destination folder location
		   FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
		
	
    //Executing test cases which are available in all sheets which are marked 'T' in main sheet
    public void executeTestCases() throws IOException,InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		
		//Note:- MOrowLength :: ModulesSheet RowLength; MOrow :: ModuleSheetRow ; M :: Merchants list starting column
    	int MScolLength = getSheetColumnCount(MainSheet);
    	int row = readMerchantRowNumber();

		
		for(int i=3;i<MScolLength;i++)
    	{
    		String EV = readExcelValue(MainSheet,row,i);

    		if(EV.equals("T")){
    			
    			String Modulename = readExcelValue(MainSheet,0,i);
    			int MOrowLength=getSheetRowCount(Modulename);
    			int MOcolLength=getSheetColumnCount(Modulename);
		
		    	 for(int M=0; M<MOcolLength; M++){  
		    		 
		    		 if(readExcelValue(Modulename,0,M).equals(readMerchant())){
		    	        	System.out.println("Executing Module\t"+Modulename);

		    			 for(int MOrow=0;MOrow<=MOrowLength;MOrow++){
		    				 
		    				 if(readExcelValue(Modulename,MOrow,M).equals("run")){
		    					 
		    					    String TCval =  readExcelValue(Modulename,MOrow,0);
                                    String TCClassName = readExcelValue(Modulename,MOrow,1);
                                    System.out.println(TCval +"\t"+TCClassName);
                                  try{
                                    Class<?> cls = Class.forName(TCClassName);
                                    Object clsInstance = (Object) cls.newInstance();
                					Method testMethod = cls.getDeclaredMethod(TCval);
                                    testMethod.invoke(clsInstance);
                                    System.out.println("Executed"+"\t"+TCClassName);
                                  }
                                 catch (java.lang.NoSuchMethodException e) 
                                  {System.out.println(TCval+"\tTestcase/Method not found in\t"+Modulename+"\texcel sheet"); }
                                 catch (java.lang.ClassNotFoundException e)
                                  {System.out.println(TCClassName+"\t Class name of Testcase/Method not found in\t"+Modulename+"\texcel sheet");}
                                 catch (java.lang.reflect.InvocationTargetException e)
                                  {System.out.println("Previous session not cleared");}

		    				 
		    			 
		    					 }
		    				 }
		    			 }
		    	 }
    		}
		   
		
		
    	}
	}
	
	
}
