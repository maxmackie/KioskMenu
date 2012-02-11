import java.awt.Robot;
import java.awt.event.KeyEvent;

/** A background process to continuously "press" key releases and prevent
* users from Alt-Tabbing out of the application. This only works in a Windows
* environment. */
public class TabStopper implements Runnable {
	private boolean isWorking = false;
	private MenuPanel parent;
	
	/** Attaches the tab stopper to a parent, allowing only certain frames to be blocked */
	public TabStopper(MenuPanel parent) {
		this.parent = parent;
		new Thread(this, "TabStopper").start();
	}
	
	/** Start the process */
	public void run() {
		this.isWorking = true;
        Robot robot;
		try {
			robot = new Robot(); 
			while (isWorking) {
		        robot.keyRelease(KeyEvent.VK_ALT);
		        robot.keyRelease(KeyEvent.VK_TAB);
		        parent.requestFocus();
		        Thread.sleep(10); 
		    }
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Stop the process. This is not currently used. */
	public void stop() {
		this.isWorking = false;	
	}
	
	/** Return if the process is working or not */
	public boolean isWorking() {
		return this.isWorking;
	}
}
