import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class ValleyBikeSim {

	/**
	 * Station ArrayLists and Objects:
	 */
	private ArrayList<Station> stationList;
	public static Station newStation;
	private ArrayList<User> userList;

	/**
	 * ValleyBike constructor
	 */
	public ValleyBikeSim() {
		stationList = new ArrayList<Station>();
		userList = new ArrayList<User>();
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * HELPER METHODS
	 * 
	 * 
	 * 
	 * 
	 */

	/**
	 * Prints the menu for the Valley Bike Simulator.
	 */
	public static void printMenu() {
		System.out.println("Please choose from one of the following menu options:\n"
				+ "0. Quit Program.\n1. View station list.\n2. Add station list.\n3. Save station list.\n"
				+ "4. Record ride.\n5. Resolve ride data.\n6. Equalize stations.");
	}

	/**
	 * Covert "1" and "0" read in from the .csv file to true and false respectively
	 * 
	 * @param s- String argument to be converted to boolean
	 * @return true if csv kiosk value is "O", false if csv kiosk value is "1"
	 */
	private static boolean toBool(String s) {
		if (s.equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Covert true and false kiosk values in the ArrayList containing station
	 * objects to 1 and 0 in the .csv file
	 * 
	 * @param x- boolean to be converted to int
	 * @return 0 if kiosk value is false, 1 if kiosk value is true
	 */
	private static int toInt(boolean x) {
		if (x == true) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Covert string to Date
	 * 
	 * @throws ParseException
	 * @return date
	 */
	private static Date changeToDate(String s) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
		return date;
	}

	/**
	 * Compare station ID values
	 * 
	 * @param s1, s2 - Station objects
	 * @return difference between station objects ids
	 */
	class idCompare implements Comparator<Station> {
		@Override
		public int compare(Station s1, Station s2) {
			return s1.id - s2.id;
		}
	}

	/**
	 * Check if station object in ArrayList<Station> contains a inputID
	 * 
	 * @param list    - ArrayList containing station objects
	 * @param station inputID
	 * @return - false if ArrayList<Station> does not contain inputID
	 */
	public boolean checkID(ArrayList<Station> list, int i) {
		for (Station station : list) {
			if (i == station.id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Reads in csv data and stores each station object as an element of
	 * ArrayList<Station>
	 */
	public void read() {
		File path = new File("data-files/station-data.csv");

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] details = line.split(",");
				String name = details[1];
				int bikes = Integer.valueOf(details[2]);
				int peds = Integer.valueOf(details[3]);
				int avDoc = Integer.valueOf(details[4]);
				int mReq = Integer.valueOf(details[5]);
				int cap = Integer.valueOf(details[6]);
				String address = details[8];
				boolean kiosk = toBool(details[7]);

				int id = Integer.valueOf(details[0]);
				stationList.add(new Station(id, name, bikes, peds, avDoc, mReq, cap, kiosk, address));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * FUNTIONALITY IMPLEMENTATION
	 * 
	 * 
	 * 
	 * 
	 */

	/**
	 * Displays data values for the stations in the csv file in a tabbed format
	 */
	public void printStation() {
		// print the column names
		System.out.println("ID\tBikes\tPedelec AvDocs\tMainReq\tCap\tKiosk\tName - Address");

		// make sure the stations are sorted by their IDs
		Collections.sort(stationList, new idCompare());

		// loop through all stations that exist in station list
		for (Station stat : stationList) {

			// get all the station's attributes and save them in the formatted way we need
			// them to be (almost like tables)
			String longName = stat.id + "\t" + stat.bikes + "\t" + stat.pedelecs + "\t" + stat.availableDocks + "\t"
					+ stat.mReq + "\t" + stat.capacity + "\t" + stat.kiosk + "\t" + stat.name + " - " + stat.address;

			// print it!
			System.out.println(longName);
		}

	}

	/**
	 * Prompt the user for the required information and add the station to the
	 * simulator.
	 */
	public void addStation() {
		// New Station object
		newStation = new Station(0, null, 0, 0, 0, 0, 0, false, null);
		System.out.println("Please enter the following information:");
		while (true) {
			// New station ID:
			System.out.println("Enter Station ID: ");
			Scanner userInput = new Scanner(System.in);
			int inputID = userInput.nextInt();
			try {
				if (checkID(stationList, inputID)) {
					System.out.println("A station with that ID already exists. Please enter another ID.");
					continue;
				} else {
					newStation.setID(inputID);
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
			}

			// Name of new station:
			System.out.println("Station Name: ");
			String inputName = userInput.next();
			newStation.setName(inputName);

			// Capacity of new station:
			System.out.println("What is the capacity for the new station (0-25):");
			int inputCapacity = userInput.nextInt();
			try {
				if (inputCapacity > 25 || inputCapacity < 0) {
					System.out.println("Invalid capacity. Restart.");
					continue;
				} else {
					newStation.setCapacity(inputCapacity);
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Restart.");
			}

			// No. of bikes at the new station
			System.out.println(
					"Enter the number of total bikes at this station (range: 0-" + newStation.capacity + "): ");
			int inputBikes = userInput.nextInt();
			try {

				if (inputBikes > newStation.capacity) {
					System.out.println(
							"The number of bikes specified exceeds the capacity of the station. Please start over.\n");
					continue;
				} else if (inputBikes < 0) {
					System.out.println("Invalid number of bikes entered. Please start over.\n");
					continue;
				} else {
					newStation.setBikes(inputBikes);
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
			}

			// No. of pedelecs at the new station
			System.out.println(
					"Enter the number of pedelecs (range: 0-" + (newStation.capacity - newStation.bikes) + "): ");
			int inputPedelecs = userInput.nextInt();
			try {
				if (inputPedelecs > newStation.capacity - newStation.bikes) {
					System.out.println(
							"The number of pedelecs specified exceeds the available docks. Please start over.\n");
					continue;
				} else if (inputPedelecs < 0) {
					System.out.println("Invalid number of pedelecs entered. Please start over.\n");
					continue;
				} else {
					newStation.setPedelecs(inputPedelecs);
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
			}

			// Calculate available docks based on capacity and total vehicles at new station
			newStation.setAvailableDocks(newStation.capacity - (newStation.bikes + newStation.pedelecs));

			// Check whether the new station has a kiosk
			System.out.println("\nDoes the station have a kiosk? (true/false)");
			String inputKiosk = userInput.next();
			if (inputKiosk.equals("true") || inputKiosk.equals("T") || inputKiosk.equals("True")
					|| inputKiosk.equals("t")) {
				newStation.setHasKiosk(true);
			} else if (inputKiosk.equals("false") || inputKiosk.equals("F") || inputKiosk.equals("False")
					|| inputKiosk.equals("f")) {
				newStation.setHasKiosk(false);
			} else {
				System.out.println("\nInvalid input. Please start over.");
				continue;
			}

			// Assume no maintenance request requires
			System.out.println("If you have maintenance request enter 1 or else enter 0: ");
			int inputmReq = userInput.nextInt();
			newStation.setMaintenanceRequests(inputmReq);

			// New station maintenance request
			System.out.println("Enter the address of the station: ");
			String inputAddress = userInput.next();
			newStation.setAddress(inputAddress);

			// Print out details of newly added station
			System.out.println("This is the new station you will be adding to the list:\n" + "\nID: " + newStation.id
					+ "\nName: " + newStation.name + "\nCapacity: " + newStation.capacity + "\nNumber of Bikes: "
					+ newStation.bikes + "\nNumber of Pedelecs: " + newStation.pedelecs
					+ "\nNumber of Available Docks: " + newStation.availableDocks + "\nNumber of Maintenance Requests: "
					+ newStation.mReq + "\nHas a kiosk: " + newStation.kiosk + "\nAddress: " + newStation.address
					+ "\n");

			// Add newStation to existing list of stations
			stationList.add(newStation);

			break;
		}
		return;

	}

	/**
	 * Read in a ride data file that contains all the rides for one day of service.
	 * Calculates average ride time
	 * 
	 * @param fileName - name of csv ride data file
	 * @throws ParseException
	 */
	public void resolveRide(String fileName) throws ParseException {
		String fileData = "data-files/" + fileName + ".csv";
		File path = new File(fileData);

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] details = line.split(",");

				int userId = Integer.valueOf(details[0]);
				int startStationId = Integer.valueOf(details[1]);
				int endStationId = Integer.valueOf(details[2]);
				Date startTime = changeToDate(details[3]);
				Date endTime = changeToDate(details[4]);

				userList.add(new User(userId, startStationId, endStationId, startTime, endTime));

			}

			// Calculate total ride time
			int totalTime = 0;
			for (User userRide : userList) {
				totalTime += userRide.duration;
			}
			// Calculates average ride time
			int averageDuration = totalTime / userList.size();
			System.out.println("The ride list contains " + userList.size() + " rides and the average ride time is "
					+ averageDuration + " minutes.\n");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Saves new additions of stations that were recently added through addStation
	 */
	public void save() {
		try {
			// start a file writer with our stations csv file
			FileWriter writer = new FileWriter("data-files/station-data.csv");

			// initialize a string builder
			StringBuilder builder2 = new StringBuilder();
			// make string of all the column names separated by commas
			String ColumnNamesList = "ID,Name,Bikes,Pedelec,AvDocs,MainReq,Cap,Kiosk,Address";
			// add new line to the string then write it to the file
			builder2.append(ColumnNamesList + "\n");
			writer.write(builder2.toString());

			// loop through all stations
			for (Station stat : stationList) {
				// for every station. make a new string builder
				// and add all its attribute to the builder separated
				// by commas
				StringBuilder builder = new StringBuilder();
				builder.append(stat.id + ",");
				builder.append(stat.name + ",");
				builder.append(stat.bikes + ",");
				builder.append(stat.pedelecs + ",");
				builder.append(stat.availableDocks + ",");
				builder.append(stat.mReq + ",");
				builder.append(stat.capacity + ",");
				builder.append(toInt(stat.kiosk) + ",");
				builder.append(stat.address);

				builder.append('\n');

				// write it to the file
				writer.write(builder.toString());

			}

			// then close the filewriter
			writer.close();

		} catch (IOException e) {
			// catch any errors or exceptions
			e.printStackTrace();
		}

	}

	/**
	 * This method records bikes or pedelecs user use for a ride from one station to
	 * another. It is the answer to number 4.
	 */
	public void recorduser() {
		// STARTING STATION
		System.out.println("Provide station ID of your starting station.");

		// get input
		Scanner userInput2 = new Scanner(System.in);
		int inputStartStation = userInput2.nextInt();

		// check that station already exists
		if (!checkID(stationList, inputStartStation)) {
			System.out.println("Please input a valid start station ID.");
			return;
		}

		// ENDING STATION
		System.out.println("Provide station ID of your destination station.");

		Scanner userInput3 = new Scanner(System.in);
		int inputEndStation = userInput3.nextInt();

		if (!checkID(stationList, inputEndStation)) {
			System.out.println("Please input a valid end station ID.");
			return;
		}

		// check if user is using bikes or pedelecs
		System.out.println("Bike or pedelec?");
		Scanner userInput4 = new Scanner(System.in);
		String bikePed = userInput4.next();

		// if the answer is bike
		if (bikePed.equals("bike")) {
			// loop through the station list
			for (Station s : stationList) {
				// find the station that matches the user input
				if (s.id == inputStartStation) {
					if (s.bikes > 0) {
						// station no longer has bike -->subtract
						s.bikes = s.bikes - 1;
					} else {
						// if it doesn't have any bikes, user can't take any bikes from that station
						System.out.println("This station has no bikes.");
						return;
					}

				}

				// also find station that matches the user input for the end station
				if (s.id == inputEndStation) {
					// make sure that there's available space for the bike being used
					if (s.availableDocks > 0) {
						// if there is, increment bike count
						s.bikes = s.bikes + 1;
					} else {
						System.out.println("No available spots!");
						return;
					}
				}
			}
		}

		// same thing for the previous if statement except now we check for pedelecs
		if (bikePed.equals("ped") || bikePed.equals("pedelec")) {
			for (Station s : stationList) {
				if (s.id == inputStartStation) {
					if (s.pedelecs > 0) {
						s.pedelecs = s.pedelecs - 1;
					} else {
						System.out.println("This station has no pedelecs.");
						return;
					}

				}

				if (s.id == inputEndStation) {
					if (s.availableDocks > 0) {
						s.pedelecs = s.pedelecs + 1;
					} else {
						System.out.println("No available spots!");
						return;
					}
				}
			}
		}

		System.out.println("Ride successfully recorded.\n");
	}

	public void Equalizer() {
		int maxCap = 0;
		for (Station stat : stationList) {
			maxCap = maxCap + stat.capacity; //// increments the capacity of each station that we have for accurate
												//// count of total capacity
		}

		int bikes = 0;
		for (Station stat : stationList) {
			bikes = bikes + stat.bikes; // increments the number of bikes that we have for accurate counting
		}

		int peds = 0;
		for (Station stat : stationList) {
			peds = peds + stat.pedelecs; // increments the number of pedelecs by station for accurate count of total
											// pedelecs
		}
		

		int noBikes = 0; // initializes the number of bikes after equalization to 0
		int noPeds = 0; /// initializes the number of pedelecs after equalization to 0

		for (Station stat : stationList) {
			
			int stationPeds = stat.pedelecs; // gets the current number of pedelecs at that station
			int stationBikes = stat.bikes; // gets the current number of bikes at the station
			int stationCap = stat.capacity; // gets the capacity of the current station you are looking at

			int eqBikes = Math.round((stationCap * bikes) / maxCap); // sets a proportioned number of bikes for specific
																		// station
			noBikes = eqBikes + noBikes; // increments the total number of bikes after equalization at each station
			stat.setBikes(eqBikes); // sets the new number of bikes according to equalization
			

			int eqPeds = Math.round((stationCap * peds) / maxCap);
			noPeds = eqPeds + noPeds; // increments the total number of pedelecs after equalization at each station
			stat.setPedelecs(eqPeds); // sets the new number of bikes according to equalization
			

		}

		int missingPeds = Math.abs(peds - noPeds); // the number of extra or missing pedelics after equalization
		int missingBikes = Math.abs(bikes - noBikes); // the number of extra or missing bikes after equalization


		if (missingBikes > 0) {// checks if there are missing or extra bikes for redistribution
			int i = 0;
			while (i < missingBikes) {
				for (Station stat : stationList) {

					if ((stat.capacity - stat.availableDocks) > 0) { // checks for room in the station
						int eqBikes = stat.bikes;
						
						stationList.get(i).setBikes(eqBikes + 1);

					}
					i++;
				}
			}
		}

		if (missingPeds > 0) { // checks if there are missing or extra pedelecs for redistribution
			int i = 0;
			while (i < missingPeds) {
				for (Station stat : stationList) {

					if ((stat.capacity - stat.availableDocks) > 0) { // checks for room in the station
						int eqPeds = stat.pedelecs;

						stationList.get(i).setPedelecs(eqPeds + 1);

					}
					i++;
				}
			}

		}
		System.out.println("Equalization Complete.");
	}

	public static void main(String[] args) {
		// ValleyBike simulation
		ValleyBikeSim mySim = new ValleyBikeSim();
		// Reads in data
		mySim.read();

		System.out.println("Welcome to the ValleyBike Simulator.");
		Scanner scanner = new Scanner(System.in);

		try {
			while (true) {
				printMenu();
				System.out.println("\nPlease enter a number (0-6): ");

				int answer = scanner.nextInt();

				if (answer == 0) {
					System.out.println("\nThank you for using Valley Bike Simulator!");
					break;
				} else if (answer == 1) {
					mySim.printStation();
				} else if (answer == 2) {
					mySim.addStation();
				} else if (answer == 3) {
					mySim.save();
				} else if (answer == 4) {
					mySim.recorduser();
				} else if (answer == 5) {
					System.out.println("\nEnter the file name in data-files directory:");
					String file = scanner.next();
					mySim.resolveRide(file);

				} else if (answer == 6) {
					mySim.Equalizer();

				} else {
					System.out.println("\nInvalid input, please select a number within the 0-6 range.\n");
				}

			}
			scanner.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
