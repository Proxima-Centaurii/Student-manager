package utils;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**An input verifier that checks if the input consists only of letters. It has the ability to display custom popup warning messages.
 * */
public class AlphabeticVerifier extends InputVerifier{

	private boolean allow_white_spaces;
	private boolean display_popup_warning;
	private String message;
	
	public AlphabeticVerifier() {
		super();
		
		display_popup_warning = false;
		allow_white_spaces = false;
	}
	
	public AlphabeticVerifier(boolean allow_white_spaces, boolean display_popup_warning, String message) {
		super();
		
		this.allow_white_spaces = allow_white_spaces;
		this.display_popup_warning = display_popup_warning;
		this.message = message;
	}
	
	@Override
	public boolean verify(JComponent component) {
		JTextComponent text_component = (JTextComponent)component;
		String val = text_component.getText().trim();
		
		for(char c : val.toCharArray())
			if(!Character.isAlphabetic(c) || (c == ' ' && allow_white_spaces)) {
				if(text_component instanceof JFormattedTextField)
					((JFormattedTextField)text_component).setValue(null);
				
				text_component.setText("");
				
				if(display_popup_warning)
					JOptionPane.showMessageDialog(null, message, "Invalid value", JOptionPane.WARNING_MESSAGE);
				
				return false;
			}
		
		//Ensure the component will return the value it's displaying
		if(text_component instanceof JFormattedTextField)
			((JFormattedTextField)text_component).setValue(val);
		
		return true;
	}

}
