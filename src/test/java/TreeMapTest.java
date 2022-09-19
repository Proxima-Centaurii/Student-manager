



import static org.junit.Assert.assertEquals;

import java.util.TreeMap;

import org.junit.Test;

import data.TreeMap2;
import org.junit.Before;

public class TreeMapTest {
	
	TreeMap<String, Integer> original = new TreeMap<String, Integer>();
	TreeMap2<String, Integer> custom = new TreeMap2<String, Integer>();
	
	@Before
	public void setUp() {		
		original.putIfAbsent("A", 1);
		original.putIfAbsent("D", 4);
		original.putIfAbsent("B", 2);
		original.putIfAbsent("E", 5);
		original.putIfAbsent("C", 3);

		custom.putIfAbsent("A", 1);
		custom.putIfAbsent("D", 4);
		custom.putIfAbsent("B", 2);
		custom.putIfAbsent("E", 5);
		custom.putIfAbsent("C", 3);
	}
	
	@Test
	public void inserting_elements() {
		assertEquals("Strings representations do not match", original.toString(), custom.toString());
		
		Integer previous_a = custom.putIfAbsent("A", 999);
		Integer previous_b = original.putIfAbsent("A", 999);
		
		assertEquals("Previous values associated with the given key do not match", previous_b, previous_a);
		
		previous_a = custom.putIfAbsent("F", 6);
		previous_b = original.putIfAbsent("F", 6);
		
		assertEquals("Previous values associated with the given key do not match", previous_b, previous_a);
		
		//Check that the structures return the same size
		assertEquals("Structures do not return the same size", original.size(), custom.size());
	}
	
	@Test
	public void fetching_elements() {
		Integer a = custom.get("B");
		Integer b = original.get("B");
		
		assertEquals("Fetched integers to not match", b, a);
	}
	
	@Test
	public void removing_elements() {
		Integer a = custom.remove("B");
		Integer b = original.remove("B");
		
		assertEquals("Removed integers do not match", b,a);
		assertEquals("Different key-value pairs after removing value", original.toString(), custom.toString());
		
		a = custom.remove("A");
		b = original.remove("A");
		
		assertEquals("Removed integers do not match", b,a);
		assertEquals("Different key-value pairs after removing value", original.toString(), custom.toString());
		
		//Removing an already removed key-value pair
		a = custom.remove("A");
		b = original.remove("A");
		
		//Removing the root
		a = custom.remove("C");
		b = original.remove("C");
		
		assertEquals("Removed integers do not match", b,a);
		assertEquals("Different key-value pairs after removing value", original.toString(), custom.toString());
		
		custom.clear();
		original.clear();
		
		assertEquals("Missmatch after clearing structures", original.toString(), custom.toString());
	}

}
