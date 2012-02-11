import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/** A custom class to hold an image object and set it's properties **/
@SuppressWarnings("unused")
public class Image {
	private MenuPanel parent;
	private ImageIcon image;
	private JLabel holder;
	
	/** Class default values **/
	public static final int DEFAULT_POSX = 0;
	public static final int DEFAULT_POSY = 0;
	public static final String DEFAULT_MEDIA_PATH = "media/";
	
	/** Creates an image object based on the variables given from the parent's method.
	* Throws an IOException because there might be error logging and this might create an IO
	* error. In case of confusion with the JLabel, images must be placed in a JLabel
	* field before they can be placed on the contentPane. */
	public Image(MenuPanel parent, 
			     String file, 
			     int posx, 
			     int posy, 
			     int pos) {
		
		this.parent = parent;
		
		// Verify image file
		file = DEFAULT_MEDIA_PATH + file;
		File tmp = new File(file);
		if (!tmp.exists()) {
			LogWriter log = new LogWriter("Cannot find specified image at \"" + file);
			System.exit(1);
		}
		
		// Load defaults if needed
		if (posx == 0) { posx = Image.DEFAULT_POSX; }
		if (posy == 0) { posy = Image.DEFAULT_POSY; }
		
		// Open image file and insert into JLabel
		image = new ImageIcon(file);
		holder = new JLabel(image);
		holder.setBounds(posx, posy, image.getIconWidth(), image.getIconHeight());
		
		// Verify if layout is specified 
		if (this.parent.getCustomLayout() != null) {
			this.parent.getCustomLayout().placeImage(holder, pos);
		}
		
		// Paint image onto pane and validate
		parent.add("image", holder);
		holder.repaint();
		holder.setVisible(true);
	}
}