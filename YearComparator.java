package project5;

import java.util.Comparator;

/**
 * This class is a Comparator class for my year binary search tree.
 * 
 * @author richardzhu
 * @version 12/06/2020
 */
public class YearComparator implements Comparator<Meteorite>{

	/**
	 * This method is the implemented compare method for this class that compares the years of meteorites.
	 * 
	 * @param o1 The first meteorite to be compared 
	 * @param o2 The second meteorite to be compared
	 * @return 1 if year of o1 > year of o2, 0 if the years are equal and -1 if year of o1 < year of o2
	 */
	@Override
	public int compare(Meteorite o1, Meteorite o2) {
		//check if years are equal, use regular compare to method
		if(o1.getYear() == o2.getYear()) return o1.compareTo(o2);
		//return difference of years
		return o1.getYear() - o2.getYear();
	}

}