package project5;

/**
 * This class represents a meteorite with all of its characteristics. The class provides a two parameter
 * constructor that takes in the name and id of the meteorite. It also contains setMass(), getMass(),
 * setYear(), getYear(), setLocation(), getLocation(). The class also implements the Comparale interface
 * and overrides compareTo(), equals(), and toString().
 * 
 * @author Richard Zhu
 * @version 12/06/2020
 */

public class Meteorite implements Comparable<Meteorite>{
	/**
	 * The name of the meteorite
	 */
	String name;
	/**
	 * The id of the meteorite
	 */
	int id;
	/**
	 * The mass of the meteorite
	 */
	private int mass;
	/**
	 * The year the meteorite landed
	 */
	private int year;
	/**
	 * The location the meteorite landed in
	 */
	private Location location;
	
	/**
	 * Constructor that validates and sets the name and id of this meteorite.
	 * 
	 * @param name The name of this meteorite
	 * @param id The id of this meteorite
	 * @throws IllegalArgumentException Exception is thrown when constructor is called with invalid parameters
	 */
	public Meteorite (String name, int id) throws IllegalArgumentException{
		//check if name and id are valid, if they are, set name and id variables
		if(!name.equals("") && id > 0) {
			this.name = name;
			this.id = id;
		}else {
			throw new IllegalArgumentException("Invalid name or id parameters.");
		}
	}
	
	/**
	 * Sets mass of this meteorite to a positive integer
	 * 
	 * @param mass A positive integer representing the mass of this meteorite
	 * @throws IllegalArgumentException Exception is thrown if mass is invalid
	 */
	public void setMass(int mass) throws IllegalArgumentException{
		//check if mass is valid, if it is, set mass variable to mass
		if(mass > 0) {
			this.mass = mass;
		}else {
			throw new IllegalArgumentException("Mass cannot be less than or equal to 0.");
		}
	}
	
	/**
	 * Returns mass of this meteorite
	 * 
	 * @return Mass of this meteorite
	 */
	public int getMass() {
		return this.mass;
	}
	
	/**
	 * Sets year of this meteorite to a positive integer smaller than the current year
	 * 
	 * @param year A positive integer representing the year of this meteorite
	 * @throws IllegalArgumentException Exception is thrown if year is invalid
	 */
	public void setYear(int year) throws IllegalArgumentException{
		//check if year is valid, if it is, set year variable to year
		if(year > 0 && year < 2020) {
			this.year = year;
		}else {
			throw new IllegalArgumentException("Invalid year.");
		}
	}
	
	/**
	 * Returns year of this meteorite
	 * 
	 * @return Year of this meteorite
	 */
	public int getYear() {
		return this.year;
	}
	
	/**
	 * Sets location of this meteorite to a Location oject
	 * 
	 * @param loc A Location object representing the location of this meteorite
	 */
	public void setLocation(Location loc){
		this.location = loc;
	}
	
	/**
	 * Returns location of this meteorite
	 * 
	 * @return Location of this meteorite
	 */
	public Location getLocation() {
		return this.location;
	}

	/**
	 * Compares two meteorite objects based on their alphanumeric names.
	 * 
	 * @param o A Meteorite object that this object is being compared to
	 * @return An integer that is positive if this object is greater than the other meteorite,
	 * negative if this object is less than the other meteorite, and 0 if they are equal
	 */
	@Override
	public int compareTo(Meteorite o) {
		//check if this Meteorite's name variable is equal to o's name variable
		if(this.name.equals(o.name)) {
			//return the difference between this Meteorite's id variable and o's id variable
			return this.id - o.id;
		}
		//return the result of compareTo using this Meteorite's name and o's name
		return this.name.compareToIgnoreCase(o.name);
	}
	
	/**
	 * Returns a boolean value that is true if this object is equal to the another object and
	 * false if they are not equal.
	 * 
	 * @param obj A meteorite object that this object is being compared to to see if they are equal
	 * @return True if this meteorite and the other meteorite are equal and false if they are not equal
	 */
	@Override
	public boolean equals(Object obj) {
		//check if this Meteorite's name and id are equal to obj's name and id
		if(!(obj instanceof Meteorite)) return false;
		if(this.name.equalsIgnoreCase(((Meteorite)obj).name) && this.id == ((Meteorite)obj).id) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a String object representing a meteorite with name, id, year, mass, latitude, and longitude.
	 * 
	 * @return String object representing a meteorite
	 */
	@Override
	public String toString() {
		String sy = "", sm = "", sla = "", slo = "";
		//check if Meteorite is missing year, mass, or location values
		//change year and mass to strings if they are not equal to zero
		if (year != 0) sy = Integer.toString(year);
		if (mass != 0) sm = Integer.toString(mass);
		if(location != null) {
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			//return formatted string of Meteorite values
			return String.format("%-20s %4d %4s %6s %10.5f %10.5f", name, id, sy, sm, lat, lon);
		}
		//return formatted string of Meteorite values with altered latitude and longitude values
		return String.format("%-20s %4d %4s %6s %10s %10s", name, id, sy, sm, sla, slo);
	}
}

