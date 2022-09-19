package data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**A re-implementation of the LinkedList class 
 * */
public class LinkedList2<T> implements Iterable<T>, Serializable{

	ListNode2<T> head, tail;
	int size;
	
	public LinkedList2() {
		head = null;
		tail = null;
		size = 0;	
	}
	

	public int size() {
		return size;
	}
	
	/**Returns the index of the passed object or -1 if the object is not found in the list.
	 * */
	public int indexOf(Object element){
	    int index = 0;
	    for(T currentElement : this){
	        if(currentElement.equals(element))
	            return index;
	        
	        index++;
	    }
	    
	    return -1;
	}
	
	public void add(T data) {
		addLast(data);
	}
	
	/**Adds all the elements of a collection to this list.
	 * @return Returns true if the operation was carried out and false if no element was added.
	 * */
	public boolean addAll(Collection<? extends T> c) {
		if(c.size() == 0)
			return false;
		
		for(Iterator<T> it = (Iterator<T>) c.iterator(); it.hasNext();)
			add(it.next());
		
		return true;
	}
	
	/**Add an element in the list at the specified index. The index must be in range.
	 * @param index The index at which the item will be inserted.
	 * @param data The data that will be inserted.
	 * */
	public void add(int index, T data) {
		checkIndex(index);
		
		ListNode2<T> newItem = new ListNode2<T>(data);
		
		ListNode2<T> currentNode = head;
		for(int i=0; i<index; i++)
			currentNode = currentNode.next;
		
		insertNodeToLeft(newItem, currentNode);
		
		size++;
	}
	
	//This is a helper function for inserting a node. Was implemented for any possible future uses.
	private void insertNodeToLeft(ListNode2<T> newNode,ListNode2<T> dest) {
		if(dest == head) {
			newNode.next = head;
			head.previous = newNode;
			
			head = newNode;
			
			return;
		}
		
		ListNode2<T> destLeft = dest.previous;
		
		destLeft.next = newNode;
		dest.previous = newNode;
		
		newNode.previous = destLeft;
		newNode.next = dest;
	}
	
	/**Adds the specified data at the head of the list.
	 * */
	public void addFirst(T data) {
		ListNode2<T> newItem = new ListNode2<T>(data);
		
		if(head == null)
			head = tail = newItem;
		else {
			/*Set the current head to point previous at the new object and
			 * make the head handle to point at the newly added item*/
			newItem.next = head;
			head.previous = newItem;
			head = newItem;
		}
		
		size++;
	}

	/**Adds the specified data at the tail of the list.
	 * */
	public void addLast(T data) {
		ListNode2<T> newItem = new ListNode2<T>(data);
	
		if(tail == null)
			head = tail = newItem;
		else {
			/*Set the current tail to point next at the new object and
			 * make the tail handle to point at the newly added item*/
			newItem.previous = tail;
			tail.next = newItem;
			tail = newItem;
		}
		
		size++;
	}
	
	/**Removes the first node that matches the value specified. (functions just like the original linked list)
	 * @return Returns false if no matching value was found or true if the value was found and removed.
	 * */
	public boolean remove(Object o) {
		
		ListNode2<T> currentNode = head;
		for(int i=0; i<size; i++) {
			if(currentNode.data.equals(o)) {
				removeNode(currentNode);
				return true;
			}
			currentNode = currentNode.next;
		}
		
		return false;
	}
	
	//A helper function that removes a node from the list. It updates the links accordingly.
	private void removeNode(ListNode2<T> node) {
		ListNode2<T> leftNeighbour = node.previous;
		ListNode2<T> rightNeighbour = node.next;
		
		leftNeighbour.next = rightNeighbour;
		rightNeighbour.previous = leftNeighbour;
		node.next = node.previous = null;
		
		size--;
	}
	
	/**Removes a node at a specified index. The index must be in range.
	 * @return Returns the removed element.
	 * */
	public T remove(int index) {
		checkIndex(index);
		
		if(index == 0)
			return removeFirst();
		else if(index == size-1)
			return removeLast();
		
		ListNode2<T> currentNode = head;
		for(int i=0; i<index; i++)
			currentNode = currentNode.next;
		
		removeNode(currentNode);
		
		return currentNode.data;
	}
	
	/**Removes the element at the head of the list.
	 * @return Returns the element removed.
	 * */
	public T removeFirst() {
		if(size <= 0)
			throw new NoSuchElementException("There is no element to be removed from an empty list.");
		
		ListNode2<T> removedNode = head;
		head = head.next;
		
		if(head == null)
			tail = null;
		else {
			head.previous = null;
			removedNode.next = null;
		}

		size--;
		
		return removedNode.data;
	}
	
	/**Removes and returns the head of the list.
	 * */
	public T remove() {
		return removeFirst();
	}
	
	/**Removes the first object that matches the specified value. The search is done from head to tail (first to last).
	 * @return Returns true if an object was found and removed or false otherwise.
	 * */
	public boolean removeFirstOccurrence(Object o) {
		
		ListNode2<T> currentNode = head;
		for(int i=0; i < size; i++){
			if(currentNode.data.equals(o)) {
				if(i == 0)
					removeFirst();
				else if(i == size-1)
					removeLast();
				else
					removeNode(currentNode);
				
				return true;
			}
		    
		    currentNode = currentNode.next;
		}
		
		return false;
	}
	
