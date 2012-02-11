import java.io.File;
import java.io.IOException;

/** This is where the main() method lives. This class performs some basic file checking to
* make sure the integrity of the application is solid. It then calls ConfigReader if everything
* is in good shape. **/
@SuppressWarnings("unused")
public class KioskMenu {
	public static final String CONFIG_FILE_NAME = "./config/default.txt";
	public static final String STARTUP_SCRIPT_WINDOWS = "./scripts/startup.bat";
	public static final String STARTUP_SCRIPT_LINUX	= "./scripts/startup.sh";
	
	public static void main(String[] args) { 
		Boolean valid = false;
		
		// In case something bad happens while validating
		try { valid = validate(); } 
		catch (IOException e) {
			e.printStackTrace(); // Log file is not written to in case it doesn't exist. See validate()
			System.exit(1);
		}
		
		// Exist if non-valid
		if (!valid) { System.exit(1); }
		
		// Continue if valid
		try { ConfigReader wrapper = new ConfigReader(CONFIG_FILE_NAME); } 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static boolean validate() throws IOException {
		File tmpFile;
		boolean result = true;
		
		// Test error.log file
		tmpFile = new File("./logs/error.log");
		if (!tmpFile.exists()) {
			tmpFile.getParentFile().mkdirs();
			tmpFile.createNewFile();
		}
		
		// Test config directory
		tmpFile = new File("./config/");
		if (!tmpFile.exists()) {
			LogWriter log = new LogWriter("Config directory does not exist. Please create config/ in working directory.");
			result = false;
		}
	
		// Test default config file
		tmpFile = new File("./config/default.txt");
		if (!tmpFile.exists()) {
			LogWriter log = new LogWriter("Config does not exist in config/ directory. Please use config/default.txt");
			result = false;
		}
		
		return result;
	}
}
