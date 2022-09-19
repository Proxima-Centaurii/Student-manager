package data;

import java.io.Serializable;

public class ListNode2<T> implements Serializable {

	public ListNode2<T> previous, next;
	public T data;
	
	public ListNode2() {
		previous = null;
		next = null;
	}
	
	public ListNode2(T data) {
		this();
		this.data = data;
	}
	
}//end of class