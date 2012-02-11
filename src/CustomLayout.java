import javax.swing.JButton;
import javax.swing.JLabel;

/** This interface must be implemented by all custom layout classes. It makes sure
* they can place all three types of components. */
public interface CustomLayout {
	public void placeButton(JButton button, int position);
	public void placeLabel(JLabel label, int position);
	public void placeImage(JLabel image, int position);
}
