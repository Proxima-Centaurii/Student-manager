package app;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import data.ModuleRecord;
import gui.GridBagComponentHelper;
import utils.MarkVerifier;
import utils.ModuleCodeVerifier;

/**A custom dialog designed specifically for entering module details.
 * */
public class NewModuleDialog extends JDialog implements ActionListener{

	JFormattedTextField code;
	JFormattedTextField mark;
	
	JButton add,cancel;
	
	int result = 0;
	
	public NewModuleDialog(Frame owner) {
		//Passes the parent window to the super class, sets a title. The last argument means that the user input is restricted to this dialog until closed.
		super(owner, "Enter module information", true);

		setLayout(new GridBagLayout());
		
		code = new JFormattedTextField();
		code.setInputVerifier(new ModuleCodeVerifier(true, "You must enter 2 letters followed by 4 digits"));
		mark = new JFormattedTextField();
		mark.setInputVerifier(new MarkVerifier(true, "You must enter a number from 0 to 100."));
		
		add = new JButton("Add");
	    add.setActionCommand("add");
	    add.addActionListener(this);
		
	    cancel = new JButton("Cancel");
	    cancel.setActionCommand("cancel");
	    cancel.addActionListener(this);
	    
		GridBagComponentHelper gbc = GridBagComponentHelper.getInstance();

		gbc.begin(this);
		
		gbc.addComponent(new JLabel("Module code:"), 0f,0f);
		gbc.addComponent(code);
		gbc.newRow();
		
		gbc.addComponent(new JLabel("Mark:"), 0f, 0f);
		gbc.addComponent(mark);
		gbc.newRow();
		
		gbc.addComponent(cancel);
		gbc.addComponent(add);
		
		gbc.end();
		
		pack();
		setSize(250, this.getHeight());
		setLocationRelativeTo(owner);
		setResizable(false);
		
		//Upon closing, the dialog will free all resources and only close itself and not the whole program.
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	/**Displays a dialog for entering module information. This function will only return a value once the dialog is closed
	 * and the thread resumes to the point where this function was called.
	 * @param buffer A buffer object used to gather the details from the interface and pass it outside the function.
	 * @return Returns true if the data was entered and is valid or returns false if the user exits the dialog.
	 * */
	public boolean showModuleDataInputDialog(ModuleRecord buffer) {
		//Clears the interface
		code.setValue(null);
		mark.setValue(null);
		
		setVisible(true);
		
		if(result == 1 && !code.getText().isEmpty() && !mark.getText().isEmpty()) {
			buffer.code = code.getText().toUpperCase();
			buffer.mark = Integer.parseInt(mark.getText());
			return true; //Dialog exits with data correctly input
		}
		
		return false; //Dialog simply exits, no data input
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "cancel":
				result = 0;
				break;
			case "add":
				result = 1;
				break;
		}
		
		//Causes the dialog to close and free any native resources (OS specific objects) taken to display the window.
		dispose();
	}
}
