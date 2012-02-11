import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;


/** For a nice intro to Layouts see the README file or the comments in ListLayout.java
* and CustomLayout.java. This layout places buttons on a grid and places labels and 
* images just like ListLayout. */
@SuppressWarnings("unused")
public class GridLayout implements CustomLayout {

	/** Inherited from CustomLayout. Sets the rules for placing the JButton on the pane. Throws
	* an IOException due to the possible error logging. */
	public void placeButton(JButton button, int position) {
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int x1 = (int) (screenWidth - 880)/2;
		int x2 = x1 + 475;
		int y;
		
		switch (position) {
		case -1:
			return;
		case 0:
			y = 175;
			button.setBounds(x1, y, 400, 100);
			return;
		case 1:
			y = 175;
			button.setBounds(x2, y, 400, 100);
			return;
		case 2:
			y = 285;
			button.setBounds(x1, y, 400, 100);
			return;
		case 3:
			y = 285;
			button.setBounds(x2, y, 400, 100);
			return;
		case 4:
			y = 395;
			button.setBounds(x1, y, 400, 100);
			return;
		case 5:
			y = 395;
			button.setBounds(x2, y, 400, 100);
			return;
		case 6:
			y = 505;
			button.setBounds(x1, y, 400, 100);
			return;
		case 7:
			y = 505;
			button.setBounds(x2, y, 400, 100);
			return;
		case 8:
			y = 615;
			button.setBounds(x1, y, 400, 100);
			return;
		case 9:
			y = 615;
			button.setBounds(x2, y, 400, 100);
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
