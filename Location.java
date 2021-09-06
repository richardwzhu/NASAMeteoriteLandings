package project5;

/**
 * This class represents the location (latitude, longitude). It provides a two parameter constructor
 * that validates and sets the latitude and longitude, a getDistance(), getLatitude(), getLongitude(),
 * and equals() method.
 * 
 * @author Richard Zhu
 * @version 12/06/2020
 */

public class Location {
	/**
	 * The latitude of the location
	 */
	private double latitude;
	/**
	 * The longitude of the location
	 */
	private double longitude;
	
	/**
	 * Constructor method that takes in a latitude in the range [-90.0, 90.0] and a longitude
	 * in the raneg [-180, 1800. 
	 * 
	 * @param latitude Latitude for object being created, must be between [-90.0, 90.0]
	 * @param longitude Longitude for object being created, must be between [-180.0, 180.0]
	 * @throws IllegalArgumentException Throws exception when constructor is called with invalid parameters
	 */
	public Location(double latitude, double longitude) throws IllegalArgumentException{
		//check if latitude and longitude are within bounds
		if((latitude >= -90 && latitude <= 90) && (longitude >= -180 && longitude <= 180)) {
			//set data fields to input parameters
			this.latitude = latitude;
			this.longitude = longitude;
		} else {
			throw new IllegalArgumentException("Invalid latitude or longitude parameters.");
		}
	}
	
	/**
	 * Method that computes and returns distance between this location and the one provided as 
	 * the parameter.
	 * 
	 * @param loc The Location object that this object is being computed with to find distance
	 * @return Returns a double that is the distance between this object and loc.
	 * @throws IllegalArgumentException Throws exception when called with null parameter
	 */
	public double getDistance(Location loc) throws IllegalArgumentException{
		//check if parameter is null
		if(loc == null) {
			throw new IllegalArgumentException("Enter a valid location parameter.");
		}
		//return the distance between this object's location and loc's location
		return haversine(this.latitude, this.longitude, loc.latitude, loc.longitude);
	}
	
	/**
	 * Method that calculates the shortest distance between two points on a sphere using their 
	 * latitudes and longitudes measured along the surface.
	 * 
	 * @author Joanna Kuklowska
	 * @param lat1 The latitude of the first location
	 * @param lon1 The longitude of the first location
	 * @param lat2 The latitude of the second location
	 * @param lon2 The longitude of the second location
	 * @return The distance between the location and second location on the surface of a sphere
	 */
	public static double haversine(double lat1, double lon1, double lat2, double lon2){
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
	
		// convert to radians
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
	
		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2) +
				   Math.pow(Math.sin(dLon / 2), 2) *
				   Math.cos(lat1) *
				   Math.cos(lat2);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;
	}
	
	/**
	 * Returns the latitude of this object.
	 * 
	 * @return The latitude of this object
	 */
	public double getLatitude() {
		return this.latitude;
	}
	
	/**
	 * Returns the longitude of this object.
	 * 
	 * @return The longitude of this object
	 */
	public double getLongitude() {
		return this.longitude;
	}
	
	/**
	 * Method that returns true if this location and another location are equal within 0.00001 and false
	 * if they are not equal.
	 * 
	 * @param obj The location this location is being compared to
	 * @return True of false whether or not the the two locations are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		//check if this object's latitude and longitude values are within 0.00001 of obj's latitude and longitude values
		if((Math.abs(this.latitude - ((Location)obj).latitude) < 0.00001) && (Math.abs(this.longitude - ((Location)obj).longitude) < 0.00001)){
			return true;
		}
		return false;
	}
}
