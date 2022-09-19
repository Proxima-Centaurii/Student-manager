package utils;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**An input verifier that allows 3 digits to be input. The resulting number must be from 0 to 100. It has the ability to 
 * display custom popup warning messages.
 * */
public class MarkVerifier extends InputVerifier{

	private boolean display_popup_warning;
	private String message;
	
	public MarkVerifier() {
		super();
		
		display_popup_warning = false;
	}
	
	public MarkVerifier(boolean display_popup_warning, String message) {
		super();
		
		this.display_popup_warning = display_popup_warning;
		this.message = message;
	}
	
	@Override
	public boolean verify(JComponent component) {
		JTextComponent text_component = (JTextComponent)component;
		String text = text_component.getText().trim();
		
		//This will allow user to exit the text box if there is no value entered in the text component
		if(text.isEmpty())
			return true;
		
		int value = -1;
		try {
			value = Integer.parseInt(text);
		}catch(NumberFormatException e) {
			if(display_popup_warning)
				JOptionPane.showMessageDialog(null, message, "Invalid value", JOptionPane.WARNING_MESSAGE);
			
			if(text_component instanceof JFormattedTextField)
				((JFormattedTextField)text_component).setValue(null);
			
			text_component.setText("");
			return false;
		}
		
		if(value < 0 || value > 100) {
			if(display_popup_warning)
				JOptionPane.showMessageDialog(null, message, "Invalid value", JOptionPane.WARNING_MESSAGE);
			text_component.setText("");
			return false;
		}
		
		if(text_component instanceof JFormattedTextField)
			((JFormattedTextField)text_component).setValue(text);
		
		return true;
	}
	
}//end of class
