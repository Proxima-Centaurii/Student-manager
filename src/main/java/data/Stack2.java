package data;

import java.io.Serializable;
import java.util.EmptyStackException;

public class Stack2<T> extends LinkedList2 implements Serializable{
	
	public Stack2() {
		super();
	}
	
	public boolean empty() {
		if(size() == 0)
			return true;
		
		return false;
	}
	
	public T peek() {
		if(empty())
			return null;
		
		return (T)getFirst();
	}
	
	public T pop() {
		if(empty())
			throw new EmptyStackException();
		
		T data = (T)getFirst();
		
		removeFirst();
		
		return data;
	}
	
	public T push(T data) {
		addFirst(data);
		
		return (T)getFirst();
	}
	
	/**Returns the position of the element in stack starting the count from 1.
	 * */
	public int search(Object o) {
		return indexOf(o)+1;
	}
	
	public String toString() {
		if(size == 0)
			return "[]";
		
		String str = "[" + tail.data;
		
		if(size == 1){
		   str += "]";
		   return str;
		}
		else
		    str += ",";
		
		ListNode2<T> currentNode = tail.previous;
		for(int i=1; i<size-1; i++){
			str += " " + currentNode.data + ",";
		
		    currentNode = currentNode.previous;
		}
		str += String.format(" %s]", currentNode.data);
		
		return str;
	}
	
}//end of class
