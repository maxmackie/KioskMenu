import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/** This class is responsible for most of the IOExceptions and try/catch blocks in this
* application. It is used to both write errors to STDERR and to a log file in logs/error.log
* Currently, all errors are logged as SEVERE */
public class LogWriter {
	public LogWriter(String error) {	
		/** STDERR part */
		ConsoleHandler err = new ConsoleHandler();
		Logger log = Logger.getLogger("");
		LogRecord rec = new LogRecord(Level.SEVERE, error);
		err.publish(rec);
		log.addHandler(err);
		err.close();
		
		/** Log file part */
		Calendar cal = Calendar.getInstance();
		error = cal.getTime() + " " + error + "\r\n";
		FileWriter writer;
		try {
			writer = new FileWriter("logs/error.log", true);
			writer.write(error);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
