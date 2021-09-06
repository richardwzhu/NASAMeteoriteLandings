package project5;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class stores all the Meteorite objects and uses the BST<T> class. However, 
 * it does not inherit from it, rather it has a 'has-a' relationship and not a "is-a"
 * relationship.
 * 
 * @author richardzhu
 * @version 12/06/2020
 */
public class MeteoriteData {
	/**
	 * The binary search tree storing the data
	 */
	private BST<Meteorite> bst;
	/**
	 * The binary search tree that stores data according to mass
	 */
	private BST<Meteorite> massTree;
	/**
	 * The binary search tree that stores data according to year
	 */
	private BST<Meteorite> yearTree;
	
	/**
	 * Default constructor that creates an empty MeteoriteData object.
	 */
	public MeteoriteData() {
		//instantiates all three binary search trees
		bst = new BST<Meteorite>();
		massTree = new BST<Meteorite>(new MassComparator());
		yearTree = new BST<Meteorite>(new YearComparator());
	}
	
	/**
	 * Adds the given Meteorite object to the collection and returns true if an equal 
	 * Meteorite is not already present. If the collection already contains and object
	 * equal to m, the method returns false. The method throws an instance of NullPOinterException
	 * if m is null. This method performs in O(H) where H is the height of the tree representing 
	 * the collection.
	 * 
	 * @param m The meteorite to be added
	 * @return true if a Meteorite is added to the collection
	 */
	public boolean add(Meteorite m) {
		//check if m is null
		if(m == null) throw new NullPointerException("Meteorite is null");
		//add m to mass tree
		massTree.add(m);
		
		//add m to year tree
		yearTree.add(m);
		
		//add m to regular tree
		return bst.add(m);
	}
	
	/**
	 * This method compares the collection to obj. The two collections are equal if they are both
	 * MeteoriteData objects and if they contain exactly the same elements. This method performs in 
	 * O(N) where N is the number of Meteorite objects in this collection.
	 * 
	 * @param obj the object to be compared to.
	 * @return true if they are equal false otherwise
	 */
	public boolean equals(Object obj) {
		//check if obj is an instance of meteorite data
		if(!(obj instanceof MeteoriteData)) return false;
		//return if the two collectinos are equal
		return this.bst.equals(((MeteoriteData)obj).bst);
	}
	
