package gui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

import app.AppLog;
import app.Window;
import data.ModuleRecord;
import data.StudentRecord;
import utils.ArrayUtils;
import utils.StudentModuleTableModel;

public class ModuleMarkTab extends JPanel{
	
	public static final String NEW_MODULE = "new-module";
	public static final String REMOVE_MODULE = "remove-module";
	
	ModuleRecord[] sortedList;
	
	JTable table;
	JButton new_entry, remove_entry;
	
	public ModuleMarkTab(Window win) {
		super(new GridBagLayout());
		
		sortedList = new ModuleRecord[5];

		//Creates a new table and overrides the editCellAt method in that instance so the user cannot modify table values
		table = new JTable(new StudentModuleTableModel(true));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JTableHeader head = table.getTableHeader();
		
		//Disables the ability to swap the position of the columns in the table
		head.setEnabled(false); 					
			
		/*Problems:
			1. Adding the table directly to the ModuleMarkTab or a basic JPanel will cause only the table head to be visible
			2. Adding the table to a JScrollPane will make the JScrollPane to expand the main window, Jpanel.setSize does not work
		  Fix: treat the table body and table head as separate components and add them in a JPanel with border layout as below
		*/
		JPanel j = new JPanel(new BorderLayout());
		j.add("North",head);
		j.add("Center",table);
		
		new_entry = new JButton("Add new module");
		new_entry.setActionCommand(NEW_MODULE);
		new_entry.addActionListener(win);
		
		remove_entry = new JButton("Remove module");
		remove_entry.setActionCommand(REMOVE_MODULE);
		remove_entry.addActionListener(win);
		
		GridBagComponentHelper gbc = GridBagComponentHelper.getInstance();
		
		gbc.begin(this);
		
		gbc.addComponent(j,2,1, 1f, 0f);
		gbc.newRow();
		
		gbc.addComponent(new_entry, 1f, .5f);
		gbc.addComponent(remove_entry, 1f, .5f);
		
		gbc.end();

	}

	@Override
	public void addNotify() {
		super.addNotify();
		AppLog.info(this, "Module mark panel added.");
	}
	
	public void updateForm(StudentRecord buffer) {
		buffer.copyModulesTo(sortedList);
		
		ArrayUtils.insertionSort(sortedList);
		//Insertion sort will sort the list in ascending order so the array must be reversed
		ArrayUtils.reverse(sortedList);
		
		refreshTable();
	}
	
	//This function will update the JTable so the data will be displayed inside the GUI
	private void refreshTable() {
		//Add all the assigned values to the JTable and fill in with empty 'spaces' the remaining rows (clears any previous data in the JTable)
		
		//Replace or add new data
		int j = 0;
		for(int i = 0; i<sortedList.length; i++) {
			if(sortedList[i].code != null) {
				table.setValueAt(sortedList[i].code, j, 0);
				table.setValueAt(sortedList[i].mark, j, 1);
				j++;
			}
		}
		//Clear remaining rows if there are any left
		for(;j<sortedList.length; j++) {
			table.setValueAt(null, j, 0);
			table.setValueAt(null, j, 1);
		}
	}
	
	/**Returns the code of the module selected in the JTable
	 * */
	public String getModuleCodeOfSelected() {
		int selected_row_id = table.getSelectedRow();
		
		if(selected_row_id < 0)
			return "";
		
		return (String)table.getValueAt(table.getSelectedRow(), 0);
	}
	
	public void clearForm() {
		for(int i=0; i< 5; i++) {
			table.setValueAt(null, i, 0);
			table.setValueAt(null, i, 1);
		}
	}
		
}//End of class
