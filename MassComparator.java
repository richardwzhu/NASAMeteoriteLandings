package project5;

import java.util.Comparator;

/**
 * This class is a Comparator class for my mass binary search tree.
 * 
 * @author richardzhu
 * @version 12/06/2020
 */
public class MassComparator implements Comparator<Meteorite>{

	/**
	 * This method is the implemented compare method for this class that compares the mass of meteorites.
	 * 
	 * @param o1 The first meteorite to be compared 
	 * @param o2 The second meteorite to be compared
	 * @return 1 if mass of o1 > mass of o2, 0 if the masses are equal and -1 if mass of o1 < mass of o2
	 */
	@Override
	public int compare(Meteorite o1, Meteorite o2) {
		//check if masses are equal, use regular compare to method
		if(o1.getMass() == o2.getMass()) return o1.compareTo(o2);
		//return difference of masses
		return o1.getMass() - o2.getMass();
	}

}
