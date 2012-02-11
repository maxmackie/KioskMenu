import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/** Spawns a small popup requesting a password.
* Seeing as this was a "hack" (there is no direct support in Swing or Awt
* for something like this, I was unable to get the textbox to receive focus.
* It seems that this is impossible in it's current setup, so it will have to be
* redesigned to actually get the focus.*/
@SuppressWarnings("unused")
public class PasswordBox {
    public String prompt(JFrame master) {
        JPasswordField pass = new JPasswordField(10);
        int action = JOptionPane.showConfirmDialog(master, pass,"Enter Password",JOptionPane.OK_CANCEL_OPTION);
        return new String(pass.getPassword());
    }
}