	/**
	 * This method should return a collection of all Meteorite objects with mass within delta grams of the specified 
	 * mass. Both values are specified in grams. The returned collection should be organized based on the mass
	 * from smallest to largest, and for meteorite objects with equal mass according to the natural ordering of the 
	 * elements (i.e., dictated by the comparaTo method defined in the Mereorite class). If either value is less than zero, 
	 * the method should throw an instance of IllegalArgumentException with an appropriate message. 
	 * If there are no elements in the collection that match the given criteria, this method should return null.
	 * This method should perform in O(K+H) in which K is the number of Meteorite objects in the returned collection and 
	 * H is the height of the tree representing this collection (not O(N) where N is the total number of all Meteorite objects).
	 * 
	 * @param mass The mass being searched for
	 * @param delta The deviation of the mass
	 * @return A collection of Meteorite objects with mass within delta grams of the specified mass.
	 */
	public MeteoriteData getByMass(int mass, int delta) {
		//check if mass and delta are valid values
		if(mass < 0 || delta < 0) {
			throw new IllegalArgumentException("Mass or delta cannot be less than 0");
		}
		
		//the minimum values for a meteorite
		Meteorite minMet = new Meteorite("\u0000", 1);
		//the maximum values for a meteorite
		Meteorite maxMet = new Meteorite("\uffff", Integer.MAX_VALUE);
		//set the mass of the min met
		minMet.setMass(mass - delta);
		//set the mass of the max met
		maxMet.setMass(mass + delta);
		
		//get the range of mets within minMet and maxMet
		ArrayList<Meteorite> list = massTree.getRange​(minMet, maxMet);
		MeteoriteData data = new MeteoriteData();
		
		//check if the list has nothing
		if(list.size() == 0) return null;
		//copy over arraylist values into a meteorite data object
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i));
		}
	
		//return meteorite data object
		return data;
	}
	
	/**
	 * This method should return a Meteorite object whose landing site is nearest to the specified location loc. 
	 * If loc is null, the method should throw an instance of IllegalArgumentException with an appropriate message.
	 * If the current collection is empty, this method should return null.This method should perform in O(N) in which
	 * N is the total number of Meteorite objects stored in this collection.
	 * 
	 * @param loc the location to be searched for
	 * @return the meteorite in the collection closest to loc
	 */
	public Meteorite getByLocation(Location loc) {
		//check if location parameter is null
		if(loc == null){
			throw new IllegalArgumentException("This is not a location.");
		}
		//check if the collection is empty
		if(bst.isEmpty()) return null;
		Meteorite closest = null;
		double distance = Integer.MAX_VALUE;
		Iterator<Meteorite> itr = bst.iterator();
		//loop through all the Meteorite in this list
		while(itr.hasNext()) {
			try {
				Meteorite m = itr.next();
				//check if m is closer to loc than Meteorite stored in closest
				//if it is, set closest to m
				double d = m.getLocation().getDistance(loc);
				if(d < distance) {
					distance = d;
					closest = m;
				}
			} catch(Exception e) {
				
			}
		}
		//return meteorite
		return closest;
	}
	
	/**
	 * This method should return a collection of all Meteorite objects that landed on Earth on the year specified. 
	 * The returned collection should be organized based on the year from earliest to most recent, and for meteorite 
	 * objects with the same year according to the natural ordering of the elements (i.e., dictated by the comparaTo 
	 * method defined in the Mereorite class). If the value of year is less than zero, the method should throw an instance 
	 * of IllegalArgumentException with an appropriate message. This method should perform in O(K+H) in which K is the number
	 * of Meteorite objects in the returned collection and H is the height of the tree representing this collection (not O(N) 
	 * where N is the total number of all Meteorite objects).
	 * 
	 * @param year the year to be searched for
	 * @return the collection of meteorites with the specified year
	 */
	public MeteoriteData getByYear(int year) { 
		//check if year is valid
		if(year <= 0 || year >= 2020) {
			throw new IllegalArgumentException("Year cannot be less than 0");
		}
		
		//the minimum value for a meteorite
		Meteorite minMet = new Meteorite("\u0000", 1);
		//the maxmimum value for a meteorite
		Meteorite maxMet = new Meteorite("\uffff", Integer.MAX_VALUE);
		//set the year for the min met
		minMet.setYear(year); 
		//set the year for the max met
		maxMet.setYear(year);
		
		//get arraylist of meteorites with year
		ArrayList<Meteorite> list = yearTree.getRange​(minMet, maxMet);
		MeteoriteData data = new MeteoriteData();
		
		//check if list is empty
		if(list.size() == 0) return null;
		//copy over meteorites from list to data
		for(int i = 0; i < list.size(); i++) {
			data.add(list.get(i));
		}
		
		//return meteorite data object with meteorites with year
		return data;
	}
	
	/**
	 * This method should return an iterator over all Meteorite objects in this collection in order specified by natural 
	 * ordering of Meteorite objects.
	 * 
	 * @return an iterator over all Meteorite objects in the collection
	 */
	public Iterator<Meteorite> iterator(){
		//return iterator over bst
		return (Iterator<Meteorite>)bst.iterator();
	}
	
	/**
	 * This method should remove an object equal to the given Meteorite object m from this collection and return true 
	 * such an object was present. If m is not in this collection, the method should return false. The method should 
	 * throw an instance of NullPointerException if m is null. This method should perform in O(H) in which H is the 
	 * height of the tree representing this collection.
	 * 
	 * @param the meteorite to be removed
	 * @return true if the meteorites are equal false otherwise
	 */
	public boolean remove(Meteorite m) {
		//check if m is null
		if(m == null) throw new NullPointerException("Meteorite is null");
		//return if m is removed from the bst
		return bst.remove(m);
	}
	
	/**
	 * Getter method to retrieve collection of meteorites
	 * 
	 * @return collection of meteorites
	 */
	public BST<Meteorite> getBST(){
		//return collection
		return bst;
	}
}
