package project5;

import java.util.*;

/**
 * This class is a for a binary search tree. It stores BSTNodes and is linked using
 * left and right pointers. It contains contains(), isEmpty(), iterator(), getRange(),
 * first(), last(), equals(), toString(), and toArray() methods.
 * 
 * @author richardzhu
 * @version 12/06/2020
 *
 * @param <T> generic data type that extends the Comparable class.
 */
public class BST < T extends Comparable <T> > {

    private BSTNode root;   //reference to the root node of the tree 
    private int size;       //number of values stored in this tree 
    private Comparator<T> comparator;   //comparator object to overwrite the 
                                //natural ordering of the elements 
	private boolean found;  //helper variable used by the remove methods
    private boolean added ; //helper variable used by the add method 

    /**
	 * Constructs a new, empty tree, sorted according to the natural ordering of its elements.
	 * 
	 * @author Joanna Kuklowska
	 */
    public BST () {
        root = null; 
        size = 0;  
        comparator = null; 
    }

    /**
	 * Constructs a new, empty tree, sorted according to the specified comparator.
	 * 
	 *  @author Joanna Kuklowska
	 */
    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    
	/**
	 * Adds the specified element to this tree if it is not already present. 
	 * If this tree already contains the element, the call leaves the 
     * tree unchanged and returns false.
     * 
     * @author Joanna Kuklowska
	 * @param data element to be added to this tree 
     * @return true if this tree did not already contain the specified element 
     * @throws NullPointerException if the specified element is null  
	 */
    public boolean add ( T data ) { 
         added = false; 
         if (data == null) return added; 
         //replace root with the reference to the tree after the new 
         //value is added
         root = add (data, root);
         //update the size and return the status accordingly 
         if (added) size++; 
         return added; 
    }
    
    /*
	 * Actual recursive implementation of add. 
     *
     * This function returns a reference to the subtree in which 
     * the new value was added. 
	 *
	 * @author Joanna Kuklowska
     * @param data element to be added to this tree 
     * @param node node at which the recursive call is made 
	 */
    private BSTNode add (T data, BSTNode node ) {
        if (node == null) {
            added = true; 
            return new BSTNode(data); 
        }
        //decide how comparisons should be done 
        int comp = 0 ;
        if (comparator == null ) //use natural ordering of the elements 
            comp = node.data.compareTo(data); 
        else                     //use the comparator 
            comp = comparator.compare(node.data, data ) ;
        
        //find the location to add the new value 
        if (comp > 0 ) { //add to the left subtree 
            node.left = add(data, node.left); 
        }
        else if (comp < 0 ) { //add to the right subtree
            node.right = add(data, node.right); 
        }
        else { //duplicate found, do not add 
            added = false; 
            return node; 
        }
        return node; 
    }

    /**
	 * Removes the specified element from this tree if it is present. 
	 * Returns true if this tree contained the element (or equivalently, 
     * if this tree changed as a result of the call). 
     * (This tree will not contain the element once the call returns.)
     * @author Joanna Kuklowska
	 * @param target object to be removed from this tree, if present
     * @return true if this set contained the specified element 
     * @throws NullPointerException if the specified element is null  
	 */
	public boolean remove(T target){
        //replace root with a reference to the tree after target was removed 
		root = recRemove(target, root);
        //update the size and return the status accordingly 
		if (found) size--; 
		return found;
	}

	/*
	 * Actual recursive implementation of remove method: find the node to remove.
     *
	 * This function recursively finds and eventually removes the node with the target element 
     * and returns the reference to the modified tree to the caller. 
     * 
     * @author Joanna Kuklowska
	 * @param target object to be removed from this tree, if present
     * @param node node at which the recursive call is made 
	 */
	private BSTNode recRemove(T target, BSTNode node){
		if (node == null)  { //value not found 
			found = false;
            return node; 
        }
        
        //decide how comparisons should be done 
        int comp = 0 ;
        if (comparator == null ) //use natural ordering of the elements 
            comp = target.compareTo(node.data); 
        else                     //use the comparator 
            comp = comparator.compare(target, node.data ) ;

        
		if (comp < 0)       // target might be in a left subtree 
			node.left = recRemove(target, node.left);
		else if (comp > 0)  // target might be in a right subtree 
			node.right = recRemove(target, node.right );
		else {          // target found, now remove it 
			node = removeNode(node);
			found = true;
		}
		return node;
	}

