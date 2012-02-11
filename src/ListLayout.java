import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;

/** The following is an implementation of the interface layout. It allows for an easy way of placing
* components on frames. It is an alternative to using absolute positioning directly from the config
* file. This layout still places components using absolute positioning, it just makes it easier for the 
* user to adjust the settings. ListLayout places buttons in one column down the middle and has placeholders
* for a main header and a sub header, as well as two images in the bottom corners of the frame */
@SuppressWarnings("unused")
public class ListLayout implements CustomLayout {
	
	/** Inherited from CustomLayout. Sets the rules for placing the JButton on the pane. Throws
	* an IOException due to the possible error logging. */
	public void placeButton(JButton button, int position) {
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int x = (int) (screenWidth - 400)/2;
		int y;
		
		switch (position) {
		case -1:
			return;
		case 0:
			y = 200;
			button.setBounds(x, y, 400, 100);
			return;
		case 1:
			y = 310;
			button.setBounds(x, y, 400, 100);
			return;
		case 2:
			y = 420;
			button.setBounds(x, y, 400, 100);
			return;
		case 3:
			y = 530;
			button.setBounds(x, y, 400, 100);
			return;
		case 4:
			y = 640;
			button.setBounds(x, y, 400, 100);
			return;
		case 5:
			y = 750;
			button.setBounds(x, y, 400, 100);
			return;
		case 6:
			y = 860;
			button.setBounds(x, y, 400, 100);
			return;
		default:
			LogWriter log = new LogWriter("Invalid POS variable declared in config file for Button definition.");
			System.exit(1);
		}
	}

	
	/** Inherited from CustomLayout. Sets the rules relating to placing a JLabel on the pane.
	* Throw IOException due to the possible error logging. */
	public void placeLabel(JLabel label, int position) {
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		String fontName = label.getFont().getName();
		Font newFont;
		int horizontalPosition;
		
		switch (position) {
		case -1:
			return;
		case 0:
			newFont = new Font(fontName, Font.PLAIN, 76);
			label.setFont(newFont);
			label.setSize((int)label.getPreferredSize().getWidth(), 91);
			
			screenWidth = (screenWidth - label.getWidth())/2;
			horizontalPosition = (int)screenWidth;
			label.setLocation(horizontalPosition , 10);
			return;
		case 1:
			newFont = new Font(fontName, Font.PLAIN, 30);
			label.setFont(newFont);
			label.setSize((int)label.getPreferredSize().getWidth(), 45);
			
			screenWidth = (screenWidth - label.getWidth())/2;
			horizontalPosition = (int)screenWidth;
			label.setLocation(horizontalPosition, 100);
			return;
		default:
			LogWriter log = new LogWriter("Invalid POS variable declared in config file for Label definition.");
			System.exit(1);
		}
	}

	
	/** Inherited from CustomLayout. Sets the rules relating to placing a JLabel (representing an image)
	* on the pane. Uses the switch statement instead of if/else for code efficiency. Throw IOException
	* due to the possible error logging. */
	public void placeImage(JLabel image, int position) {
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x;
		int y;
		
		switch (position) {
		case -1:
			return;
		case 0:
			x = 10;
			y = (int) screenHeight - (image.getHeight() + 10);
			image.setLocation(x, y);
			return;
		case 1:
			x = (int)(screenWidth - (image.getWidth() + 10));
			y = (int) screenHeight - (10 + image.getHeight());
			image.setLocation(x, y);
			return;
		default:
			LogWriter log = new LogWriter("Invalid POS variable declared in config file for Image definition.");
			System.exit(1);
		}
	}
}
