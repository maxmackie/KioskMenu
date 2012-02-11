import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** A custom class to hold buttons and change their properties */
@SuppressWarnings("unused")
public class Button implements ActionListener {
	private MenuPanel parent;
	private JButton button;
	private String action;
	private String password;
	
	/** Some default values **/
	public static final String DEFAULT_NAME = "Button";
	public static final String DEFAULT_FONT = "";
	public static final int DEFAULT_POSX = 0;
	public static final int DEFAULT_POSY = 0;
	public static final int DEFAULT_WIDTH = 200;
	public static final int DEFAULT_HEIGHT = 30;
	public static final int DEFAULT_FONTCOLOR = 0;
	public static final int DEFAULT_FONTSIZE = 20;
	
	
	/** This construction accepts a String for the text to be displayed on the button, as well as the
	* desired password and the desired action. It also accepts 4 integers that give the button it's
	* position and dimensions. Finally, a MenuPanel object is passed which indicated on which frame
	* the button should be drawn to. */
	public Button (String text, 
			       String password, 
			       int posx, 
			       int posy, 
			       int pos, 
			       int width, 
			       int height, 
			       String action, 
			       MenuPanel parent, 
			       String font, 
			       int fontSize, 
			       int fontColor, 
			       int backgroundColor) {
		
		this.button = new JButton();
		this.parent = parent;
		this.password = password; 
		this.action = action;
		
		/** Load the defaults if needed */
		if (text.equals("")) { text = Button.DEFAULT_NAME; }
		if (font.equals("")) { font = Button.DEFAULT_FONT; }
		if (posx == 0) { posx = Button.DEFAULT_POSX; }
		if (posy == 0) { posy = Button.DEFAULT_POSY; }
		if (width == 0) { width = Button.DEFAULT_WIDTH; }
		if (height == 0) { height = Button.DEFAULT_HEIGHT; }
		if (fontColor == 000000000) { fontColor = Button.DEFAULT_FONTCOLOR; }
		if (fontSize <= 0) { fontSize = Button.DEFAULT_FONTSIZE; }
		
		/** Set the button text */
		this.button.setText(text); 
		
		/** Find the colour */
		if (backgroundColor != 0) { 
			Color tmpBgColor = new Color(backgroundColor);
			button.setBackground(tmpBgColor);
		}
		
		/** Set bounds */
		this.button.setBounds(posx, posy, width, height);
		
		/** Find the font */
		Font newfont = new Font(font, Font.PLAIN, fontSize);
		Color tmpFontColor = new Color(fontColor);
		button.setForeground(tmpFontColor);
		button.setFont(newfont);
		
		/** Verify if layout is specified */
		if (this.parent.getCustomLayout() != null) {
			this.parent.getCustomLayout().placeButton(button, pos);
		}
		
		/** Paint button onto pane and validate */
		parent.add("button", this.button);
		button.validate();
		button.repaint();
		this.parent.validate();
	}
	
	
	/** Initiates the action to the button in question. The reason this isn't in the constructor is 
	* because the Button object itself needs to be passed as an argument. You can't pass something 
	* that doesn't yet exist (as is the case in a constructor, the object is in the process of being 
	* created but has not been completed). */
	public void init() {
		this.button.addActionListener(this);
	}
	
	
	/** This method handles the possible actions that pressing a button will produce. There are three
	* possibilities of actions. This method is also responsible for handling the validation of a 
	* password (if it was defined). */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.button) {
			
			// Check password
			if (password != "") {
				String tmpPASS = new PasswordBox().prompt(this.parent.getMaster());
				if (!tmpPASS.equals(this.password)) {
					JOptionPane.showMessageDialog(this.parent.getMaster(), "Password Invalid", "Error", JOptionPane.ERROR_MESSAGE);
					LogWriter log = new LogWriter("Invalid password attempt on button \"" + this.button.getText() + "\"");
					return;
				}
			}
			
			// Handle a shell() action
			if (action.startsWith("shell(\"")) {
				String tmpSHELL = action.substring(7, action.length() - 2);
				try {
					Process p = Runtime.getRuntime().exec(tmpSHELL);
				} catch (IOException e1) {
					LogWriter log = new LogWriter("Cannot execute action \"" + this.action + "\"" + " on button \"" + this.button.getText() + "\"");
					System.exit(1);
				}
			}
			
			// Handle a frame() action
			else if (action.startsWith("frame(\"")) {
				String tmpFRAME = action.substring(7, action.length() - 2);
				MenuPanel target = ConfigReader.getFrame(tmpFRAME);
				
				if (target == null) {
					LogWriter log = new LogWriter("Frame \"" + tmpFRAME + "\" doesn't exist");
				}
				
				CardLayout c = (CardLayout) (this.parent.getParent().getLayout());
				c.show(this.parent.getParent(), target.getName());
			}
			
			else if (action.equals("exit()")) {
				System.exit(1);
			}
			
			else if (action.equals("")) {
				return;
			}
			
			else {
				LogWriter log = new LogWriter("Action declaration of button \"" + this.button.getText() + "\" is invalid"); 
				System.exit(1);
			}	
		}
    }
}