	/**Removes and returns the tail of the list;
	 * */
	public T removeLast() {
		if(size <=0)
			throw new NoSuchElementException("There is no element to be removed from an empty list.");
		
		ListNode2<T> removedNode = tail;
		tail = tail.previous;
		
		if(tail == null)
			head = null;
		else {
			tail.next = null;
			removedNode.previous = null;
		}
		
		size--;
		
		return removedNode.data;
	}
	
	/**Removes the last object that matches the specified value. The search is done from tail to head (last to first).
	 * @return Returns true if an object was found and removed or false otherwise.
	 * */
	public boolean removeLastOccurrence(Object o) {
		
		ListNode2<T> currentNode = tail;
		for(int i=size-1; i >= 0; i--){
			if(currentNode.data.equals(o)) {
				if(i == size-1)
					removeLast();
				else if(i == 0)
					removeFirst();
				else
					removeNode(currentNode);
				
				return true;
			}
			
			currentNode = currentNode.previous;
		}
		
		return false;
	}

	/**Removes all the nodes of the list.
	 * */
	public void clear() {
		ListNode2<T> nextNode = head;
		
		while(nextNode.next != null) {
			nextNode = nextNode.next;
			head.next = null;
			head.previous = null;
			head = nextNode;
		}
		
		nextNode.previous = null;
		head = tail = nextNode = null;
		
		size = 0;
	}

	/**Checks if a certain element exists within the list.
	 * @return Returns true if a match was found or false otherwise.
	 * */
	public boolean contains(T check_data) {
		for(T currentNodeData : this)
			if(check_data.equals(currentNodeData))
				return true;
		
		return false;
	}

	/**Returns the element at the specified index.
	 * */
	public T get(int index) {
		checkIndex(index);
		
		ListNode2<T> currentNode = head;
		for(int i=0; i<index; i++)
			currentNode = currentNode.next;
		
		return currentNode.data;
	}

	/**Returns the head of the list.
	 * */
	public T getFirst() {
            if(head == null)
                throw new NoSuchElementException();
	
            return head.data;
	}
	
	/**Returns the tail of the list.
	 * */
	public T getLast() {
            if(tail == null)
                throw new NoSuchElementException();
            
            return tail.data;
	}
	
	/**Updates the value at the specified index with the new data passed.
	 * @return Returns the value that has been removed. 
	 * */
	public T set(int index, T data) {
		checkIndex(index);
		
		ListNode2<T> currentNode = head;
		for(int i=0; i<index; i++)
			currentNode = currentNode.next;
		
		T old_value = currentNode.data;
		currentNode.data = data;
		
		return old_value;
	}
	
	/**Returns the Linked list as an array of Objects.
	 * */
	public Object[] toArray() {
		Object[] arr = new Object[size];
		
		ListNode2<T> currentNode = head;
		for(int i=0; i<size; i++) {
			arr[i] = currentNode.data;
			currentNode = currentNode.next;
		}
		
		return arr;
	}
	
	/**Copies the elements of the linked list into the array passed to this function. If that array does not have
	 * sufficient space then the elements will be placed in a new array and returned.
	 * @return Returns a new array containing the elements of the linked list if the passed array does not have sufficient space.
	 * */
	public T[] toArray(T[] a) {
		if(size == 0)
			return null;
		
		if(a.length >= size) {
			
			copyElementsToArray(a);
			
			if(size < a.length)
				Arrays.fill(a, size, a.length-1, null);
			return a;
		}
		else {
			T[] newArr = (T[]) toArray();
			
			return newArr;
		}
		
	}

	public String toString() {
		if(size == 0)
			return "[]";
		
		String str = "[" + head.data;
		
		if(size == 1){
		   str += "]";
		   return str;
		}
		else
		    str += ",";
		
		ListNode2<T> currentNode = head.next;
		for(int i=1; i<size-1; i++){
			str += " " + currentNode.data + ",";
		
		    currentNode = currentNode.next;
		}
		str += String.format(" %s]", currentNode.data);
		
		return str;
	}
	
	@Override
	public Iterator<T> iterator() {
		Iterator<T> i = new Iterator<T>() {

			ListNode2<T> currentNode = head;
			
			@Override
			public boolean hasNext() {
				if(currentNode != null)
					return true;
				
				return false;
			}

			@Override
			public T next() {
				T data = currentNode.data;
				currentNode = currentNode.next;
				return data;
			}
		};
		
		return i;
	}
	
	public Iterator<T> descendingIterator(){
		Iterator<T> i = new Iterator<T>() {

			ListNode2<T> currentNode = tail;
			
			@Override
			public boolean hasNext() {
				if(currentNode != null)
					return true;
				
				return false;
			}

			@Override
			public T next() {
				T data = currentNode.data;
				currentNode = currentNode.previous;
				return data;
			}
		};
		
		return i;
	}
	
	private void checkIndex(int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException(String.format("Index out of bounds [0, %d]. Selected index: %d", size-1, index));
	}
	
	private void copyElementsToArray(Object[] a) {
		int currentIndex = 0;
		for(T data : this) 
			a[currentIndex++] = (Object) data;
	}
	
}//end of class