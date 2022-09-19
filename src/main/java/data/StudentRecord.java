package data;

import java.io.Serializable;

public class StudentRecord implements Serializable{
	//A constant describing the maximum size of modules that can be registered
	public static final int MODULES_SIZE = 5;
	
	//The array below contains special characters. It is stored as number in order to prevent encoding errors that might occur when saving source code.
	private static final char[] c = new char[] {9562,9556,9559,9565,9553,9552,9571,9574,9577,9568};
	
	public String ID;
	public String first_name, last_name,email;
	public int year_of_study;
	
	private ModuleRecord modules[];
	//Keeps track of how many modules are registered
	private int no_of_modules;
	
	public StudentRecord() {
		ID = first_name = last_name = email = "";
		year_of_study = -1;
		
		modules = new ModuleRecord[MODULES_SIZE];
		for(int i=0; i<MODULES_SIZE; i++)
			modules[i] = new ModuleRecord();
		
		no_of_modules = 0;
	}
	
	/**Add a module to the student record. If there are more than 5 modules being added, only the 5 most recent will be kept.
	 * @param code The module code.
	 * @param mark The module mark;
	 * */
	public void addModule(String code, int mark) {	
		//Less than 5 modules
		if(no_of_modules < MODULES_SIZE) {
			modules[no_of_modules].code = code;
			modules[no_of_modules].mark = mark;
			
			no_of_modules++;
		}
		//More than 5 modules
		else {
			//Shift all modules to left
			for(int i=0; i<MODULES_SIZE-1; i++) { 
				modules[i].code = modules[i+1].code;
				modules[i].mark = modules[i+1].mark;
			}
			
			//Add the new module at the end
			modules[MODULES_SIZE-1].code = code;
			modules[MODULES_SIZE-1].mark = mark;
		}
	}
	
	/**Removes a module from the array. This is done by setting it's code to 'null' and mark to 0.
	 * @param code The code of the module to be removed.
	 * */
	public void removeModule(String code) {
		if(code == null || code.isEmpty() || no_of_modules == 0)
			return;

		//Getting the index of the module to be removed.
		int i;
		boolean found = false;
		for(i=0; i<no_of_modules; i++)
			if(modules[i].code.equals(code)) {
				found = true;
				break;
			}
		
		//Exit the function if the module was not found
		if(!found)
			return;
		
		//If the module to be removed happens to be the last one registered in the list, simply remove it.
		if(i == no_of_modules-1) {
			modules[i].code = null;
			modules[i].mark = 0;
		}
		//The module to be removed is not the last one
		else {
			//Shift all modules that are on it's right towards the left side.
			for(;i<no_of_modules-1;i++)
				modules[i].code = modules[i+1].code;
				modules[i].mark = modules[i+1].mark;
			
			//Finally, remove the last element (will be duplicate of it's previous)
			modules[i].code = null;
			modules[i].mark = 0;
		}
		
		no_of_modules--;
	}
	
	public void clearModules() {
		no_of_modules = 0;
		
		for(ModuleRecord m : modules) {
			m.code = null;
			m.mark = 0;
		}
		
	}
	
	/**Copies the module record data form a student object to given destination array.
	 * @param dest The array in which the module record data will be copied into.
	 * */
	public void copyModulesTo(ModuleRecord[] dest) {
		for(int i=0; i<MODULES_SIZE; i++)
			dest[i] = modules[i];
	}
	
	/**Copies in the data from another student object. This avoids mixing the references of member object.
	 * @param original The object from which the data will be copied from.
	 * */
	public void copyFrom(StudentRecord original) {
		ID = original.ID;
		first_name = original.first_name;
		last_name = original.last_name;
		email = original.email;
		year_of_study = original.year_of_study;
		
		ModuleRecord[] originalList = original.modules;
		for(int i=0; i<MODULES_SIZE; i++) { 
			modules[i].code = originalList[i].code;
			modules[i].mark = originalList[i].mark;
		}
		
		no_of_modules = original.no_of_modules;
	}
	
	/**Returns a formatted string that is a representation of the student record.
	 * */
	public String getDisplayString() {
		String output = "";
		
		//Appends a string that displays student ID as a title.
		// %7s means there are at least 7 characters spaces in front of the string that it can overwrite (string padding)
		output += String.format("=====[ID: %7s]=====\n", ID);
		output += String.format("First name: %s\nLast name: %s\nEmail: %s\nYear of study: %d\n", first_name, last_name, email, year_of_study);
		
		//Produces: '~~~~Modules~~~~' (table name)
		output += String.format("%-4sModules%4s\n", c[1], c[2]).replace(' ', c[5]);
		
		//Adds a table head with columns 'CODE' and 'MARK'
		output += String.format("%c%-6s%c%6s%c\n", c[9], "CODE", c[7], "MARK", c[6]).replace(' ', c[5]);
		
		for(int i=0; i<no_of_modules; i++)
			output += String.format("%c%-6s%c%6d%c\n", c[4],modules[i].code, c[4], modules[i].mark, c[4]);
		
		output += String.format("%c%6s%c%6s%c\n", c[0],"",c[8],"",c[3]).replace(' ', c[5]);
		
		return output;
	}
	
	public boolean contains(String code) {
		for(int i=0; i<no_of_modules; i++)
			if(code.equals(modules[i].code))
				return true;
		
		return false;
	}
	
}//end of class
