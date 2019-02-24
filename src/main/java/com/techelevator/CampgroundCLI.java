package com.techelevator;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.dao.CampgroundDAO;
import com.techelevator.dao.JDBCCampgroundDAO;
import com.techelevator.dao.JDBCParkDAO;
import com.techelevator.dao.JDBCReservationDAO;
import com.techelevator.dao.JDBCSiteDAO;
import com.techelevator.dao.ParkDAO;
import com.techelevator.dao.ReservationDAO;
import com.techelevator.dao.SiteDAO;
import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private static Scanner userInput = new Scanner(System.in);
	private Menu menu = new Menu(System.in, System.out);

	private ParkDAO parkData;
	private CampgroundDAO campData;
	private SiteDAO siteData;
	private ReservationDAO reserveData;

	// Main menu options
	private final static String VIEW_CAMPGROUNDS = "View Campgrounds";
	private final static String SEARCH_RESERVATIONS = "Search for Reservation";
	private final static String RETURN = "Return to Previous Screen";
	private final static String VIEW_ALL_PARKS = "View all Parks";
	private final static String[] PARK_OPTIONS = { VIEW_CAMPGROUNDS, SEARCH_RESERVATIONS, VIEW_ALL_PARKS };

	// Campground options
	private final static String SELECT_AVAILABLE_RESERVATION = "Select Available Reservation";
	private final static String[] CAMP_OPTIONS = { SELECT_AVAILABLE_RESERVATION, RETURN };

	public static void main(String[] args) throws SQLException {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
		dataSource.close();
		userInput.close();
	}

	public CampgroundCLI(DataSource datasource) {
		parkData = new JDBCParkDAO(datasource);
		campData = new JDBCCampgroundDAO(datasource);
		siteData = new JDBCSiteDAO(datasource);
		reserveData = new JDBCReservationDAO(datasource);
	}

	public void run() { // wellcome message

		System.out.println("+----------------------------------+");
		System.out.println("|      National Park Campground    |");
		System.out.println("|                by                |");
		System.out.println("|     Alexandra, Arthur, Ipsita    |");
		System.out.println("|              help from           |");
		System.out.println("|            Tom, Beth,            |");
		System.out.println("|     StackOverflow, W3Schools,    |");
		System.out.println("|             Quora and            |");
		System.out.println("|           Java Tutorial          |");
		System.out.println("+----------------------------------+");

		while (true) {
			printMainMenu(); // method calling
		}
	}

	// Display Main menu option - All Park Names
	private void printMainMenu() { // done
		printHeading("Select a Park for Further Details");
		List<Park> parkList = parkData.getAllParks();

		String[] parkNames = new String[parkList.size() + 1];
		int i = 0;
		for (Park p : parkList) {
			parkNames[i] = p.getName() + ", " + p.getLocation();
			i++;
		}
		parkNames[i] = "Quit";

		String choice = (String) menu.getChoiceFromOptions(parkNames);
		if (choice.equals("Quit"))
			System.exit(0);
		else {
			String[] parkNameAndState = choice.split(", ");
			printParkInfo(parkData.getParkByName(parkNameAndState[0]));
			// printParkInfo(parkData.getParkByState(parkState[0]));
		}
	}

	// Print the selected park's information
	private void printParkInfo(Park park) { // done
		while (true) {
			printHeading(park.getName() + " National Park");
			System.out.printf("%-16s %s%n", "Location:", park.getLocation());
			System.out.printf("%-16s %s%n", "Established:",
					park.getDayEstablished().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
			System.out.printf("%-16s %,d sq km%n", "Area:", park.getArea());
			System.out.printf("%-16s %,d%n", "Annual Visitors:", park.getVisitors());
			System.out.println();
			System.out.println(park.getDescription());

			String choice = (String) menu.getChoiceFromOptions(PARK_OPTIONS);

			if (choice.equals(VIEW_CAMPGROUNDS)) { // option 1
				printCampgroundInfo(park); // method calling

			} else if (choice.equals(SEARCH_RESERVATIONS)) { // option 2
				printReservationsForPark(park); // method calling

			} else if (choice.equals(VIEW_ALL_PARKS)) // option 3
				break;
		}
	}

	// When "View Campgrounds" option 1 is Selected
	private void printCampgroundInfo(Park park) // done
	{
		while (true) {
			List<Campground> campList = campData.getCampgroundsByParkID(park.getId());

			printHeading(park.getName() + " National Park Campgrounds");
			System.out.printf("%-32s %-10s %-10s %-8s%n", "Name", "Open", "Close", "Daily Fee");
			for (Campground camp : campList) {
				System.out.printf("%-32s %-10s %-10s $%-8s%n", camp.getName(), camp.getOpenFrom(), camp.getOpenTo(),
						camp.getDailyFee().toPlainString());
			}

			String choice = (String) menu.getChoiceFromOptions(CAMP_OPTIONS);

			if (choice.equals(RETURN))
				break;
			else if (choice.equals(SELECT_AVAILABLE_RESERVATION)) {
				selectCampgroundForReservation(park); // method calling
				break;
			}
		}
	}

	// When "Search for Reservation" option 2 is Selected
	private void printReservationsForPark(Park park) { // done
		List<Campground> campList = campData.getCampgroundsByParkID(park.getId());

		printHeading("Reservations for Campgrounds in " + park.getName() + " for the next 30 days");

		for (Campground camp : campList) { // iterate over the CampgroundsByParkID List

			System.out.println(camp.getName());

			List<Reservation> reserve = reserveData.getReservationsByStartDateForCampground(camp);
			if (reserve.isEmpty()) {
				System.out.println("No current reservations, all dates available.");
			}
			for (Reservation res : reserve) {
				System.out.printf("%-6d %-32s %-10s %-10s%n", res.getSiteId(), res.getName(),
						res.getFromDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
						res.getToDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
			}
		}
	}

	// When option 1 (inside "view campground") selected "Search for Available
	// Reservation"
	//
	private void selectCampgroundForReservation(Park park) {
		while (true) {
			List<Campground> campList = campData.getCampgroundsByParkID(park.getId()); // get and store all campgrounds
																						// details in campList

			printHeading(park.getName() + " National Park Campgrounds");
			System.out.printf("%-2s %-32s %-10s %-10s %-8s", "", "Name", "Open", "Close", "Daily Fee"); // header

			String[] campNames = new String[campList.size() + 1]; // new array of campNames = array of campList size +1
			int i = 0;
			for (Campground camp : campList) // iterate over the CampgroundsByParkID List
			{
				// get list of all campgrounds in the park
				campNames[i] = String.format("%-32s %-10s %-10s $%-8s", camp.getName(), camp.getOpenFrom(),
						camp.getOpenTo(), camp.getDailyFee().toPlainString());
				i++;
			}
			campNames[i] = "Cancel";

			int choice = menu.getIndexFromOptions(campNames);

			if (choice == campNames.length - 1)
				break;
			else {
				selectDatesForReservation(campList.get(choice));
				break;
			}
		}
	}
//	}

	private void selectDatesForReservation(Campground campground)
	// selectDatesForReservation?^
	{
		while (true) {
			LocalDate fromDate = promptForDate(true);
			LocalDate toDate = promptForDate(false);

			if (fromDate.isBefore(toDate)) {
				if (fromDate.isAfter(LocalDate.now())) {
					if (campground.isOpenForDates(fromDate, toDate)) {

						siteRequirements(campground, fromDate, toDate);

						break;
					} else
						System.out.println("Campsite is closed during selected period");
				} else
					System.out.println("Starting date must be after today");
			} else
				System.out.println("End date was before start date. Please try again");
		}
	}

	private void siteRequirements(Campground campground, LocalDate fromDate, LocalDate toDate) {
		boolean accessible = false;
		int max_rv_length = 0;
		boolean utilities = false;
		int max_occupancy = 0;
		{
			boolean stop = false;
			while (!stop) {
				try {
					String userInput = getUserInput(
							"Do you require an accessible camp site? Choose 1 for yes or 2 for no");
					if (Integer.parseInt(userInput) == 1) {
						accessible = true;
						stop = true;
					}
					else if (Integer.parseInt(userInput) == 2) {
						accessible = false;
						stop = true;
					} else {
						System.out.println("Please select a valid option \n");
					}
				} catch (Exception notAnInt) {
					System.out.println("Please select a valid option \n");
				}

			}
		}
		{
			boolean stop = false;
			while (!stop) {
				String userInput = getUserInput(
						"If you require space for an RV please enter the length of your RV in feet. If you do not have an RV, please enter \"0\"");
				try {
					max_rv_length = Integer.parseInt(userInput);
					stop = true;
				} catch (Exception notAnInt) {
					System.out.println("Please select a valid option \n");
				}

			}
		}
		{
			boolean stop = false;
			while (!stop) {
				try {
					String userInput = getUserInput("Do you require utilities? Choose 1 for yes or 2 for no");
					if (Integer.parseInt(userInput) == 1) {
						utilities = true;
						stop = true;
					}
					else if (Integer.parseInt(userInput) == 2) {
						utilities = false;
						stop = true;
					} else {
						System.out.println("Please select a valid option \n");
					}
				} catch (Exception notAnInt) {
					System.out.println("Please select a valid option \n");
				}

			}
		}
		{
			boolean stop = false;
			while (!stop) {
				String userInput = getUserInput(
						"Please enter the number of people who will be staying at your campsite. All children older than 18 months old count as a person");
				try {
					max_occupancy = Integer.parseInt(userInput);
					stop = true;
				} catch (Exception notAnInt) {
					System.out.println("Please select a valid option \n");
				}

			}
		}

		displayAvailableSites(campground, fromDate, toDate, accessible, max_rv_length, utilities, max_occupancy);

	}

	private void displayAvailableSites(Campground campground, LocalDate fromDate, LocalDate toDate, boolean accessible,
			int max_rv_length, boolean utilities, int max_occupancy) {
		while (true) {
			List<Site> availableSites = siteData.getSitesAvailableForDateRange(campground.getId(), fromDate, toDate,
					accessible, max_rv_length, utilities, max_occupancy);

			BigDecimal totalCost = campground.getDailyFee()
					.multiply(new BigDecimal(fromDate.until(toDate, ChronoUnit.DAYS) + 1));
			printHeading(campground.getName() + " - Available Configurations");
			System.out.println("Option \t Max Occup. \t Accessible \t Max RV Length \t Utility \t Cost");
			String[] options = new String[availableSites.size() + 1];
			int i = 0;
			for (Site site : availableSites) {
				options[i] = ("\t"+site.getMaxOccupancy() +"\t" +"\t" + booleanToYesNo(site.isAccessible()) +"\t" +"\t"
						+ intToNAorNumber(site.getMaxRVLength()) +"\t" +"\t" + booleanToYesNo(site.isUtilities()) +"\t" +"\t" + totalCost)
								.toString();
				i++;
			}
			options[i] = "Cancel";

			int choice = menu.getIndexFromOptions(options);

			if (choice == options.length - 1)
				break;
			else {
				getNameForReservation(availableSites.get(choice), fromDate, toDate);
				break;
			}
		}
	}

	private void getNameForReservation(Site site, LocalDate fromDate, LocalDate toDate) {
		while (true) {
			String resName = getUserInput("Please enter a name for the reservation");
			long returnedId;
			long siteID=site.getId();

			try {
				returnedId = reserveData.createReservation(siteID, resName, fromDate, toDate);
				System.out.println("The reservation has been made and the confirmation id is " + returnedId);
			} catch (InvalidKeyException e) {
				System.out.println("Something went wrong when trying to create the reservation");
			}
			break;
		}
	}

	private LocalDate promptForDate(boolean arrival) {
		LocalDate input = null;

		while (input == null) {
			try {
				input = LocalDate.parse(
						getUserInput("What is the " + ((arrival) ? "arrival" : "departure") + " date? mm/dd/yyyy"),
						DateTimeFormatter.ofPattern("MM/dd/yyyy").withLocale(Locale.US));
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use formate mm/dd/yyyy");
			}
		}
		return input;
	}

	private String booleanToYesNo(boolean input) {
		return (input) ? "Yes" : "No";
	}

	private String intToNAorNumber(int input) {
		return (input == 0) ? "N/A" : Integer.toString(input);
	}

	private void printHeading(String heading) {
		System.out.printf("%n%s%n", heading);
		for (int i = 0; i < heading.length(); i++)
			System.out.print('-');
		System.out.println();
	}

	private String getUserInput(String prompt) {
		System.out.print(prompt + " >>> ");
		return userInput.nextLine();
	}
}