package Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonFunctions {
	
	   public static String getProperty(String key) {
		   String baseDir = System.getProperty("user.dir");
		   String propfilepath = baseDir + "/src/test/java/TestData/testData.properties";
	        Properties prop = new Properties();
	        try {
	            FileInputStream input = new FileInputStream(propfilepath);
	            prop.load(input);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return prop.getProperty(key);
	    }
	  	
}