	/*
	 * Actual recursive implementation of remove method: perform the removal.
	 *
	 * @author Joanna Kuklowska
	 * @param target the item to be removed from this tree
	 * @return a reference to the node itself, or to the modified subtree
	 */
	private BSTNode removeNode(BSTNode node){
		T data;
		if (node.left == null)   //handle the leaf and one child node with right subtree 
			return node.right ; 
		else if (node.right  == null)  //handle one child node with left subtree 
			return node.left;
		else {                   //handle nodes with two children 
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			return node;
		}
	}

	/*
	 * Returns the information held in the rightmost node of subtree
	 *
	 * @author Joanna Kuklowska
	 * @param subtree root of the subtree within which to search for the rightmost node
	 * @return returns data stored in the rightmost node of subtree
	 */
	private T getPredecessor(BSTNode subtree){
		if (subtree==null) //this should not happen 
            throw new NullPointerException("getPredecessor called with an empty subtree");
		BSTNode temp = subtree;
		while (temp.right  != null)
			temp = temp.right ;
		return temp.data;
	}

	/**
	 * Returns the number of elements in this tree.
	 * 
	 * @author Joanna Kuklowska
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}

	/**
	 * To string method for the tree
	 * 
	 * @author Joanna Kuklowska
	 * @return to string representation for the tree
	 */
    public String toStringTree( ) {
        StringBuffer sb = new StringBuffer(); 
        toStringTree(sb, root, 0);
        return sb.toString();
    }
    
    //uses preorder traversal to display the tree 
    //WARNING: will not work if the data.toString returns more than one line 
    private void toStringTree( StringBuffer sb, BSTNode node, int level ) {
        //display the node 
        if (level > 0 ) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (node == null) {
            sb.append( "->\n"); 
            return;
        }
        else {
            sb.append( node.data + "\n"); 
        }

        //display the left subtree 
        toStringTree(sb, node.left, level+1); 
        //display the right subtree 
        toStringTree(sb, node.right, level+1); 
    }

    /**
     * Returns true if this tree contains the specified element. More formally, returns 
     * true if and only if this tree contains an element e such that Objects.equals(o, e).
     * This operation should be O(H).
     * 
     * @param o the object being searched for
     * @return true if this contains o and false otherwise
     * @throws NullPointerException if the specified object cannot be compared with the
			elements currently in the set
     * @throws ClassCastException if the specified element is null and this tree uses
			natural ordering, or its comparator does not permit null elements
     */
    @SuppressWarnings("unchecked")
	public boolean contains(Object o) throws NullPointerException, ClassCastException{
    	//check if null is null and if the comparator is null
    	if(o == null && comparator == null) throw new NullPointerException("Object is null");
    	//call to recursive method
    	return find((T)o, this.root);
    }
    
    /**
     * Recursive method for contains that searches for an object
     * 
     * @param data the object to be searched for
     * @param node the node that is used for the recursive call
     * @return true if this contains data and false otherwise
     */
    public boolean find(T data, BSTNode node) {
    	//check if node is null
    	if(node == null) return false;
    	//check if the node's data is equal to data
    	if(data.equals(node.data))return true; 
    	//if the data is less than the node's data, recursive call using node.left
    	else if(data.compareTo(node.data) < 0) return find(data, node.left);
    	//otherwise, recursive call using node.right
    	else return find(data, node.right);
    }
    
    /**
     * Returns true if this tree contains no elements. This operation should be O(1).
     * 
     * @return true if this is empty and false otherwise
     */
    public boolean isEmpty() {
    	//reuturn true if size is 0 and false otherwise
    	return size == 0;
    }
    
    /**
     * Returns an iterator over the elements in this tree in ascending order. 
     * This operation should be O(N).
     * 
     * @return an iterator over the elements in this tree in ascending order
     */
    public Iterator<T> iterator(){
    	//return an Itr starting at the root of this tree
    	return new Itr(this.root);
    }
    
