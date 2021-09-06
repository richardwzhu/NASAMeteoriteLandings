package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class is the actual program containing the main method. It is responsible for opening 
 * and reading the data file, obtaining user input, performing some data validation and handling 
 * all errors that may occur.
 * 
 * @author Richard Zhu
 * @version 12/06/2020
 */

public class FallenStars {
	/**
	 * The main() method of this program. 
	 * @param args array of Strings provided on the command line when the program is started; 
	 * the first string should be the name of the input file containing the list of meteorites. 
	 */
	public static void main(String[] args) {	
		//checks if an argument is given
		if (args.length == 0 ) {
			//prints an error message if there is no argument inputted
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		}
		
		//verify that command line argument contains a name of an existing file 
		File data = new File(args[0]); 
		if (!data.exists()){
			System.err.println("Error: the file "+data.getAbsolutePath()+" does not exist.\n");
			System.exit(1);
		}
		if (!data.canRead()){
			System.err.println("Error: the file "+data.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//open the file for reading 
		Scanner inData = null; 
		try {
			inData = new Scanner (data ) ;
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file "+data.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		
		//read the content of the file and save the data in a list of meteorites
		MeteoriteData list = new MeteoriteData();
		String line = inData.nextLine();
		ArrayList<String> splitLine = null;
		String name = null;
		String id = null;
		String mass = null;
		String year = null;
		String reclat = null;
		String reclong = null;
		Meteorite current = null;
		
		while(inData.hasNextLine()) {
			try {
				line = inData.nextLine();
				//call splitCSVLine to convert string of data to arraylist
				splitLine = splitCSVLine(line);
				//access elements of arraylist to get name, id, mass, year, lat, and long
				name = splitLine.get(0);
				//Integer.parseInt used to convert string to int
				id = splitLine.get(1);
				mass = splitLine.get(4);
				year = splitLine.get(6);
				//Double.parseDouble used to convert string to double
				reclat = splitLine.get(7);
				reclong = splitLine.get(8);
			}
			catch (Exception e) {
				//ignore this exception and skip to the next line 
				continue; 
			}
			try {
				if(!(name.length() == 0 || id.length() == 0)) {
					//create new Meteorite and set mass, location, and year
					current = new Meteorite (name, Integer.parseInt(id));
					if(mass.length() != 0) current.setMass(Integer.parseInt(mass));
					if(new Location(Double.parseDouble(reclat), Double.parseDouble(reclong)) != null) current.setLocation(new Location(Double.parseDouble(reclat), Double.parseDouble(reclong)));
					if(year.length() != 0) current.setYear(Integer.parseInt(year.substring(6,10)));
					//add Meteorite to MeteoriteList
					list.add(current); 
				}
			}
			catch (Exception e) {
				//ignore this exception and skip to the next line 
				continue;
			}
		}
		
		
		//interactive mode: 
		System.out.println("Search the database by using one of the following queries.");
		System.out.println("To search for meteorite nearest to a given goe-location, enter location LATITUDE LONGITUDE");
		System.out.println("To search for meteorites that fell in a given year, enter year YEAR");
		System.out.println("To search for meteorites with weights MASS +/- 10 grams, enter mass MASS");
		System.out.println("To finish the program, enter quit");
		
		String type = "";	
		Scanner scan = new Scanner(System.in);;
		
		do {
			//print out query message
			System.out.println("Enter your search query.");
			//instantiate scanner and use System.in to retrieve user input
			type = scan.nextLine();
			
			//declare meteorite and meteorite data variables
			Meteorite m = null;
			MeteoriteData mL = null;
			
			//check if user input is quit
			if(!type.equalsIgnoreCase("quit")) {
				//try catch loop
				try {
					//split up user input into array, each element is indicated by a space
					String[] split = type.split(" ");
					
					//check if first element is location and if the array contains 3 elements
					if(split[0].equalsIgnoreCase("location") && split.length == 3) {
						//create new location using lat and long and get get meteorite with closest location
						m = list.getByLocation(new Location(Double.parseDouble(split[1]), Double.parseDouble(split[2])));
						//check if the meteorite is null
						if(m == null) System.out.println("This is not a valid location. Try again.");
						//otherwise print the meteorite
						else System.out.println(m.toString());
					}else if(split[0].equalsIgnoreCase("year") && split.length ==  2) {
						//get meteorites with the year from user input
						mL = list.getByYear(Integer.parseInt(split[1]));
						//if there are no meteorites in the specified year then print message
						if(mL == null) System.out.println("This is not a valid year. Try again.");
						//otherise print out meteorites
						else {
							Iterator<Meteorite> itr = mL.iterator();
							while(itr.hasNext()) {
								System.out.println(itr.next());
							}
						}
					}else if(split[0].equalsIgnoreCase("mass") && split.length == 2) {
						//get meteorites with mass from user input within 10 grams
						mL = list.getByMass(Integer.parseInt(split[1]), 10);
						//check if there are no meteorites within the specified mass, print message
						if(mL == null) System.out.println("No matches found. Try again.");
						//otherwise print out meteorites
						else {
							Iterator<Meteorite> itr = mL.iterator();
							while(itr.hasNext()) {
								System.out.println(itr.next());
							}
						}
					//if false for all if statements above then the format of user input is wrong	
					}else {
						//throw illegal argument excpetion
						throw new IllegalArgumentException("Entry does not follow the format specified above.");
					}
				//catch IllegalArgumentExceptions	
				}catch(IllegalArgumentException ex) {
					//print error message
					System.err.println("This is not a valid query. Try again.");
					//continue
					continue;
				}
			}
		//continue loop while type is not "quit"
		}while (!type.equalsIgnoreCase("quit"));
		
		//close the scanner
		scan.close();
	} 
	
	
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas).
	 * 
	 * @author Joanna Kuklowska
	 * @param textLine  a line of text from a CSV file to be parsed
	 * @return an ArrayList object containing all individual entries found on that line;
	 *  any missing entries are indicated as empty strings; null is returned if the textline
	 *  passed to this function is null itself.
	 */
	public static ArrayList<String> splitCSVLine(String textLine){

		if (textLine == null ) return null;

		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;

		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);

			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {

				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false;
				}
				else {
					insideQuotes = true;
					insideEntry = true;
				}
			}
			else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
					// add it to the current entry
					nextWord.append( nextChar );
				}
				else { // skip all spaces between entries
					continue;
				}
			}
			else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar);
				}
				else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			}
			else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			}

		}
		// add the last word ( assuming not empty )
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}
}
