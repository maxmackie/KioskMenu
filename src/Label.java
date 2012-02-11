import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JLabel;

/** A custom class to hold labels and set properties */
@SuppressWarnings("unused")
public class Label {
	private MenuPanel parent;
	private JLabel label;
	
	/** Some default values */
	public static final String DEFAULT_NAME = "";
	public static final String DEFAULT_FONT = "";
	public static final int DEFAULT_POSX = 0;
	public static final int DEFAULT_POSY = 0;
	public static final int DEFAULT_FONTCOLOR = 0;
	public static final int DEFAULT_FONTSIZE = 20;
	
	/** Constructor for the Label object. This constructor is very basic. It accepts values relating
	* to its size and style and places it on the content pane of its parent. */
	public Label( final MenuPanel parent, 
			      String text, 
			      String labelBoolean, 
			      String font, 
			      int posx, 
			      int posy, 
			      int pos, 
			      int fontSize, 
			      int fontColor, 
			      int bgColor ) {
		
		this.label = new JLabel();
		this.parent = parent;
		
		/** Load defaults if needed*/
		if (text.equals("")) { text = Label.DEFAULT_NAME; }
		if (font.equals("")) { font = Label.DEFAULT_FONT; }
		if (posx == 0) { posx = Label.DEFAULT_POSX; }
		if (posy == 0) { posy = Label.DEFAULT_POSY; }
		if (fontSize == 0) { fontSize = Label.DEFAULT_FONTSIZE; }
		if (fontColor == 0) { fontColor = Label.DEFAULT_FONTCOLOR; }
		
		/** Find the font */
		Font newfont = new Font(font, Font.PLAIN, fontSize);
		Color tmpFontColor = new Color(fontColor);
		
		/** Find the colour */
		if (bgColor != 0) {
			Color tmpBgColor = new Color(bgColor);
			this.label.setBackground(tmpBgColor);
		}
		
		/** Do we want to read from STDOUT? 
		* EXPERIMENTAL **/
		if (text.startsWith("shell(\"")) {
			text = grabSTDOUT(text);
		}
		
		/** Define the label */
		int recommendedHeight = (fontSize + 15);
		this.label.setBounds(posx, posy, 1000, recommendedHeight);
		this.label.setText(text);
		this.label.setFont(newfont);
		this.label.setForeground(tmpFontColor);
		
		/** Detect if this label displays a date */
		if (!labelBoolean.equals("")) {
			new DateTimer(1000, this.label);
		}
		
		/** Detect if a specific layout was defined. If not, skip this step and use absolute */
		if (this.parent.getCustomLayout() != null) {
			this.parent.getCustomLayout().placeLabel(this.label, pos);
		}

		/** Add label to panel and validate */
		//parent.getContentPane().add(label);
		parent.add("label", this.label);
		label.repaint();
		label.setVisible(true);
		//parent.getContentPane().validate();
	}
	
	/** Grabs the output of a command from STDOUT. Experimental **/
	private String grabSTDOUT(String text) {
		String command = text.substring(7, text.length() - 1);
		String line;
		String STDOUT = "";
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader
            	(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				STDOUT += (line + '\n');
			}
			input.close();
		} catch (IOException e1) {
			LogWriter log = new LogWriter("Cannot grab STDOUT from \"" + command + "\"");
			System.exit(1);
		}
		return STDOUT;
	}
}
