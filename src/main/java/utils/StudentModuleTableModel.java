package utils;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import app.AppLog;
import data.StudentRecord;

public class StudentModuleTableModel extends AbstractTableModel{

	private String[] column_names = {"Module code", "Mark"};
	private Object[][] data = new Object[5][2];
	
	
	
	public StudentModuleTableModel(boolean display_popup_warning) {
		super();
	}
	
	/*All of the functions below had to be overridden and implemented so the JTable can function as normal. Some
	 * of these functions can be used externally (i.e. outside the JTable) however some of them are used by the JTable itself.*/
	
	@Override
	public int getColumnCount() {
		return column_names.length;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public String getColumnName(int col) {
		return column_names[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	//---------

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int col){
		//Updates the data matrix in this class
		data[row][col] = value;
		
		//Updates the actual cell based on the data matrix in this class
		fireTableCellUpdated(row, col);
	}
	
	
}//End of class
