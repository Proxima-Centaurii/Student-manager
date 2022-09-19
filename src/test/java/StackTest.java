



import static org.junit.Assert.assertEquals;

import java.util.EmptyStackException;
import java.util.Stack;

import org.junit.Test;

import data.Stack2;

public class StackTest {

	Stack<String> original = new Stack<String>();
	Stack2<String> custom = new Stack2<String>();
	
	@Test
	public void empty_stack_test() {
		assertEquals("Empty test failed", original.empty(), custom.empty());
		
		boolean exception_a = false, exception_b = false;
		
		try {
			custom.pop();
		}catch(EmptyStackException e) {
			exception_a = true;
		}
		
		try {
			original.pop();
		}catch(EmptyStackException e) {
			exception_b = true;
		}
		
		assertEquals("Empty stack exception not properly thrown" , exception_b, exception_a);
	}
	
	@Test
	public void push_and_pop() {
		custom.push("hello");
		custom.push("world");
		
		original.push("hello");
		original.push("world");
		
		assertEquals("Stack is not the same after push operation", original.toString(), custom.toString());
		
		//Checking if the element poped off is correct
		assertEquals("Invalid element poped off the stack", original.pop(), custom.pop());
	}
	
	@Test
	public void search_and_peek() {
		custom.push("Apple");
		custom.push("Bananna");
		custom.push("Clementine");
		
		original.push("Apple");
		original.push("Bananna");
		original.push("Clementine");
		
		//Check if peek returns the same value
		assertEquals("Peek did not return the same value", original.peek(), custom.peek());
		
		//Check if the search function is correct
		assertEquals("Search functions returned different indices", original.search("Clementine"), custom.search("Clementine"));
	}

}