    /**
     * Iterator class that iterates through the binary search tree.
     * 
     * @author richardzhu
     * @version 12/06/2020
     */
    private class Itr implements Iterator<T> {
    	/**
    	 * MyStack variable
    	 */
    	private MyStack<BSTNode> stack;
    	/**
    	 * BSTNode variable
    	 */
    	private BSTNode current = root;
    	
    	/**
		 * Method that advances this iterator
		 * 
		 * @return The next element in the iterator
		 */
    	@Override
		public T next() {
    		//pop the top node in the stack
			BSTNode node = stack.pop();
			//get the data of the element
			T data = node.data;
			//check if the right pointer of the node is null
			if(node.right != null) {
				//advance node to the right pointer
				node = node.right;
				//while the node is not null
				while(node != null) {
					//push the node onto the stack
					stack.push(node);
					//advance the node to the left pointer 
					//this goes all the way down to the smallest element
					//and places that element on the top of the stack
					node = node.left;
				}
			}
			//return the node's data
			return data;
		}
    	
    	/**
		 * Method that checks if the iterator has a next element
		 * 
		 * @return true if there is a next element and false otherwise
		 */
    	@Override
		public boolean hasNext() {
    		//return if the stack is not empty
			if(stack.isEmpty()) return false;
			else return true;
		}
    	
    	/**
    	 * Constructor that takes in the root node
    	 * 
    	 * @param root the root node
    	 */
    	public Itr(BSTNode root) {
    		//instantiate stack
    		stack = new MyStack<BSTNode>();
    		//lloop through the collection and push elements onto the stack
    		while(current != null) {
    			stack.push(current);
    			current = current.left;
    		}
    	}
    }
    
    /**
     * Returns a collection whose elements range from fromElement, inclusive, 
     * to toElement, inclusive. The returned collection/list is backed by this 
     * tree, so changes in the returned list are reflected in this tree, and 
     * vice-versa (i.e., the two structures share elements. The returned 
     * collection should be organized according to the natural ordering of 
     * the elements (i.e., it should be sorted). This operation should be O(M) 
     * where M is the number of elements in the returned list.

     * 
     * @param fromElement the lower end element of the range
     * @param toElement the upper end element of the range
     * @return an arraylist containing the elements in the collection from fromElement to toElement
     * @throws NullPointerException if fromElement or toElement is null
     * @throws IllegalArgumentException if fromElement is greater than toElement
     */
    public ArrayList<T> getRange​(T fromElement, T toElement) throws NullPointerException, IllegalArgumentException{
    	//check if from element or to element are null
    	if(fromElement == null || toElement == null) throw new NullPointerException("Element is null");
    	//check if from element is greater than to element
    	if(comparator != null) {
    		if(comparator.compare(fromElement, toElement) > 0) throw new IllegalArgumentException("fromElement larger than toElement");
    	}else { 
    		if(fromElement.compareTo(toElement) > 0) throw new IllegalArgumentException("fromElement larger than toElement");
    	}
    	//recursive call to get the range
    	return range(fromElement, toElement, root);
    }
    
