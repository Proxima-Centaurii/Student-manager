package gui;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import app.AppLog;
import app.Window;
import utils.StudentIDVerifier;

public class StudentLookup extends JPanel{
	
	public static final String LOAD_STUDENT = "load-student";
	public static final String VIEW_ALL = "view-all-students";
	
	JFormattedTextField search_box;
	JButton load, view_all;
	
	public StudentLookup(Window win) {
		super(new GridBagLayout());
		
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY, 2), 	//Border Type (colour, thickness in pixels)
				"Lookup",										//Title
				TitledBorder.LEFT,								//Title justification
				TitledBorder.TOP,								//Title position (top, bottom, top on the line, bottom on the line etc) 
				null,											//Font to be used for the title. Passing null will not change the default font
				Color.GRAY)										//Font colour
				);
		
		search_box = new JFormattedTextField();
		search_box.setInputVerifier(new StudentIDVerifier(true, "Student ID must contain 3 letters followed by 4 digits."));
		
		load = new JButton("Load");
		load.setActionCommand(LOAD_STUDENT);
		load.addActionListener(win);
		
		view_all = new JButton("View all");
		view_all.setActionCommand(VIEW_ALL);
		view_all.addActionListener(win);
		
		//Getting the helper and placing components
		GridBagComponentHelper gbc = GridBagComponentHelper.getInstance();
		
		gbc.begin(this);
		
		gbc.addComponent(new JLabel("Student id:"), 0f, 0f);
		gbc.addComponent(search_box, 2, 1);
		gbc.newRow();
		
		gbc.addComponent(load,3,1);
		gbc.newRow();
		
		gbc.addComponent(view_all,3,1);
		
		gbc.end();
	}
	
	//This function will be called once the this view has been successfully created and added to a parent component (in this case the window)
	@Override
	public void addNotify() {
		super.addNotify();
		AppLog.info(this, "Student lookup panel added.");
	}
	
	public void clearForm() {
		search_box.setText("");
	}
	
	public String getSearchID() { return (String) search_box.getValue(); }
	
	
}//end of class
