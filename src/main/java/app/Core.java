package app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import data.StudentRecord;
import data.TreeMap2;
import data.TreeMapIterator2;

public class Core {
	
	private TreeMap2<String, StudentRecord> students;
	private String current_file;
	
	public Core() {
		students = new TreeMap2<String,StudentRecord>();
		current_file = ""; //Empty string means no file is loaded or file not saved yet
	}
	
	public boolean addNewStudent(StudentRecord s) {
		//If the new record was successfully added then the function putIfAbsent should return null
		if(students.putIfAbsent(s.ID, s) == null) {
			AppLog.info(this, "Student added!");
			return true;
		}
	
		AppLog.info(this, String.format("A student with ID \'%s\' already exists!", s.ID));
		
		return false;
	}
	
	public boolean getStudentRecord(String ID, StudentRecord buffer) {
		StudentRecord fetchResult = students.get(ID);
		if(fetchResult != null) {
			buffer.copyFrom(fetchResult);
			return true;
		}
		else {
			AppLog.err(this, String.format("Could not find student with ID \'%s\'", ID));
			return false;
		}
	}
	
	public boolean deleteStudentRecord(String ID) {
		//The remove function returns the previous value associated with key 'ID'. If there is no such key then the returned value is null.
		if(students.remove(ID) != null) {
			AppLog.info(this, String.format("Student with ID \'%s\' was removed.", ID));
			return true;
		}
		
		AppLog.err(this, String.format("Could not remove student with ID \'%s\'. Student not found.", ID));
		
		return false;
	}
	
	public boolean updateStudent(String ID, StudentRecord updated_student) {
		StudentRecord fetchResult = students.get(ID);
		if(fetchResult != null) {
			fetchResult.copyFrom(updated_student);
			AppLog.info(this, String.format("Successfully updated student with ID \'%s\'", ID));
			return true;
		}
		else {
			AppLog.err(this, String.format("Could not update student with ID \'%s\'. Student not found.", ID));
			return false;
		}
	}
	
	public int getStudentCount() {
		return students.size();
	}

	public String[] getDisplayStringStudents() {
		int size = students.size();
		if(size < 1)
			return null;
		
		String[] output = new String[size];

		//Iterates over the nodes of the tree map, retrieving the display string (formatted text) for each student
		TreeMapIterator2<String,StudentRecord> it = (TreeMapIterator2<String,StudentRecord>) students.iterator();
		int i = 0;
		while(it.hasNext())
			output[i++] = it.next().getDisplayString();
		
		return output;
	}
	
	public void newFile() {
		current_file = "";
		students.clear();
	}
	
	public boolean openFile(String path) {
		ObjectInputStream obin = null;
		try {
			obin = new ObjectInputStream(new FileInputStream(path));
			students = (TreeMap2<String,StudentRecord>) obin.readObject();
		}catch(Exception e) {
			AppLog.err(this, String.format("Could not read student records from file. Path: %s", path));
			AppLog.printStackTrace(this, e.getStackTrace());
			return false;
		}
		finally {
			try {
				obin.close();
			}catch(IOException ioe) {
				AppLog.err(this, "Could not close object input stream.");
				AppLog.printStackTrace(this, ioe.getStackTrace());
				return false;
			}
		}
		
		current_file = path;
		AppLog.info(this, String.format("Successfully opened file! Path: %s", path));
		
		return true;
	}
	
	public boolean saveFile() {
		if(current_file.isEmpty()) {
			AppLog.info(this, "Cannot save to current file in use, no file in use.");
			return false;
		}
		
		ObjectOutputStream obout = null;
		try {
			obout = new ObjectOutputStream(new FileOutputStream(current_file));
			obout.writeObject(students);
		}catch(Exception ex) {
			AppLog.err(this, String.format("Could not write student records to file. Path: %s", current_file));
			AppLog.printStackTrace(this, ex.getStackTrace());
			return false;
		}
		finally {
			try {
				obout.close();
			}catch(IOException ioe) {
				AppLog.info(this, "Could not close object output stream.");
				AppLog.printStackTrace(this, ioe.getStackTrace());
				return false;
			}
		}
		
		AppLog.info(this, String.format("Successfullly saved file! Path: %s", current_file));
		
		return true;
	}
	
	public boolean saveFileTo(String path) {
		AppLog.info(this, String.format("Saving file to specified destination... (path: %s)", path));
		current_file = path;
		boolean success = saveFile();
		
		return success;
	}
	
	
}//end of class
