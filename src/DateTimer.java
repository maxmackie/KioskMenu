import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.Timer;

/** This class is called when a label is supposed to display the date (and be updated).
* It creates a new thread thanks to Swing's Timer class and writes the current time,
* updating every second. */
public class DateTimer implements ActionListener {
	private int delay;
	private JLabel label;

	public DateTimer(int delay, JLabel label) {
		this.delay = delay;
		this.label = label;
		
		new Timer(this.delay, this).start();
		
		/** Without this we don't see the date until 1 "delay" after startup. */
		this.label.setText(Calendar.getInstance().getTime().toString());
	}
	
	
	/** Changes the text of the label every "delay" to show the current date and time */
	public void actionPerformed(ActionEvent e) {
		this.label.setText(Calendar.getInstance().getTime().toString());
	}
}
