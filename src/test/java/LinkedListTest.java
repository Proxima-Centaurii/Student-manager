



import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Test;

import data.LinkedList2;
import org.junit.Before;

/**This JUnit class will check if the LinkedList2 class behaves like the LinkedList class.
 * The most important functionalities will be checked and the ones that are used in the scope of this project.
 * */
public class LinkedListTest {
	LinkedList<String> data = new LinkedList<String>();
	
	LinkedList2<String> custom = new LinkedList2<String>();
	LinkedList<String> original = new LinkedList<String>();
	
	@Before
	public void setUp() {
		data.add("Apple");
		data.add("Bananna");
		data.add("Melon");
		data.add("Orange");
		data.add("Bananna");
	}
	
	@Test
	public void adding_and_removing_elements() {
		//Checking add function
		custom.add("Apple");
		custom.add("Bananna");
		
		original.add("Apple");
		original.add("Bananna");
		
		assertEquals("Regular add failed", original.toString(), custom.toString());
		
		//Checking addFirst function
		custom.addFirst("Orange");
		original.addFirst("Orange");
		
		assertEquals("Add first failed", original.toString(), custom.toString());
		
		//Checking addLast function
		custom.addLast("Grape");
		original.addLast("Grape");
		
		assertEquals("Add last failed", original.toString(), custom.toString());
		
		//Checking removeLast function
		custom.removeLast();
		original.removeLast();
		
		assertEquals("Remove last failed", original.toString(), custom.toString());
		
		//Cheking removeFirst function
		custom.removeFirst();
		original.removeFirst();
		
		assertEquals("Remove first failed", original.toString(), custom.toString());
		
		custom.remove();
		original.remove();
		
		assertEquals("Failed on remove function", original.toString(), custom.toString());
		
		custom.clear();
		original.clear();
		
		assertEquals("Failed to clear data strucure", original.toString(), custom.toString());
	}
	
	@Test
	public void empty_list_remove() {
		boolean custom_exception = false, original_exception = false;
			
		assertEquals("List is not empty", original.size(), custom.size());
		
		try{
			custom.remove();
		}catch(NoSuchElementException e) {
			custom_exception = true;
		}
		
		try {
			original.remove();
		}catch(NoSuchElementException e) {
			original_exception = true;
		}
		
		assertEquals("Exception throwing missmatch", original_exception, custom_exception);
	}
	
	@Test
	public void fetching_index() {
		//Adding all
		custom.addAll(data);
		original.addAll(data);
		
		assertEquals("Failed to add elements of collection", original.toString(), custom.toString());
		
		//Getting the index of an existing element
		int a = custom.indexOf("Melon");
		int b = original.indexOf("Melon");
		
		assertEquals("Index fetched is incorrect", b, a);
		
		//Getting the index of an inexistent element
		a = custom.indexOf("inexistent");
		b = custom.indexOf("inexistent");
		
		assertEquals("Index fetched is incorrect", b, a);
		
		//Invalid index
		boolean exception_a = false, exception_b = false;
		
		try {
			custom.get(-1);
		}catch(IndexOutOfBoundsException e) {
			exception_a = true;
		}
		
		try {
			original.get(-1);
		}catch(IndexOutOfBoundsException e) {
			exception_b = true;
		}
		
		assertEquals("Index out of bounds exception not propperly thrown", exception_b, exception_a);
	}

	@Test
	public void fetch_values() {
		custom.addAll(data);
		original.addAll(data);
		
		String a = custom.getFirst();
		String b = original.getFirst();
		
		assertEquals("Failed to get first item", b, a);
	
		a = custom.getLast();
		b = original.getLast();
		
		assertEquals("Failed to get the last item", b, a);
	}
	
}
