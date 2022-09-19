package utils;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**An input verifier that checks if the input follows the following pattern: LLL0000. (L- letter, 0- digit) It has the ability 
 * to display custom popup warning messages.
 * */
public class StudentIDVerifier extends InputVerifier{
	private boolean display_popup_warning;
	private String message;
	
	public StudentIDVerifier() {
		super();
		
		display_popup_warning = false;
	}
	
	public StudentIDVerifier(boolean display_popup_warning, String message) {
		super();
		
		this.display_popup_warning = display_popup_warning;
		this.message = message;
	}
	
	@Override
	public boolean verify(JComponent component) {
		JTextComponent text_component = (JTextComponent)component;
		String text = text_component.getText().trim();
		
		if(text.isEmpty())
			return true;
		
		if(text.length() != 7) {
			if(display_popup_warning)
				JOptionPane.showMessageDialog(null, message, "Invalid value", JOptionPane.WARNING_MESSAGE);
			
			if(text_component instanceof JFormattedTextField)
				((JFormattedTextField)text_component).setValue(text);
			
			text_component.setText("");
			return false;
		}
		
		char[] str = text.toCharArray();
		for(int i=0; i < 7; i++)
			if((i < 3 && !Character.isAlphabetic(str[i])) || (i >= 3 && !Character.isDigit(str[i]))) {
				if(display_popup_warning)
					JOptionPane.showMessageDialog(null, message, "Invalid value", JOptionPane.WARNING_MESSAGE);
				
				if(text_component instanceof JFormattedTextField)
					((JFormattedTextField)text_component).setValue(text);
				
				text_component.setText("");
				return false;
			}
		
		if(text_component instanceof JFormattedTextField)
			((JFormattedTextField)text_component).setValue(text);
		
		return true;
	}
	
}