	/**
	 * Recursive method for range that retrieves all elements in this tree between
	 * fromElement to toElement
	 * 
	 * @param fromElement the lower end element of the range
	 * @param toElement the upper end element of the range
	 * @param node the current node pointer
	 * @return an arraylist containing elements in the collection between fromElement and toElement
	 */
    public ArrayList<T> range(T fromElement, T toElement, BSTNode node){
    	//create a new arraylist
    	ArrayList<T> list = new ArrayList<T>();
    	//base case if node is null
    	if(node == null) return list;
    	//check if there is a comparator
    	if(comparator != null) {
    		//if the node is between from element and to element
	    	if(comparator.compare(node.data, fromElement) >= 0 && comparator.compare(node.data, toElement) <= 0) {
	    		//recursive call to left side of node
	    		ArrayList<T> temp = range(fromElement, toElement, node.left);
	    		//recursive call to right side of node
	    		ArrayList<T> temp2 = range(fromElement, toElement, node.right);
	    		//add elements from left side to list
	    		for(T t : temp) {
	    			list.add(t);
	    		}
	    		//add data from node
	    		list.add(node.data);
	    		//add elements form right side to list
	    		for(T t : temp2) {
	    			list.add(t);
	    		}
	    	//if node is greater than given range
	    	}else if(comparator.compare(node.data, fromElement) > 0 && comparator.compare(node.data, toElement) > 0) {
	    		//recursive call to left side of node
	    		ArrayList<T> temp = range(fromElement, toElement, node.left);
	    		//add elements from left side to list
	    		for(T t : temp) {
	    			list.add(t);
	    		}
	    	//if node is less than given range
	    	}else if(comparator.compare(node.data, fromElement) < 0 && comparator.compare(node.data, toElement) < 0) {
	    		//recursive call to right side of node
	    		ArrayList<T> temp = range(fromElement, toElement, node.right);
	    		//add elements form right side to list
	    		for(T t : temp) {
	    			list.add(t);
	    		}
	    	}else {
	    		//add data of node to list
	    		list.add(node.data);
	    	}
	    //if there isn't a comparator	
    	}else {
    		//if the node is between from element and to element
    		if(node.data.compareTo(fromElement) >= 0 && node.data.compareTo(toElement) <= 0) {
    			//recursive call to left side of node
    			ArrayList<T> temp = range(fromElement, toElement, node.left);
    			//recursive call to right side of node
    			ArrayList<T> temp2 = range(fromElement, toElement, node.right);
    			//add elements from left side to list
    			for(T t : temp) {
        			list.add(t);
        		}
    			//add data from node
    			list.add(node.data);
    			//add elements form right side to list
    			for(T t : temp2) {
        			list.add(t);
        		}
    		//if node is greater than given range
    		}else if(node.data.compareTo(fromElement) > 0 && node.data.compareTo(toElement) > 0) {
    			//recursive call to left side of node
    			ArrayList<T> temp = range(fromElement, toElement, node.left);
    			//add elements from left side to list
    			for(T t : temp) {
        			list.add(t);
        		}
    		//if node is less than given range
    		}else if(node.data.compareTo(fromElement) < 0 && node.data.compareTo(toElement) < 0) {
    			//recursive call to right side of node
    			ArrayList<T> temp = range(fromElement, toElement, node.right);
    			//add elements form right side to list
    			for(T t : temp) {
        			list.add(t);
        		}
        	}else {
        		//add data of node to list
        		list.add(node.data);
        	}
    	}
    	//return list
    	return list;
    }

    /**
     * Returns the first (lowest) element currently in this tree. This operation should be O(H).
     * 
     * @return the lowest element in the tree
     * @throws NoSuchElementException if this tree is empty
     */
    public T first() throws NoSuchElementException{
    	//check if tree is empty
    	if(this.isEmpty()) throw new NoSuchElementException("This tree is empty");
    	//check if the left pointer of the node is null
    	if(root.left == null) return root.data;
    	BSTNode current = root;
    	//iterate through the left pointers of node until null
    	while(current.left != null) current = current.left;
    	//return the pointer's data
    	return current.data;
    }
    
    /**
     * Returns the last (highest) element currently in this tree. This operation should be O(H).
     * 
     * @return the last (highest) element currently in this tree
     * @throws NoSuchElementException if this tree is empty
     */
    public T last() throws NoSuchElementException{
    	//check if tree is empty
    	if(this.isEmpty()) throw new NoSuchElementException("This tree is empty");
    	//check if the right pointer of the node is null
    	if(root.right == null) return root.data;
    	BSTNode current = root;
    	//iterate through the right pointers of node until null
    	while(current.right != null) current = current.right;
    	//return the pointer's data
    	return current.data;
    }

