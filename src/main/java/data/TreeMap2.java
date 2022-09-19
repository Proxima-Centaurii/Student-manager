package data;

import java.io.Serializable;
import java.util.Iterator;

/**A 'Red-Black tree' based implementation of a Binary Search Tree (BST) where
 * values are mapped to a unique key. The values are sorted according to the key's
 * comparator.
 * In a 'Red-Black tree', the structure of the underlying tree is optimised so that
 * the height is kept to a minimum. This is important since the worst case time to
 * search for an element and the memory usage in such a data structure is O(h) (h is
 * the height of the tree). The 'Red-Black tree' algorithm optimises the tree structure
 * by making node rotations and/or colouring the nodes according to the following rules:
 * 
 * 1. Each node is either RED or BLACK
 * 2. The root is black
 * 3. All NULL leaves are black
 * 4. A red node can't have red children (must be black)
 * 5. All from the root node to the leaves must contain the same number of black nodes (not counting the root)
 * */
public class TreeMap2<K extends Comparable<K>,V> implements Iterable, Serializable{

	static final boolean RED = false;
	static final boolean BLACK = true;
	
	TreeMapNode2<K,V> root;
	int size;
	
	public TreeMap2() {
		root = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	/**Gets the value associated with the given key.
	 * @param key The key to look for in the tree.
	 * @return Returns the value associated with the key or null if they key could not be found.
	 * */
	public V get(K key) {
		TreeMapNode2<K,V> result = searchNode(key);
		
		return (result != null) ? result.value : null;
	}
	
	//Helper function that searches for a node and returns it. This will be a binary search in nature.
	private TreeMapNode2<K,V> searchNode(K key) {
		TreeMapNode2<K,V> currentNode = root;
		
		/*Search until the end of the leaf nodes have been reached. The the tree is a binary tree, all values to the
		 * left of a note are smaller and all the values on the right are bigger. With this in mind the key is compared
		 * to the current node and progression to the next one is made accordingly.*/
		while(currentNode != null) {
			if(key.compareTo(currentNode.key) < 0)
				currentNode = currentNode.left;
			else if(key.compareTo(currentNode.key) > 0)
				currentNode = currentNode.right;
			else
				return currentNode;
		}
		
		
		return null;
	}
	
	/**Places a value in the tree only if the specified key is not present in the tree map.
	 * @return Returns the key if it's already present in the tree or null if it's not.
	 * */
	public V putIfAbsent(K key, V value){
	    TreeMapNode2<K,V> previous = searchNode(key);
	    
	    if(previous == null){
	        insertNode(key,value, false);
	        return null;
	    }
	    else
	        return previous.value;
    }
	
	/**Inserts or replaces the key-value pair into the tree.
	 * @param key The key value.
	 * @param value The value.
	 * @param replaceAllowed If this is set to true then when a node with a matching key if found the value will be replaced. If this is false then the key-value pair can only be added when no match is found.  
	 * */
	private void insertNode(K key, V value, boolean replaceAllowed) {
		TreeMapNode2<K,V> currentNode = root;
		TreeMapNode2<K,V> parent = null;
		
		//Traversing the tree
		//Search for note with the specified key. This node should not exist therefore the parent of the null node will be returned.
		while(currentNode != null) {
			parent = currentNode;
			
			if(key.compareTo(currentNode.key) > 0)
				currentNode = currentNode.right;
			else if(key.compareTo(currentNode.key) < 0)
				currentNode = currentNode.left;
			else if(replaceAllowed){
				currentNode.value = value;
				return;
			}
			else
				throw new IllegalArgumentException("TreeMap already contains a node with key " + key);
		}
		
		//Inserting the new node
		TreeMapNode2<K,V> newNode = new TreeMapNode2<K,V>(key, value);
		newNode.colour = RED;
		
		//If parent is null then the tree had no nodes therefore the inserted node is now the root
		if(parent == null)
			root = newNode;
		else if(key.compareTo(parent.key) < 0)
			parent.left = newNode;
		else
			parent.right = newNode;
		
		newNode.parent = parent;
		
		fixPropertiesPostInsert(newNode);
		
		size++;
	}
	
	//Removes the node that corresponds with the specified key if it exists. This is a function specific to the Red-Black tree algorithm.
	private TreeMapNode2<K,V> deleteNode(K key) {
		TreeMapNode2<K,V> node = searchNode(key);
		
		//Node requested was not found, exit the function
		if(node == null)
			return null;
		
		TreeMapNode2<K,V> removed = new TreeMapNode2<K,V>(node.key, node.value);
		
		//Deleting process starts
		TreeMapNode2<K,V> movedUpNode, inOrderSuccessor = null;
		boolean deletedNodeColour;
		
		//Node has zero or one child
		if(node.left == null || node.right == null) {
			movedUpNode = deleteNodeWithMaxOneChild(node);
			deletedNodeColour = node.colour;
		}
		else {
			//Finds the minimum value node in the right subtree
			inOrderSuccessor = findMinimum(node.right);
			
			node.value = inOrderSuccessor.value;
			node.key = inOrderSuccessor.key;
			
			movedUpNode = deleteNodeWithMaxOneChild(inOrderSuccessor);
			deletedNodeColour = inOrderSuccessor.colour;
		}
		
		if(deletedNodeColour == BLACK) {
			fixPropertiesPostDelete(movedUpNode);
			
			//Finish removing the node if the moved up node was a leaf node
			if(movedUpNode == inOrderSuccessor || movedUpNode == node)
				replaceParentsChild(movedUpNode.parent, movedUpNode, null);
		}
		
		size--;
		return removed;
	}
	
	/**Removes the key-value pair form the tree and returns the value member of the pair.
	 * */
	public V remove(K key) {
		TreeMapNode2<K,V> node = deleteNode(key);
		if(node == null)
			return null;
		else
			return node.value;
	}
	
	/**Removes all the key-value pairs in the tree.
	 * */
	public void clear(){
	    root = null;
	    size = 0;
	}
	
	//This is a function specific to the Red-Black tree algorithm.
	private void fixPropertiesPostInsert(TreeMapNode2<K,V> node) {
		TreeMapNode2<K,V> parent = node.parent;
		
		//Case 1: New node is the root. Ensuring property #2 is fulfilled (black root).
		if(parent == null) {
			node.colour = BLACK;
			return;
		}
		
		//In the next cases the parent node is always RED. If that condition is not met then exit the function now.
		if(parent.colour == BLACK)
			return;
		
		//Case 2: Parent and Uncle nodes are red. Ensuring property #4 is fulfilled (no red-red).
		TreeMapNode2<K,V> grandparent = parent.parent;
		TreeMapNode2<K,V> uncle = this.getUncle(parent);
		
		if(uncle != null && uncle.colour == RED) {
			parent.colour = BLACK;
			uncle.colour = BLACK;
			grandparent.colour = RED;
			
			//Since grandparent is now red, this function will be called recursively to
			//check if there are more nodes to be fixed and the whole tree is balanced
			fixPropertiesPostInsert(grandparent);
		}
		
		//Case 3: Parent is RED, Uncle is BLACK, new node is left child of Parent (inner grand child)
		//Case 4: Parent is RED, Uncle is BLACK, new node is right child of Parent (outer grand child)
		
		//Parent is left child of grandparent
		else if(parent == grandparent.left) {
			if(node == parent.right) {
				rotateLeft(parent);
				
				parent = node;
			}
			
			rotateRight(grandparent);
			
			parent.colour = BLACK;
			grandparent.colour = RED;
		}
		//Parent is right child of grandparent
		else {
			if(node == parent.left) {
				rotateRight(parent);
				
				parent = node;
			}
			
			rotateLeft(grandparent);
			
			parent.colour = BLACK;
			grandparent.colour = RED;
		}
	}
	
	//This is a function specific to the Red-Black tree algorithm.
	private void fixPropertiesPostDelete(TreeMapNode2<K,V> node) {
		//Case 1: deleted node is the root
		if(node == root) {
			node.colour = BLACK;
			return;
		}
		
		TreeMapNode2<K,V> sibling = getSibling(node);
		
		//Case 2: Sibling is red
		if(!isBlack(sibling)) {
			node.parent.colour = RED;
			sibling.colour = BLACK;
			
			if(node == node.parent.left)
				rotateLeft(node.parent);
			else
				rotateRight(node.parent);
			
			sibling = getSibling(node); //Getting new sibling for fall-through cases
		}

		//Case 3+4
		if(isBlack(sibling.left) && isBlack(sibling.right)) {
			//Both cases require to colour the sibling red
			sibling.colour = RED;
			
			//Only case 3 requires to re-colour the parent black
			if(node.parent.colour == RED) 				
				node.parent.colour = BLACK;
			else 
				fixPropertiesPostDelete(node.parent);
		}
		
		//Cases 5 & 6
		else {
			boolean nodeLeftOfParent = node == node.parent.left;
			
			//Case 5: Black sibling with at least one red child, "outter nephew" is black
			if(nodeLeftOfParent && isBlack(sibling.right)) {
				sibling.left.colour = BLACK;
				sibling.colour = RED;
				
				rotateRight(sibling);
				sibling.colour = sibling.parent.colour;
				sibling = node.parent.right;
			}
			else if(!nodeLeftOfParent && isBlack(sibling.left)) {
				sibling.right.colour = BLACK;
				sibling.colour = RED;
				
				rotateLeft(sibling);
				sibling = node.parent.left;
			}
			
			sibling.colour = node.parent.colour;
			node.parent.colour = BLACK;
			
			if(nodeLeftOfParent) {
				sibling.right.colour = BLACK;
				rotateLeft(node.parent);
			}
			else {
				sibling.left.colour = BLACK;
				rotateRight(node.parent);
			}
		}
	}
	
	//XXX-----HELPER FUNCTIONS--------//
	
	/**Finds the minimum value node in a subtree with the parent node passed to this function.
	 * @param node The parent node of the subtree (or tree)
	 * @return The minimum value in the respective subtree (or tree)
	 * */
	private TreeMapNode2<K,V> findMinimum(TreeMapNode2<K,V> node){
		while(node.left != null)
			node = node.left;
		
		return node;
	}
	
	/**A helper function implemented to get the sibling of a given node.
	 * @param node The node that for which the sibling will be returned.
	 * @return The sibling of the passed node.
	 * */
	private TreeMapNode2<K,V> getSibling(TreeMapNode2<K,V> node){
		TreeMapNode2<K,V> parent = node.parent;
		
		if(node == parent.left)
			return parent.right;
		else if(node == parent.right)
			return parent.left;
		else
			throw new IllegalStateException("Node is not a child of its parent.");
	}
	
	/**A helper function implemented to get the uncle of a node given its parent node.
	 * @param parent - the parent of the node
	 * @return Returns the uncle of the parent's child
	 * */
	private TreeMapNode2<K,V> getUncle(TreeMapNode2<K,V> parent){
		TreeMapNode2<K,V> grandparent = parent.parent;
		
		//Check on which side of the grandparent the passed parent node is and return the node on opposite side
		if(grandparent.left == parent)
			return grandparent.right;
		
		else if(grandparent.right == parent)
			return grandparent.left;
		
		//This last case can only happen if there is an issue with node references
		else
			throw new IllegalStateException("Parent is not a child of its own parent.");
		
	}
	
	//This is a helper function that is called only on nodes with a missing child node or a leaf node.
	private TreeMapNode2<K,V> deleteNodeWithMaxOneChild(TreeMapNode2<K,V> node){
		//Node has only a left child
		if(node.left != null) {
			replaceParentsChild(node.parent, node, node.left);
			return node.left;
		}
		
		//Node has only a right child
		else if(node.right != null) {
			replaceParentsChild(node.parent, node, node.right);
			return node.right;
		}
		
		//Node has no children and is RED
		else if(node.colour == RED) {
			replaceParentsChild(node.parent, node, null);
			return null;
		}
		
		//Node has no children and is BLACK
		return node;
	}
	
	/**A helper function that updates the link of the nodes of a subtree after rotation/deletion. It assigns to the parent node a new child in the place of the old one.
	 * @param parent Subtree's parent node. If parent is null then the respective child node is the current root.
	 * @param oldChild Former child of the parent node.
	 * @param newChild New child of the parent node. 
	 * */
	private void replaceParentsChild(TreeMapNode2<K,V> parent, TreeMapNode2<K,V> oldChild, TreeMapNode2<K,V> newChild) {
		//If the oldChild was the root the it would have no parent (null) therefore the newChild will become the root
		if(parent == null)
			root = newChild;
		
		else if(parent.left == oldChild)
			parent.left = newChild;
		
		else if(parent.right == oldChild)
			parent.right = newChild;
		
		//This last case can only happen if there is an issue with node references
		else
			throw new IllegalStateException("Node 'oldChild' is not a child of its parent");
		
		//Link the 'newChild' node to its new parent node
		if(newChild != null)
			newChild.parent = parent;
	}
	
	/**Rotates a subtree to right. Let the following be:
	 * N - the root of the subtree
	 * L - N's left child
	 * LR - L's right child
	 * 
	 * Then:
	 * L becomes N's parent
	 * LR becomes N's left child
	 * N's former parent becomes L's new parent
	 * @param node The subtree's 'root' (can be the root of the whole tree but not always the case)
	 * */
	private void rotateRight(TreeMapNode2<K,V> node) {
		TreeMapNode2<K,V> parent = node.parent;
		TreeMapNode2<K,V> leftChild = node.left;
		
		node.left = leftChild.right;
		if(leftChild.right != null)
			leftChild.right.parent = node;
		
		leftChild.right = node;
		node.parent = leftChild;
		
		replaceParentsChild(parent, node, leftChild);
	}
	
	/**Rotates a subtree to left. Let the following be:
	 * N - the 'root' of the subtree
	 * R - N's right child
	 * RL - R's left child
	 * 
	 * Then:
	 * R becomes N's parent
	 * RL becomes N's right child
	 * N's former parent becomes R's new parent
	 * @param node The subtree's 'root' (can be the root of the whole tree but not always the case)
	 * */
	private void rotateLeft(TreeMapNode2<K,V> node) {
		TreeMapNode2<K,V> parent = node.parent;
		TreeMapNode2<K,V> rightChild = node.right;
		
		node.right = rightChild.left;
		if(rightChild.left != null)
			rightChild.left.parent = node;
		
		rightChild.left = node;
		node.parent = rightChild;
		
		replaceParentsChild(parent, node, rightChild);
	}
	
	private boolean isBlack(TreeMapNode2<K,V> node) {
		return node == null || node.colour == BLACK;
	}

	@Override
	public Iterator<V> iterator() {
		return new TreeMapIterator2<K,V>(root);
	}
	
	/**Prints the string representation of the tree.
	 * */
	public String toString() {
		TreeMapIterator2<K,V> it = (TreeMapIterator2<K,V>) iterator();
		String st = "";
		
		TreeMapNode2<K,V> currentNode = null;
		while(it.hasNext()) {
			currentNode = it.nextNode();
			st += currentNode.key + "=" + currentNode.value;
			if(it.hasNext())
				st += ", ";
		}

		return "{" + st + "}";
	}
	
}//end of class
