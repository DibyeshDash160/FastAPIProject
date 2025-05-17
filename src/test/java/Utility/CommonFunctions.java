package Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonFunctions {
	public static Properties props = new Properties();
	public static String baseDir = System.getProperty("user.dir");
	
	
	   public static String getProperty(String key) {
		   String propfilepath = baseDir + "/src/test/java/TestData/testData.properties";
		   props = new Properties();
	        try {
	            FileInputStream input = new FileInputStream(propfilepath);
	            props.load(input);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return props.getProperty(key);
	    }


	   public static String EnvConfigManager(String key) throws IOException {
	        if (props.isEmpty()) {
	            String env = System.getProperty("env", "production"); 
	            String fileName = baseDir + "/config/config_" + env + ".properties";

	            try (FileInputStream input = new FileInputStream(fileName)) {
	                props.load(input);
	            } catch (IOException e) {
	                throw new IOException("Failed to load config file: " + fileName, e);
	            }
	        }
	        return props.getProperty(key);
	    }
	  	
}
