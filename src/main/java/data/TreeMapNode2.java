package data;

import java.io.Serializable;

/**Tree node used to link the keys and values of a 'Red-Black' based TreeMap.
 * K - the class of the key, is must be a comparable (extends comparable)
 * V - the class of the value
 * colour = BLACK then colour = true
 * colour = RED then colour = false 
 * */
public class TreeMapNode2<K extends Comparable<K>,V> implements Serializable {
	
	public TreeMapNode2<K,V> left, right, parent;
	public boolean colour;
	public V value;
	public K key;
	
	public TreeMapNode2() {
		left = null;
		right = null;
		parent = null;
		colour = false;
	}
	
	public TreeMapNode2(K key, V value) {
		this();
		this.key = key;
		this.value = value;
	}
	
	public String toString(){return key.toString()+"="+value.toString();}
	
}//end of class