    /**
     * Compares the specified object with this tree for equality. Returns true if the given 
     * object is also a tree, the two trees have the same size, and the inorder traversal of the
     * two trees returns the same nodes in the same order. This operation should be O(N).
     * 
     * @param obj the object to be compared to
     * @return true if the trees are equal and false otherwise
     */
    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object obj) {
    	//check if obj and root are null
    	if(obj == null && root == null) return true;
    	//check if obj is null
    	if(obj == null) return false;
    	//check if the sizes of the trees are equal
    	if(this.size != ((BST<T>)obj).size) return false;
    	//iterator for this tree
    	Iterator<T> iterator1 = this.iterator();
    	//iterator for obj
    	Iterator<T> iterator2 = ((BST<T>)obj).iterator();
    	//loop through both trees
    	while(iterator1.hasNext() && iterator2.hasNext()) {
    		//compare if each element is equal
    		if(iterator1.next().compareTo(iterator2.next()) != 0) return false;
    	}  	
    	return true;
    }
    
    /**
     * Returns a string representation of this tree. The string representation consists of a list
     * of the tree's elements in the order they are returned by its iterator (inorder traversal),
     * enclosed in square brackets ("[]"). Adjacent elements are separated by the characters ", "
     * (comma and space). Elements are converted to strings as by String.valueOf(Object).
     * This operation should be O(N).
     * 
     * @return a string representation of this collection
     */
    @Override
    public String toString() {
    	String str = "[";
    	Iterator<T> iterator = this.iterator();
    	//iterate through this collection
    	while(iterator.hasNext()) {
    		//add each element of this collection to a string
    		str += String.valueOf(iterator.next()) + ", ";
    	}
    	//cut off ", " if necessary
    	if(str.length() > 1 && str.substring(str.length() - 2).equals(", ")) str = str.substring(0, str.length() - 2);
    	return str + "]";
    }
    
    /**
     * This function returns an array containing all the elements returned by this tree's iterator,
     * in the same order, stored in consecutive elements of the array, starting with index 0. The
     * length of the returned array is equal to the number of elements returned by the iterator.
     * This operation should be O(N).
     * 
     * @return an array, whose runtime component type is Object, containing all of the element
     */
    public Object[] toArray() {
    	//create array with size elements
    	Object[] list = new Object[size];
    	Iterator<T> iterator = this.iterator();
    	int index = 0;
    	//iterate over this collection
    	while(iterator.hasNext()) {
    		//add each element to array
    		list[index] = iterator.next();
    		//advance index
    		index++;
    	}
    	//return array
    	return list;
    }
    
    /* 
     * Node class for this BST 
     * 
     * @author Joanna Kuklowska
     */ 
    private class BSTNode implements Comparable < BSTNode > {

        T data;
        BSTNode  left;
        BSTNode  right;

        public BSTNode ( T data ) {
            this.data = data;
        }

        public BSTNode (T data, BSTNode left, BSTNode right ) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int compareTo ( BSTNode other ) {
            if (BST.this.comparator == null )
                return this.data.compareTo ( other.data );
            else 
                return comparator.compare(this.data, other.data); 
        }
    }
    
    //Stack ADT
    private interface Stack<E> {
	  	/**
    	* Add an element to the top of this stack
    	* @param item to be added to this stack
    	* throws IllegalArgumentException if `item == null`
    	*/
    	public void push ( E item ) ;

    	/**
    	* Remove and return the element from the top of this stack
    	* @return the element from the top of this stack or null if this stack is empty
    	*/
    	public E pop () ;

    	/** Return the element from the top of this stack.
    	* @return  the element from the top of this stack or null if this stack is empty
    	*/
    	public E top () ;

    	/**
    	* Determines if this stack is equal to `obj`.
    	* @obj an Object that is compared to this stack for equality
    	* @return true if this stack is equal to `obj` (same elements in the same order)
    	*         false, otherwise
    	*/
    	public boolean equals(Object obj);

    	/**
    	* Returns a string representation of this stack. The string is constructed by
    	* concatenating all elements of this stack separated by a comma and a single space.
    	* The bottom of the stack should be the leftmost element and the top of the stack
    	* should be the rightmost element. There should be no comma after the last element.
    	* @return a string representation of this stack.
    	*/
    	public String toString () ;
    }
    
    /**
     * This class provides an implementation for a stack using a singly linked linked reference 
     * structure. It implements an a default constructor that instantiates an empty stack, a push
     * method that adds an element to the top of the stack, a pop method that returns and removes the 
     * element from the top of the stack, a top method that returns the element from the top of the 
     * stack, an equals method that compares two stack objects, and a toString method that returns
     * a string representation of the stack.
     * 
     * @author Richard Zhu
     * @version 10/29/2020
     */
    private class MyStack<E> implements Stack<E> {
    	/**
    	 * The head of the linked list reference structure
    	 */
    	private Node head;
    	
    	/**
    	 * Default constructor that creates a MyStack object. It sets the head pointer for the linked
    	 * list reference structure to null
    	 */
    	public MyStack() {
    		head = null;
    	}

    	/**
    	 * Adds an element to the top of the stack and throws an IllegalArgumentException if item is null.
    	 * 
    	 * @param item that is to be added to this stack
    	 * throws IllegalArgumentException if the item to be added is null
    	 */
    	@Override
    	public void push(E item) {
    		//throws an exception if 'item' is null
    		if(item == null) throw new IllegalArgumentException();
    		Node e = new Node(item);
    		//checks if the stack is empty
    		if (head != null) e.next = head;
    		head = e;
    	}

    	/**
    	 * Removes and returns the element at the top of the stack and null if the stack is empty
    	 * 
    	 * @return the element of the top of the stack and null if the stack is empty
    	 */
    	@Override
    	public E pop() {
    		//check if stack is empty
    		if(head != null){
    			//store and increment the head pointer
    			Node removed  = head;
    			head = head.next;
    			return removed.data;
    		}
    		return null;
    	}

    	/**
    	 * Returns the element at the top of the stack or null if the stack is empty
    	 * 
    	 * @return the element at the top of the stack or null if the stack is empty
    	 */
    	@Override
    	public E top() {
    		//returns the the element at the top of the stack of null if the stack is empty
    		return (head != null) ? head.data : null;
    	}
    	
    	/**
    	 * Compares this stack to equal and determines if they are equal. Returns true if they are equal
    	 * and false if not.
    	 * 
    	 * @param obj the Object that this stack if compared to for equality
    	 * @return true if this stack is equal to 'obj' and false if not 
    	 */
    	@SuppressWarnings("unchecked")
		@Override
    	public boolean equals(Object obj) {
    		//check if obj is this object
    		if(obj == this) return true;
    		//check if obj is null
    		if(obj == null) return false;
    		//checks if obj is an instance of MyStack
    		if(!(obj instanceof MyStack)) return false;
    		//typecast obj to a MyStack object to iterate through
    		MyStack<E> s = (MyStack<E>)obj;
    		//check if the two stacks are both empty
    		if(head == null && s.head == null) return true;
    		Node curr1 = head;
    		Node curr2 = s.head;
    		//iterate through the two stacks
    		while(curr1 != null && curr2 != null) {
    			//check if each element is the same
    			if(!curr1.data.equals(curr2.data)) return false;
    			//increment the current pointers for both stacks
    			curr1 = curr1.next;
    			curr2 = curr2.next;
    		}
    		//return whether or not both stacks are empty at the end of the while loop
    		//if they are both empty then both have the same amount of elements and the same elements
    		return (curr1 == null && curr2 == null);
    	}
    	
    	/**
    	 * Returns a string representation of this stack. The string is created by concatenating all
    	 * the elements in this stack separated by a ", ". The bottom of the stack is the leftmost item and 
    	 * the top of the stack is the rightmost element. If the stack is empty then "" is returned.
    	 * 
    	 * @return the string representation of this stack
    	 */
    	@Override
    	public String toString() {
    		String result = "";
    		if(head == null) return result;
    		Node current = head;
    		result += current.data;
    		while(current.next != null) {
    			current = current.next;
    			result = current.data + ", " + result;
    		}
    		return result;
    	}
    	
    	public boolean isEmpty() {
    		return head == null;
    	}
    	
    	/**
    	 * This class describes a node in a linkedlist
    	 * 
    	 * @author Richard Zhu
    	 * @version 10/29/20
    	 */
    	private class Node{
    		/**
    		 * stores data of node
    		 */
    		private E data;
    		/**
    		 * stores next pointer
    		 */
    		private Node next;

    		/**
    		 * Constructor that takes in data of the node
    		 * 
    		 * @param data of the node
    		 */
    		Node ( E data ) {
    			this.data = data;
    		}
    		
    		/**
    		 * Returns a string representation of the data in the node
    		 * 
    		 * @return the string representation of the data in the node
    		 */
    		@Override
    		public String toString() {
    			return data.toString();
    		}
    	}
    }
}