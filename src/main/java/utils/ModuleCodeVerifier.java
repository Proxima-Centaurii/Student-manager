package utils;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**An input verifier that checks if the input follows the following pattern: LL0000. (L- letter, 0- digit) It has the ability 
 * to display custom popup warning messages.
 * */
public class ModuleCodeVerifier extends InputVerifier{
	private boolean display_popup_warning;
	private String message;
	
	public ModuleCodeVerifier() {
		super();
		
		display_popup_warning = false;
	}
	
	public ModuleCodeVerifier(boolean display_popup_warning, String message) {
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
		
		if(text.length() != 6) {
			if(display_popup_warning)
				JOptionPane.showMessageDialog(null, message, "Invalid value", JOptionPane.WARNING_MESSAGE);
			
			if(text_component instanceof JFormattedTextField)
				((JFormattedTextField)text_component).setValue(text);
			
			text_component.setText("");
			return false;
		}
		
		char[] str = text.toCharArray();
		for(int i=0; i < 6; i++)
			if((i < 2 && !Character.isAlphabetic(str[i])) || (i >= 2 && !Character.isDigit(str[i]))) {
				if(display_popup_warning)
					JOptionPane.showMessageDialog(null, message, "Invalid value", JOptionPane.WARNING_MESSAGE);
				text_component.setText("");
				return false;
			}
		
		if(text_component instanceof JFormattedTextField)
			((JFormattedTextField)text_component).setValue(text);
		
		return true;
	}
}
