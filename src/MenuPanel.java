import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/** This class represents a "frame" in the GUI sense. **/
@SuppressWarnings({ "unused", "serial" })
public class MenuPanel extends JPanel {
	
	// These arrays hold the components that as defined for this MenuPanel
	private Button[] buttons = new Button[5];
	private int buttonsSize = 0;
	private Label[] labels = new Label[5];
	private int labelsSize = 0;
	private Image[] images = new Image[5];
	private int imagesSize = 0;
	private JPanel parent;
	private JFrame master;
	
	// Holds the desired layout for the frame.
	private CustomLayout layout;
	 
	// Prevent Alt-Tabbing
	TabStopper stopper;
	
	/** This constructor simply accepts a string which represents the title name of the
	* frame. It also makes it so that users cannot manually close the window or Alt+Tab
	* out of it. It maximizes the frame, sets the bounds and sets the content layout 
	* to null (this allows for absolute positioning). */
	public MenuPanel(String title, JPanel parent, JFrame master) {
		
		this.setLayout(null);
		
		// Don't start a TabStopper if running Linux
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("windows")) {
			stopper = new TabStopper(this);
		}
		
		this.parent = parent;
		this.master = master;
		setName(title);
        this.setBackground(Color.white);
	}
	
	
	/** Sets the desired layout option. This is a shaky way of determining a class
	* name based on a user defined string. This might be changed in the future. */
	public void setComponentLayout(String layout) {
		try { this.layout = (CustomLayout) Class.forName(layout).newInstance(); } 
		catch (Exception e){
			LogWriter log = new LogWriter("Layout specified in config file is non-valid or does not exist");
			System.exit(1);
		}
	}
	
	
	/** Used to add special configuration to the MenuPanel itself (as opposed to just a
	* component on the frame). Also responsible for setting the layout. */
	public void configure(String layoutName, String preventClose, int bgColor) {
		if (!layoutName.equals("")) {
			setComponentLayout(layoutName);
		}
		
		if (bgColor != 0) {
			setBackground(new Color(bgColor));
		}
	}
	
	
	/** Handles the creation and initialization of buttons and their actions. 
	* Increments the button array */
	public void addButton(String text, String password, int posx, int posy, int pos, int width, int height, String action, String font, int fontSize, int fontColor, int bgColor) {
		if (buttonsSize == buttons.length) { incrementArray("buttons"); }
		buttons[buttonsSize] = new Button(text, password, posx, posy, pos, width, height, action, this, font, fontSize, fontColor, bgColor);
		buttons[buttonsSize].init();
		buttonsSize++;
	}
	
	
	/** Handles the creation of labels. Increments the label array */
	public void addLabel(String text, String dateboolean, String font, int posx, int posy, int pos, int fontSize, int fontColor, int bgColor) {
		if (labelsSize == labels.length) { incrementArray("labels"); }
		labels[labelsSize] = new Label(this, text, dateboolean, font, posx, posy, pos, fontSize, fontColor, bgColor);
		labelsSize++;
	}
	
	
	/** Handles the creation of images. Increments the image array */
	public void addImage(String file, int posx, int posy, int pos) {
		if (imagesSize == images.length) { incrementArray("images"); }
		images[imagesSize] = new Image(this, file, posx, posy, pos);
		imagesSize++;
	}
	
	
	/** Get this frame's layout */
	public CustomLayout getCustomLayout() {
		return this.layout;
	}
	
	/** Increments the size of buttons, labels or images array. This is a good memory
	* saving technique and allows for an infinite amount of components. */
	private void incrementArray(String which) {
		if (which.equals("buttons")) {
			Button[] tmp = new Button[buttonsSize*2];
			for (int i = 0; i < buttonsSize; i++) {
				tmp[i] = buttons[i];
			}
			this.buttons = tmp;
		}
		
		else if (which.equals("labels")) {
			Label[] tmp = new Label[labelsSize*2];
			for (int i = 0; i < labelsSize; i++) {
				tmp[i] = labels[i];
			}
			this.labels = tmp;
		}
		
		else if (which.equals("images")) {
			Image[] tmp = new Image[imagesSize*2];
			for (int i = 0; i < imagesSize; i++) {
				tmp[i] = images[i];
			}
			this.images = tmp;
		}
		
		else {
			LogWriter log = new LogWriter("Failed to increment component array. You should never see this, if you do please review source or file a bug.");
			System.exit(1);
		}
	}
	
	public JPanel getParent() {
		return this.parent;
	}
	
	public JFrame getMaster() {
		return this.master;
	}
}