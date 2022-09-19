package app;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.ModuleRecord;
import data.StudentRecord;
import gui.GridBagComponentHelper;
import gui.ModuleMarkTab;
import gui.PersonalDetailsTab;
import gui.StudentLookup;

public class Window extends JFrame implements ActionListener{
	
	//Constants
	final String CLEAR_FORM = "clear-form";
	final String UPDATE_STUDENT = "update-student";
	final String NEW_FILE = "new_file";
	final String OPEN_FILE = "open_file";
	final String SAVE_FILE = "save_file";
	final String SAVE_TO = "save_to";
	
	//Back-end variables
	Core app_core;
	StudentRecord student_buffer;
	ModuleRecord module_buffer;
	String id_of_loaded_student;
	
	//Interface components
	JPanel main_panel;
	JTabbedPane tabbed_pane;
	JButton update_student, clear_form;
	JMenuBar menu_bar;
	
	//Sub-components of the interface
	StudentLookup student_lookup;
	PersonalDetailsTab personal_details;
	ModuleMarkTab module_mark;
	
	//Dialogs
	JFileChooser file_chooser_dialog;
	NewModuleDialog new_module_dialog;
	TextDisplayDialog student_records_dialog;
	
	//Entry point of the program
	public static void main(String args[]) {
		AppLog.initialize();
		
		//When the application will close, runtime hooks run. This will trigger the AppLog's file output to close.
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				AppLog.info(this, "Exiting program...");
				AppLog.close();
			}
		});
		
		new Window();
	}
	
	public Window() {		
		AppLog.info(this, "Initialising window...");
		
		setTitle("Student manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initFrame();

		pack();
		setSize(350, this.getHeight());
		setLocationRelativeTo(null);
		setResizable(false);
		
		new_module_dialog = new NewModuleDialog(this);
		student_records_dialog = new TextDisplayDialog(this);
		
		file_chooser_dialog = new JFileChooser();
		file_chooser_dialog.setFileFilter(new FileNameExtensionFilter("Student records object(.sro)","sro"));
		
		id_of_loaded_student = ""; //Empty string means no student is currently loaded
		student_buffer = new StudentRecord();
		module_buffer = new ModuleRecord();
		app_core = new Core();
		
		setVisible(true);
	}
	
	private void initFrame() {
		main_panel = new JPanel(new GridBagLayout());
		
		initMenuBar();
		
		update_student = new JButton("Update student record");
		update_student.setActionCommand(UPDATE_STUDENT);
		update_student.addActionListener(this);
		
		clear_form = new JButton("Clear form");
		clear_form.setActionCommand(CLEAR_FORM);
		clear_form.addActionListener(this);
		
		student_lookup = new StudentLookup(this);
		personal_details = new PersonalDetailsTab(this);
		module_mark = new ModuleMarkTab(this);
		
		tabbed_pane = new JTabbedPane();
		tabbed_pane.add(personal_details, "Personal details");
		tabbed_pane.add(module_mark, "Modules mark");

		
		GridBagComponentHelper gbc = GridBagComponentHelper.getInstance();
		
		gbc.begin(main_panel);
		
		gbc.addComponent(student_lookup);
		gbc.newRow();
		
		gbc.addComponent(tabbed_pane);
		gbc.newRow();
		
		gbc.addComponent(update_student);
		gbc.newRow();
		
		gbc.addComponent(clear_form);
		
		gbc.end();
		
		add(main_panel);
		
	}
	
	private void initMenuBar() {
		menu_bar = new JMenuBar();
		
		JMenu file_menu = new JMenu("File");
		file_menu.setMnemonic(KeyEvent.VK_F);
		
		//Initialising JMenuItems with a display text and a mnemonic key (shortcut)
		JMenuItem new_file = new JMenuItem("New file", KeyEvent.VK_N);
		new_file.setActionCommand(NEW_FILE);
		new_file.addActionListener(this);
		
		JMenuItem open_file = new JMenuItem("Open file", KeyEvent.VK_O);
		open_file.setActionCommand(OPEN_FILE);
		open_file.addActionListener(this);
		
		JMenuItem save_file = new JMenuItem("Save file", KeyEvent.VK_S);
		save_file.setActionCommand(SAVE_FILE);
		save_file.addActionListener(this);
		
		JMenuItem save_to = new JMenuItem("Save to...", KeyEvent.VK_A);
		save_to.setActionCommand(SAVE_TO);
		save_to.addActionListener(this);
		
		file_menu.add(new_file);
		file_menu.add(open_file);
		file_menu.add(save_file);
		file_menu.add(save_to);
		menu_bar.add(file_menu);
		
		this.setJMenuBar(menu_bar);
	}
	
	//This function will be called once the window has been successfully created.
	@Override
	public void addNotify() {
		super.addNotify();
		AppLog.info(this, "Successfully initialised window!");
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case StudentLookup.LOAD_STUDENT:
				loadStudent(student_lookup.getSearchID().toUpperCase());
				break;
			
			case StudentLookup.VIEW_ALL:
				viewAll();
				break;
				
			case PersonalDetailsTab.ADD_STUDENT:
				addStudent();
				break;
				
			case PersonalDetailsTab.DELETE_STUDENT:
				deleteStudent();
				clearForm();
				break;
				
			case UPDATE_STUDENT:
				updateStudent();
				break;
				
			case ModuleMarkTab.NEW_MODULE:
				newModule();
				break;
			
			case ModuleMarkTab.REMOVE_MODULE:
				removeModule();
				break;
			case CLEAR_FORM:
				clearForm();
				break;
				
			case NEW_FILE:
				newFile();
				break;
				
			case OPEN_FILE:
				openFile();
				break;
				
			case SAVE_TO:
				saveTo();
				break;
			
			case SAVE_FILE:
				saveFile();
				break;
		}
	}
	
	private void newFile() {
		app_core.newFile();
		clearForm();
	}
	
	private void openFile() {
		int response = file_chooser_dialog.showOpenDialog(this);

		if(response == JFileChooser.APPROVE_OPTION) {
			boolean success = app_core.openFile(file_chooser_dialog.getSelectedFile().toString());
			
			if(success) {
				clearForm();
				JOptionPane.showMessageDialog(this, "File loaded!", "Opened file", JOptionPane.INFORMATION_MESSAGE);
			}
			else 
				JOptionPane.showMessageDialog(this, "Failed to open file!", "File open fail", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void saveFile() {
		boolean success = app_core.saveFile();
		if(!success)
			saveTo();
		else
			JOptionPane.showMessageDialog(this, "File saved successfully!", "File saved", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	private void saveTo() {
		int response = file_chooser_dialog.showSaveDialog(this);
		
		if(response == JFileChooser.APPROVE_OPTION) {
			boolean success = app_core.saveFileTo(file_chooser_dialog.getSelectedFile().toString()+".sro");
			if(success)
				JOptionPane.showMessageDialog(this, "File saved successfully!", "File saved", JOptionPane.INFORMATION_MESSAGE);
			else 
				JOptionPane.showMessageDialog(this, "Could not save file", "save failed", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void viewAll() {
		String[] s = app_core.getDisplayStringStudents();
		if(s != null) {
			AppLog.info(this, "User clicked view all, array of display strings is not null.");
			student_records_dialog.showDialog(s);
		}
		else {
			AppLog.info(this, "User clicked view all, no strings to display.");
			JOptionPane.showMessageDialog(this, "No records to display!", "No records", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**Clears all form entries and some buffer data to prepare the form for new entries.
	 * */
	public void clearForm() {
		student_lookup.clearForm();
		personal_details.clearForm();
		module_mark.clearForm();
		
		id_of_loaded_student = "";
		student_buffer.clearModules();
	}
	
	private void loadStudent(String search_ID) {
		if(search_ID == null || search_ID.isEmpty()) {
			AppLog.err(this, "Search failed, no student ID entered.");
			JOptionPane.showMessageDialog(this, "You must enter a student ID first.", "Search failed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		//If a student was found the getStudentRecord method will return true and will load all of the student data in student_buffer
		if(app_core.getStudentRecord(search_ID, student_buffer)) {
			personal_details.updateForm(student_buffer);
			//moduleMark.updateForm(student_buffer.getModuleListCopy());
			module_mark.updateForm(student_buffer);
			
			id_of_loaded_student = student_buffer.ID;
			
			AppLog.info(this, String.format("Loaded student with id \'%s\'.", search_ID));
		}
		else
			JOptionPane.showMessageDialog(this, "No student record found.", "Search failed", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void addStudent() {
		if(!personal_details.getValidFormEntries(student_buffer)) {
			AppLog.err(this, "Adding new student failed. Invalid entries found in personal details while fetching data.");
			JOptionPane.showMessageDialog(this, "You must fill in all personal details!", "Failed operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		/*The buffer's data is copied in a new instance so the buffer does not reference a data item inside the
		 * student tree structure. Otherwise, any modifications to the buffer will be reflected in the tree structure
		 * and will ruin the stored data. */
		StudentRecord new_student = new StudentRecord();
		new_student.copyFrom(student_buffer);
		
		//If the result of the add new student operation is false then the student could not be added
		if(!app_core.addNewStudent(new_student))
			JOptionPane.showMessageDialog(this, String.format("A student with ID \'%s\' already exists!", new_student.ID),
					"Failed to add student", JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(this, "Successfully added student!", "Student added", JOptionPane.INFORMATION_MESSAGE);
		
		id_of_loaded_student = student_buffer.ID;
	}
	
	private void deleteStudent() {
		if(id_of_loaded_student.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No student loaded.", "Delete failed", JOptionPane.WARNING_MESSAGE);
			AppLog.info(this, String.format("Delete attempt failed, no loaded student. (id_of_loaded_student: %s)", id_of_loaded_student));
			return;
		}
		
		if(!app_core.deleteStudentRecord(id_of_loaded_student)) 
			JOptionPane.showMessageDialog(this, "Failed to delete specified student. Student was not registered.", "Delete failed", JOptionPane.WARNING_MESSAGE);
		else
			JOptionPane.showMessageDialog(this, "Student successfully deleted!", "Delete successful", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void updateStudent() {
		if(id_of_loaded_student.isEmpty()) {
			JOptionPane.showMessageDialog(this, "You must load an existing record first!", "Update failed", JOptionPane.WARNING_MESSAGE);
			AppLog.info(this, "Student update failed, no student loaded.");
			return;
		}
		
		//Updating buffer data from personal details form
		//NOTE: modules do not need to be updated form the form as they are already present in the buffer
		if(!personal_details.getValidFormEntries(student_buffer)) {
			AppLog.err(this, "Adding new student failed. Invalid entries found in personal details while fetching data.");
			JOptionPane.showMessageDialog(this, "You must fill in all personal details!", "Failed operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		/*The ID of the student is a key inside the TreeMap data structure. If the ID
		 * was updated as well,the update must be handled differently since it affects the structure of the tree. */
		if(!id_of_loaded_student.equals(student_buffer.ID)) {
			AppLog.info(this, String.format("Student ID altered from \'%s\' to \'%s\'", id_of_loaded_student, student_buffer.ID));
			
			int response = JOptionPane.showConfirmDialog(this, "Student ID has been changed. Student will be removed and re-added under" +
					" the new ID.\nDo you wish to continue with the new ID? (Cliking NO will proceed with the old ID)", 
					"Update?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(response == JOptionPane.CANCEL_OPTION) {
				AppLog.info(this, "User cancelled update.");
				JOptionPane.showMessageDialog(this, "Update cancelled!", "Update failed", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			//A simple update is performed
			if(response == JOptionPane.NO_OPTION) {
				AppLog.info(this, "User selected NO. Proceeding with the old ID.");
				student_buffer.ID = id_of_loaded_student;
				
				//Attempts to update the student and checks if the update was successful
				if(app_core.updateStudent(student_buffer.ID, student_buffer)) 
					JOptionPane.showMessageDialog(this, "Student successfully updated!", "Update successful", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "Failed to update the student.", "Failed update", JOptionPane.ERROR_MESSAGE);
			}
			//Student gets deleted and re-added
			else if(response == JOptionPane.YES_OPTION) {
				AppLog.info(this, "User selected YES. Proceeding with the new ID.");
				
				deleteStudent();
				addStudent();
				loadStudent(id_of_loaded_student);
			}
		}
		//Student ID was not changed, performing simple update.
		else {
			//Attempts to update the student and checks if the update was successful
			if(app_core.updateStudent(student_buffer.ID, student_buffer)) 
				JOptionPane.showMessageDialog(this, "Student successfully updated!", "Update successful", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "Failed to update the student.", "Failed update", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void newModule() {
		boolean proceed = new_module_dialog.showModuleDataInputDialog(module_buffer);
		
		if(!proceed)
			return;
		
		if(student_buffer.contains(module_buffer.code)) {
			AppLog.info(this, String.format("User entered a module that is already present in the student record. Module code: %s", module_buffer.code));
			JOptionPane.showMessageDialog(this, "Module code entered already exists in the record!", "Duplicate module", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		student_buffer.addModule(module_buffer.code, module_buffer.mark);
		module_mark.updateForm(student_buffer);
	}
	
	private void removeModule() {
		String selected = module_mark.getModuleCodeOfSelected();
		
		student_buffer.removeModule(selected);
		module_mark.updateForm(student_buffer);
	}
	
}//end of class
