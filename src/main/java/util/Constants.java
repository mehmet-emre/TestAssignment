package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {
	
	/*
	 * constants
	 */
	public static String SERVER_ENDPOINT;
	
	/*
	 * error code descriptions
	 */
	public static String E000000; // successful
	public static String E000001; // invalid username
	public static String E000002; // not enough balance
	public static String E000003; // unknown error
	
	public static void loadProperties() throws IOException{
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream("config.properties");
			prop.load(input);
	 
			SERVER_ENDPOINT = prop.getProperty("SERVER_ENDPOINT");
			E000000 = prop.getProperty("E000000");
			E000001 = prop.getProperty("E000001");
			E000002 = prop.getProperty("E000002");
			E000003 = prop.getProperty("E000003");

	 
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
