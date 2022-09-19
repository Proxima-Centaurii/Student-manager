package data;

import java.util.Iterator;

public class TreeMapIterator2<K extends Comparable<K>,V> implements Iterator<V>{

	Stack2<TreeMapNode2<K,V>> stack;
	
	public TreeMapIterator2(TreeMapNode2<K,V> node){
		stack = new Stack2<TreeMapNode2<K,V>>();
		fill(node);
	}

	/*Because the iteration is done in ascending order, this function will fill the stack with all the elements on
	 * the left branch of any given node because they are the smallest. They will also be pushed from the biggest
	 * value to the smallest.
	 * */
	private void fill(TreeMapNode2<K,V> node) {
		while(node != null) {
			stack.push(node);
			node = node.left;
		}
	}
	
	/*The stack can be empty only if there are no more nodes to iterate over. The stack will stop filling once
	 *the right-lower most element has been added.
	 * */
	public boolean hasNext() {
		return !stack.empty();
	}

	
	public V next() {
		TreeMapNode2<K,V> currentNode = stack.pop();
		
		fill(currentNode.right);
		
		return currentNode.value;
	}

	public TreeMapNode2<K,V> nextNode(){
		TreeMapNode2<K,V> currentNode = stack.pop();
		
		fill(currentNode.right);
		
		return currentNode;
	}
	
}
