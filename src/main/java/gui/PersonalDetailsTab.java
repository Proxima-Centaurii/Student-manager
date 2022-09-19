package gui;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import app.AppLog;
import app.Window;
import data.StudentRecord;
import utils.AlphabeticVerifier;
import utils.EmailVerifier;
import utils.StudentIDVerifier;

public class PersonalDetailsTab extends JPanel {

	public static final String ADD_STUDENT = "add-student";
	public static final String DELETE_STUDENT = "delete-student";
	
	JFormattedTextField id,first_name, last_name, email;
	JSpinner year_of_study;
	JButton add, delete;
	
	public PersonalDetailsTab(Window win) {
		super(new GridBagLayout());
		
		AlphabeticVerifier verifier = new AlphabeticVerifier(false, true, "Only letters are allowed, no white spaces.");
		
		id = new JFormattedTextField();
		id.setInputVerifier(new StudentIDVerifier(true,"Student ID must contain 3 letters followed by 4 digits."));
		
		first_name = new JFormattedTextField();
		first_name.setInputVerifier(verifier);
		
		last_name = new JFormattedTextField();
		last_name.setInputVerifier(verifier);
		
		email = new JFormattedTextField();
		email.setInputVerifier(new EmailVerifier("Input submitted does not have a valid email format.\nValid format example: example@domain.xyz"));
		
		year_of_study = new JSpinner(new SpinnerNumberModel(0,0,10,1));
		
		add = new JButton("Add student");
		add.setActionCommand(ADD_STUDENT);
		add.addActionListener(win);
		
		delete = new JButton("Delete student");
		delete.setActionCommand(DELETE_STUDENT);
		delete.addActionListener(win);
		
		//Getting the helper and placing components
		GridBagComponentHelper gbc = GridBagComponentHelper.getInstance();
		
		gbc.begin(this);
		
		gbc.addComponent(new JLabel("ID:"), 0f,0f);
		gbc.addComponent(id);
		gbc.newRow();
		
		gbc.addComponent(new JLabel("First name:"), 0f,0f);
		gbc.addComponent(first_name);
		gbc.newRow();
		
		gbc.addComponent(new JLabel("Last name:"), 0f, 0f);
		gbc.addComponent(last_name);
		gbc.newRow();
		
		gbc.addComponent(new JLabel("Email address:"), 0f, 0f);
		gbc.addComponent(email);
		gbc.newRow();
		
		gbc.addComponent(new JLabel("Year of study:"), 0f, 0f);
		gbc.addComponent(year_of_study);
		gbc.newRow();
		
		gbc.addComponent(add);
		gbc.addComponent(delete);
		
		gbc.end();
	}
	
	//This function will be called once the this view has been successfully created and added to a parent component (in this case the window)
	@Override
	public void addNotify() {
		super.addNotify();
		AppLog.info(this, "Personal details panel added.");
	}

	public void updateForm(StudentRecord buffer){
		id.setValue(buffer.ID);
		first_name.setValue(buffer.first_name);
		last_name.setValue(buffer.last_name);
		email.setValue(buffer.email);
		year_of_study.setValue(buffer.year_of_study);
	}
	
	public void clearForm() {
		id.setValue(null);
		first_name.setValue(null);
		last_name.setValue(null);
		email.setValue(null);
		year_of_study.setValue(0);
	}
	
	//The entries will be forced to be valid because of the input verifiers. However the inputs can be empty which should no be allowed.
	private boolean validateEntries() {
		if(id.getValue() == null || first_name.getValue() == null || last_name.getValue() == null || email.getValue() == null)
			return false;
		
		return true;
	}

	public boolean getValidFormEntries(StudentRecord buffer) {
		if(!validateEntries())
			return false;
		
		buffer.ID = ((String)id.getValue()).toUpperCase();
		buffer.first_name = (String)first_name.getValue();
		buffer.last_name = (String)last_name.getValue();
		buffer.email = (String)email.getValue();
		buffer.year_of_study = (Integer)year_of_study.getValue();
		
		return true;
	}
	
	
	
}//End of class
