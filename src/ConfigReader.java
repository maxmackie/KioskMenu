import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/** This class parses the default.txt configuration file and passes on
* component information to MenuPanel **/
@SuppressWarnings("unused")
public class ConfigReader {
	private static int panelsLength = 0;
	private static int lengthOfConfig;
	private static MenuPanel[] panels = new MenuPanel[1];
	

	/** The main method of the whole menu system. This will initialize the graphical device
	* of the machine and check if a config file exists. It then calls the findPanels() method
	* to look for panels, followed by the parse() method which will look for components and
	* their attributes. */
	public ConfigReader(String fileName) throws IOException {
		RandomAccessFile reader = new RandomAccessFile(fileName, "r");
		String line;
		int count = 0;
		while ((line = reader.readLine()) != null) {
			count++;
		}
		lengthOfConfig = count;
		findPanels(reader);
	}
	
	
	/** Goes through the config file to find any frame definitions (FRAME ") and reads the
	* name then creates a new frame and adds it to the array of panels. */
	private static void findPanels(RandomAccessFile reader) throws IOException {	
		reader.seek(0);
		
		JFrame frame = new JFrame("KioskMenu");
		JPanel mainPanel = new JPanel(new CardLayout());
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < lengthOfConfig; i++) {
			String currentLine = reader.readLine();
			if (currentLine == null) { break; }
			
			if (currentLine.startsWith("FRAME \"")) {
				
				/** Need more room in panels array **/
				if (panelsLength == panels.length) {
					incrementPanels();
				}
				
				String subString = currentLine.trim();
				subString = currentLine.substring(7, (currentLine.length() - 3));
				
				panels[i] = new MenuPanel(subString, mainPanel, frame);
				
				panelsLength++;
				parse(reader, panels[i]);
			}
		}

		if (panelsLength == 0) { 
			LogWriter log = new LogWriter("No panels found in config file. Configuration must contain at least one frame.");
			System.exit(1);
		}
		
