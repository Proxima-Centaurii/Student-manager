package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**An input verifier that scans for email patterns in a given string. It has the ability to display custom popup warning messages.
 * */
public class EmailVerifier extends InputVerifier {

	final private Pattern regex = Pattern.compile("^[A-Z0-9!#$%&\\_'*+-/=?\\^\\-\\'{\\|}~]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
	private boolean display_popup_warning;
	private String message;
	
	public EmailVerifier() {
		super();
		
		display_popup_warning = false;
	}
	
	public EmailVerifier(String message) {
		super();
		
		display_popup_warning = true;
		this.message = message;
	}
	
	@Override
	public boolean verify(JComponent component) {
		JTextComponent text_component = (JTextComponent)component;
		String val = text_component.getText();
		
		if(!val.isEmpty()) {
			Matcher matcher = regex.matcher(val);
			//The email must begin with a letter in addition to matching the regex, this was added to fix an issue that came to light in test cases
			if(!matcher.matches() || !Character.isLetter(val.charAt(0))) {
				if(text_component instanceof JFormattedTextField)
					((JFormattedTextField)text_component).setValue(null);
				
				text_component.setText("");
				
				if(display_popup_warning)
					JOptionPane.showMessageDialog(null, message, "Invalid value!", JOptionPane.WARNING_MESSAGE);
				
				return false;
			}	
			
		}
		
		//Ensure the component has the same value as the text it's displaying
		if(text_component instanceof JFormattedTextField)
			((JFormattedTextField)text_component).setValue(val);
		
		return true;
	}

}//End of class