		for (int i = 0; i < panelsLength; i++) {
			mainPanel.add(panels[i], panels[i].getName());
		}
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setUndecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setBounds(0,0,screenSize.width, screenSize.height);
	    
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		frame.setVisible(true);
		mainPanel.setVisible(true);
		mainPanel.validate();
	}
	
	
	/** Searches for a MenuPanel with the same title as the string specified and returns
	* it. This is useful when configuring a button to change menus and the user specifies
	* a menu title name in the config file. The only calling of this method is in the
	* Button's action method. */
	public static MenuPanel getFrame(String name) {
		for (int i = 0; i < panelsLength; i++) {
			if (panels[i].getName().equals(name)) {
				return panels[i];
			}
		}
		return null;
	}
	
	
	/** This method is used to increment the size of the panels array when it's maximum has
	* been reached. This is good for two reasons. Firstly, it allows the program to use less
	* memory to begin with (as the default size is only 2) and it allows the program to make
	* an unlimited number of panels without needing to recompile **/
	private static void incrementPanels() {
		MenuPanel[] tmp = new MenuPanel[panelsLength*2];
		for (int i = 0; i < panelsLength; i++) {
			tmp[i] = panels[i];
		}
		panels = tmp;
	}
	
	
	/** This method is called by the findPanels() method above and goes through the config file
	* to find declarations of components. It initializes the components and assigns their
	* parent based on where they are declared in the file. */
	private static void parse(RandomAccessFile reader, MenuPanel parent) throws IOException {
		boolean readComponents = true;
		String currentLine = "";
		currentLine = reader.readLine();
		currentLine = currentLine.trim();
		
		while (readComponents && currentLine != null) {
			
			// Stop looping
			if (currentLine.equals("")) {
				readComponents = false;
			}
			
			// Look for self{} definition
			else if (currentLine.equalsIgnoreCase("self {")) {
				String selfLayout = "";
				String selfPreventClose = "";
				int selfBgColor = 0;
				
				while (true) {
					currentLine = reader.readLine();
					currentLine = currentLine.trim();
					
					if (currentLine.toLowerCase().startsWith("layout: ")) {
						selfLayout = currentLine.substring(8);
					}
					else if (currentLine.toLowerCase().startsWith("bgcolor: ")) {
						selfBgColor = Integer.parseInt(currentLine.substring(9));
					}
					else if (currentLine.equals("}")) {
						break;
					}
					else {
						LogWriter log = new LogWriter("Error parsing frame configuration. Please review syntax.");
						System.exit(1);
					}
				}
				
				parent.configure(selfLayout, selfPreventClose, selfBgColor);
				currentLine = reader.readLine();
				currentLine = reader.readLine();
				
				if (currentLine == null) { break; }
				currentLine = currentLine.trim();
			}
			
			
			// Look for button{} definition
			else if (currentLine.equalsIgnoreCase("button {")) {
				String buttonText = "";
				String buttonPassword = ""; 
				String buttonAction = ""; 
				String buttonFont = "";
				int buttonPosX = 0;
				int buttonPosY = 0;
				int buttonWidth = 0;
				int buttonHeight = 0;
				int buttonFontSize = 0;
				int buttonFontColor = 0;
				int buttonBgColor = 0;
				int buttonLayoutPosition = -1;
				
				while (true) {
					currentLine = reader.readLine();
					currentLine = currentLine.trim();		
					
					if (currentLine.toLowerCase().startsWith("text: \"")) {
						buttonText = currentLine.substring(7, (currentLine.length() - 1));
					}
					else if (currentLine.toLowerCase().startsWith("password: \"")) {
						buttonPassword = currentLine.substring(11, (currentLine.length() - 1));
					}
					else if (currentLine.toLowerCase().startsWith("pos: ")) {
						buttonLayoutPosition = Integer.parseInt(currentLine.substring(5));
					}
					else if  (currentLine.toLowerCase().startsWith("posx: ")) {
						buttonPosX = Integer.parseInt(currentLine.substring(6));
					}
					else if  (currentLine.toLowerCase().startsWith("posy: ")) {
						buttonPosY = Integer.parseInt(currentLine.substring(6));
					}
					else if (currentLine.toLowerCase().startsWith("width: ")) {
						buttonWidth = Integer.parseInt(currentLine.substring(7));
					}
					else if (currentLine.toLowerCase().startsWith("height: ")) {
						buttonHeight = Integer.parseInt(currentLine.substring(8));
					}
					else if (currentLine.toLowerCase().startsWith("action: ")) {
						buttonAction = currentLine.substring(8);
					}
					else if (currentLine.toLowerCase().startsWith("font: ")) {
						buttonFont = currentLine.substring(6);
					}
					else if (currentLine.toLowerCase().startsWith("fontcolor: ")) {
						buttonFontColor = Integer.parseInt(currentLine.substring(11));
					}
					else if (currentLine.toLowerCase().startsWith("fontsize: ")) {
						buttonFontSize = Integer.parseInt(currentLine.substring(10));
					}
					else if (currentLine.toLowerCase().startsWith("bgcolor: ")) {
						buttonBgColor = Integer.parseInt(currentLine.substring(9));
					}
					else if (currentLine.equals("}")) {
						break;
					}
					else {
						LogWriter log = new LogWriter("Error parsing button configuration. Please review syntax.");
						System.exit(1);
					}
				}
				parent.addButton(buttonText, buttonPassword, buttonPosX, buttonPosY, buttonLayoutPosition, buttonWidth, buttonHeight, buttonAction, buttonFont, buttonFontSize, buttonFontColor, buttonBgColor);
				currentLine = reader.readLine();
				currentLine = reader.readLine();
				
				if (currentLine == null) { break; }
				currentLine = currentLine.trim();
			}
			
			
			// Look for label definition
			else if (currentLine.equalsIgnoreCase("label {")) {
				String labelText = "";
				String labelFont = "";
				String labelDateBoolean = "";
				int labelPosX = 0;
				int labelPosY = 0;
				int labelFontSize = 0;
				int labelFontColor = 0;
				int labelBgColor = 0;
				int labelLayoutPosition = -1;
				
				while (true) {
					currentLine = reader.readLine();
					currentLine = currentLine.trim();
					
					if (currentLine.toLowerCase().startsWith("text: \"")) {
						labelText = currentLine.substring(7, (currentLine.length() - 1));
					} 
					else if (currentLine.toLowerCase().startsWith("posx: ")) {
						labelPosX = Integer.parseInt(currentLine.substring(6));
					}
					else if (currentLine.toLowerCase().startsWith("posy: ")) {
						labelPosY = Integer.parseInt(currentLine.substring(6));
					}
					else if (currentLine.toLowerCase().startsWith("pos: ")) {
						labelLayoutPosition = Integer.parseInt(currentLine.substring(5));
					}
					else if (currentLine.toLowerCase().startsWith("font: ")) {
						labelFont = currentLine.substring(6);
					}
					else if (currentLine.toLowerCase().startsWith("fontcolor: ")) {
						labelFontColor = Integer.parseInt(currentLine.substring(11));
					}
					else if (currentLine.toLowerCase().startsWith("fontsize: ")) {
						labelFontSize = Integer.parseInt(currentLine.substring(10));
					}
					else if (currentLine.toLowerCase().startsWith("bgcolor: ")) {
						labelBgColor = Integer.parseInt(currentLine.substring(9));
					}
					else if (currentLine.toLowerCase().startsWith("date: ")) {
						labelDateBoolean = currentLine.substring(6);
					}
					else if (currentLine.equals("}")) {
						break;
					}
					else {
						LogWriter log = new LogWriter("Error parsing label configuration. Please review syntax.");
						System.exit(1);
					}
				}
				parent.addLabel(labelText, labelDateBoolean, labelFont, labelPosX, labelPosY, labelLayoutPosition, labelFontSize, labelFontColor, labelBgColor);
				currentLine = reader.readLine();
				currentLine = reader.readLine();
				
				if (currentLine == null) { break; }
				currentLine = currentLine.trim();
			}
			
			
			// Look for image definition
			else if (currentLine.equalsIgnoreCase("image {")) {
				String tmpFILE = "";
				int tmpPOSX = 0;
				int tmpPOSY = 0;
				int tmpPOS = -1;
				
				while (true) {
					currentLine = reader.readLine();
					currentLine = currentLine.trim();
					
					if (currentLine.toLowerCase().startsWith("file: \"")) {
						tmpFILE = currentLine.substring(7, (currentLine.length() - 1));
					} 
					else if (currentLine.toLowerCase().startsWith("posx: ")) {
						tmpPOSX = Integer.parseInt(currentLine.substring(6));
					}
					else if (currentLine.toLowerCase().startsWith("posy: ")) {
						tmpPOSY = Integer.parseInt(currentLine.substring(6));
					}
					else if (currentLine.toLowerCase().startsWith("pos: ")) {
						tmpPOS = Integer.parseInt(currentLine.substring(5));
					}
					else if (currentLine.equals("}")) {
						break;
					}
					else {
						LogWriter log = new LogWriter("Error parsing image configuration. Please review syntax.");
						System.exit(1);
					}
				}
				parent.addImage(tmpFILE, tmpPOSX, tmpPOSY, tmpPOS);
				currentLine = reader.readLine();
				currentLine = reader.readLine();
				
				if (currentLine == null) { break; }
				currentLine = currentLine.trim();
			}
		}
	}
}