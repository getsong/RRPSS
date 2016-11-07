package rrpss;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;

public class Main {

	public static void main(String[] args) {
		
		Restaurant restaurant;
		final String DIVIDER = "-----------------------------------------------------";

		try {

			// resume restaurant
			// read from serialized file the object restaurant
			restaurant = (Restaurant) DatabaseManager.readRestaurantObject("restaurant.dat");
			Scanner intScanner = new Scanner(System.in);
			Scanner stringScanner = new Scanner(System.in);

			// programming from this line onward
			// ...
			println("*** Welcome Back ***");
			println("Enter System Time: " + RestaurantCal.getCurrentDate(Calendar.getInstance()).getString());
			println(DIVIDER);

			// Update restaurant by asking whether current date and session
			//restaurant.updateReservation();

			boolean exit = false;
			// main program
			do {
				println("Main Menu:");
				println("1. Reservation");
				println("2. Order");
				println("3. Menu Modification");
				println("4. Staff");
				println("5. Revenue report");
				println("6. Save");
				println("7. Save and Exit");
				print("Enter your option: ");
				int choice = intScanner.nextInt();
				println(DIVIDER);
				
				int staffId;

				switch (choice) {
				case 1:
					boolean backReservation = false;
					do {
						println("Main Menu > Reservation: ");
						println("0. Back");
						println("1. Check Available Table");
						println("2. New reservation");
						println("3. Check/Remove reservation");
						println("4. Save");
						println("5. Save and Exit");
						print("Enter your option: ");
						choice = intScanner.nextInt();
						println(DIVIDER);

						// temporary storage
						String name;
						int pax;
						RestaurantCal reservedDate;
						ArrayList<Reservation> tempReservationList;
						int tempIndex;

						switch (choice) {

						case 0:
							backReservation = true;
							break;
						case 1:
							println("Main Menu > Reservation > Check Available Table");
							print("Table size: ");
							pax = intScanner.nextInt();
							println("Date of Reservation");
							reservedDate = inputDateAndSession(stringScanner);
						
							if (restaurant.isTablesAvailable(reservedDate, pax)) {
								restaurant.printTablesAvailable(reservedDate, pax);
							} else {
								println("Tables for " + pax + "persons on " + reservedDate.getString()
										+ " are not available.");
							}
							println(DIVIDER);
							break;
						case 2:
							println("Main Menu > Reservation > New reservation");
							print("Name: ");
							name = stringScanner.nextLine();
							print("Table size: ");
							pax = intScanner.nextInt();
							println("Date of Reservation");
							reservedDate = inputDateAndTime(stringScanner);
							print("Contact: ");
							String contact = stringScanner.nextLine();
							
							// Check if table is available
							if (restaurant.isTablesAvailable(reservedDate, pax)) {
								restaurant.printTablesAvailable(reservedDate, pax);
								print("Choose table: ");
								int tableIdChosen = intScanner.nextInt();
								Reservation reservation = new Reservation(reservedDate, pax, name, contact,
										tableIdChosen);
								restaurant.getTable(tableIdChosen).addReservationList(reservation);
								println("Reservation of Table " + tableIdChosen + " on " + reservedDate.getString()
										+ " is confirmed.");
							} else {
								println("Tables for " + pax + "persons on " + reservedDate.getString()
										+ " are not available.");
							}
							println(DIVIDER);
							break;
						case 3:
							boolean backCheckReservation = false;
							do {
								println("Main Menu > Reservation > Check/Remove reservation: ");
								println("0. Back");
								println("1. By name");
								println("2. By date");
								println("3. Save");
								println("4. Save and Exit");
								print("Enter your option: ");
								choice = intScanner.nextInt();
								println(DIVIDER);

								tempReservationList = new ArrayList<Reservation>();
								switch (choice) {
								case 0:
									// Back
									backCheckReservation = true;
									break;
								case 1:
									// Check/Remove Reservation By Name
									println("Main Menu > Reservation > Check/Remove reservation > By name");
									// Check reservation by name
									print("Name: ");
									name = stringScanner.nextLine();
									// update restaurant
									restaurant.updateReservation();
									for (Table table : restaurant.getTableList()) {
										tempReservationList.addAll(table.viewReservation(name));
									}
									tempIndex = 1;
									for (Reservation reservation : tempReservationList) {
										println(tempIndex + ".");
										reservation.printReservationDetail();
										tempIndex++;
									}
								case 2:
									// Check/Remove Reservation By Date
									if (choice == 2) {
										println("Main Menu > Reservation > Check/Remove reservation > By date");
										// Check reservation by date
										println("Date of Reservation");
										reservedDate = inputDateAndSession(stringScanner);
										// update restaurant
										restaurant.updateReservation();
										for (Table table : restaurant.getTableList()) {
											tempReservationList.addAll(table.viewReservation(reservedDate));
										}
										tempIndex = 1;
										for (Reservation reservation : tempReservationList) {
											println(tempIndex + ".");
											reservation.printReservationDetail();
											tempIndex++;
										}
									}
									println(DIVIDER);

									// show option to remove reservation
									boolean exitReservationRemove = false;
									do {
										println("Select reservation to remove or press 0 to exit");
										choice = intScanner.nextInt();
										println(DIVIDER);
										switch (choice) {
										case 0:
											exitReservationRemove = true;
											break;

										default:
											if (choice > 0 && choice <= tempReservationList.size()) {
												// user select reservation
												Reservation selectedReservation = tempReservationList.get(choice - 1);
												restaurant.getTable(selectedReservation.getTableId())
														.getReservationList().remove(selectedReservation);
												println("The following reservation is removed.");
												selectedReservation.printReservationDetail();
												println(DIVIDER);

											} else {
												// user select invalid input
											}
											break;
										}
									} while (!exitReservationRemove);
									break;
								case 3:
									// save restaurant state
									saveRestaurant(restaurant);
									break;
								case 4:
									saveRestaurant(restaurant);
									backCheckReservation = true;
									backReservation = true;
									exit = true;
									break;
								default:
									break;
								}
							} while (!backCheckReservation);
							break;
						case 4:
							// save restaurant state
							saveRestaurant(restaurant);
							break;
						case 5:
							// save restaurant state
							saveRestaurant(restaurant);
							// exit
							backReservation = true;
							exit = true;
							break;
						default:
							break;
						}

					} while (!backReservation);

					break;
				case 2:
					// order
					boolean backToMenuFromOrder = false;
					int itemIndexInMenu, itemIndexInOrder, tableNumber;
					MenuItem menuItem;
					do {
						println(DIVIDER);
						println("Main Menu > Order: ");
						println("0. Back");
						println("1. New Order");
						println("2. View Order");
						println("3. Add Item to Order");
						println("4. Remove Item from Order");
						println("5. Remove Order");
						println("6. Print Order Invoice");
						println("7. Save");
						println("8. Save and Exit");
						print("Enter your option: ");
						choice = intScanner.nextInt();
						println(DIVIDER);

						//temporary storage
						RestaurantCal dateForRevenue;
						int tableNum;
						ArrayList<MenuItem> tempMenuItemList;
						
						switch (choice) {
						case 0:
							// Back
							backToMenuFromOrder = true;
							break;
						case 1:
							// New Order
							ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();
							itemIndexInMenu=0;
							println("Enter table number:");
							tableNumber = intScanner.nextInt();
							println("Enter staff ID:");
							staffId = intScanner.nextInt();
							println("Enter MenuItem index (enter 0 to exit):");
							itemIndexInMenu = intScanner.nextInt();
							while (itemIndexInMenu > 0){
								if (itemIndexInMenu > 0){
									menuItem = restaurant.getMenu().getMenuItem(itemIndexInMenu-1);
									menuItemList.add(menuItem);
								}
								println("Enter MenuItem index (enter 0 to exit):");
								itemIndexInMenu = intScanner.nextInt();
							}
							tempMenuItemList=menuItemList;
							restaurant.addOrder(tableNumber, menuItemList, staffId);
							break;
						case 2:
							// View Order
							println("Main Menu > Order > View Order");
							println("Enter table number:");
							tableNumber = intScanner.nextInt();
							restaurant.viewOrder(tableNumber);
							break;
						case 3:
							// Add Item to Order
							println("Main Menu > Order > Add Item to Order");
							println("Enter table number:");
							tableNumber = intScanner.nextInt();
							println("Enter MenuItem index:");
							itemIndexInMenu = intScanner.nextInt();
							if (itemIndexInMenu > 0){
								restaurant.addItemToOrder(tableNumber, itemIndexInMenu);
							}
							else
								println("Index is invalid");
							break;
						case 4:
							// Remove item from order
							println("Main Menu > Order > Remove Item From Order");
							println("Enter table number:");
							tableNumber = intScanner.nextInt();
							println("Enter MenuItem index in the order:");
							itemIndexInOrder = intScanner.nextInt();
							restaurant.removeItemFromOrder(tableNumber, itemIndexInOrder);
							break;
						case 5:
							//Remove order from the table
							println("Main Menu > Order > View/Update/Remove Order > Remove Order");
							println("Enter table number:");
							tableNumber = intScanner.nextInt();
							restaurant.removeOrder(tableNumber);
							break;
						case 6:
							//print order invoice
							println("Main Menu > Order > View/Update/Remove Order > Print Order Invoice");
							print("Enter a table number: ");
							tableNum=intScanner.nextInt();
							while (tableNum>30||tableNum<1) {
								println("Please enter a valid table number.");
								print("Enter table number: ");
								tableNum=intScanner.nextInt();
							}
							//get the current date
							Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
							java.util.Date dateOfOrder = calendar.getTime();
							//if(restaurant.getTable(tableNum-1).getStatus().equals("occupied")){
								restaurant.createRecord(tableNum-1);
								println("record created for table "+tableNum);
								//add record & print invoice (customer paid)
								restaurant.printOrderInvoice(dateOfOrder,tableNum-1);
								//remove order
								restaurant.removeOrder(tableNum);
								
//							}else{
//								println("No record found for Table"+(tableNum));
//							}
							
						case 7:
							// save
							saveRestaurant(restaurant);
							break;
						case 8:
							// save and exit
							saveRestaurant(restaurant);
							backToMenuFromOrder = true;
							exit = true;
							break;
						default:
							// handle invalid inputs
							break;
						}
					} while (!backToMenuFromOrder);
					break;
				case 3:

					// Menu Modification
					boolean backMenuModification = false;
					do {
						println("Main Menu > Menu Modification: ");
						println("0. Back");
						println("1. New Menu Item");
						println("2. View/Update/Remove Menu Item");
						println("3. Save");
						println("4. Save and Exit");
						print("Enter your option: ");
						choice = intScanner.nextInt();
						println(DIVIDER);

						switch (choice) {
						case 0:
							// Back
							backMenuModification = true;
							break;
						case 1:
							// New Menu Item
							boolean exitNewMenu = false;
							
							do{
								
							println("Main Menu > Menu Modification > New Menu Item");
							println("Select type of menu:");
							println("0. Back");
							println("1. Ala Carte");
							println("2. Package");
							choice = intScanner.nextInt();
							
							switch(choice){
							case 0:
								//back
								exitNewMenu = true;
								break;
								
							case 1:
								// add new ala carte menu
								println("Main Menu > Menu Modification > New Menu Item > Ala Carte");
								
								println("Enter Name:");
								String name = stringScanner.nextLine();
								
								println("Enter Description:");
								String description = stringScanner.nextLine();
								
								println("Enter Price:");
								int price = intScanner.nextInt();
								
								println("Enter Type:");
								println("1. Main Course");
								println("2. Drinks");
								println("3. Dessert");
								
								choice = intScanner.nextInt();
								
								restaurant.getMenu().addMenuItem(new AlaCarte(name,price,description,choice), choice);
							
								break;
							
							case 2:
								// add a promotional package menu
								println("Main Menu > Menu Modification > New Menu Item > Package");
								
								println("Enter Name:");
								name = stringScanner.nextLine();
								
								println("Enter Description:");
								description = stringScanner.nextLine();
								
								println("Enter Price:");
								price = intScanner.nextInt();
								
								// it will only create the template, to add item go to modify section
								restaurant.getMenu().addMenuItem(new Package(name,price,description), 4);
							
								break;
								
							default:
								break;
							}
							
							}while(!exitNewMenu);

							break;
						case 2:
							// View/Update/Remove Menu Item
							println("Main Menu > Menu Modification > View/Update/Remove Menu Item");
							
							boolean exitUpdate = false;
							
							do{
							println("Selection :");
							println("0. Back");
							println("1. View Menu");
							println("2. Update Menu Item");
							println("3. Remove");
							choice = intScanner.nextInt();
							
							switch(choice){
							
							case 0:
								exitUpdate = true;
								break;
							case 1:
								restaurant.getMenu().printMenu(2);
								break;
							case 2:
								
								int index; 
								
								restaurant.getMenu().printMenu(2);
								while(true){
								println("Select which menu to be updated:");
								println("Or press 0 to return:");
								restaurant.getMenu().printMenu(2);
								
								index = intScanner.nextInt();
								if(index == 0)break;
								
								//check whether the item updated is a package or ala carte
								if(restaurant.getMenu().getMenuItem(index-1) instanceof Package){
									
									while(true){
									System.out.println("select which attribute that needs to be updated:");
									System.out.println("0. Return");
									System.out.println("1. Name");
									System.out.println("2. Price");
									System.out.println("3. Description");
									System.out.println("4. Add Ala Carte");
									System.out.println("5. Remove Ala Carte");
									
									choice = intScanner.nextInt();
									if(choice == 0) break;
									
									switch(choice){
									case 1:
										System.out.println("Enter name");
										String name = stringScanner.nextLine();
										restaurant.getMenu().update(index, choice, name);
										break;
									case 2:
										System.out.println("Enter price");
										float price = intScanner.nextFloat();
										restaurant.getMenu().update(index, choice, String.valueOf(price));
										break;
									case 3:
										System.out.println("Enter description");
										String description = stringScanner.nextLine();
										restaurant.getMenu().update(index, choice, description);
										break;
									case 4:
										while(true){
											
											System.out.println("Enter Ala Carte to be added to the package:");
											System.out.println("Or enter 0 to finish");
											
											restaurant.getMenu().printMenu(1);
											int choiceAlaCarte = intScanner.nextInt();
											if(choiceAlaCarte == 0) break;

											restaurant.getMenu().update(index, choice, String.valueOf(choiceAlaCarte));
										}
										break;
									case 5:

										while (true){
											System.out.println("Enter Ala Carte to be removed to the package:");
											System.out.println("Or enter 0 to finish");

											restaurant.getMenu().getMenuItem(index-1).printDefaultDescription();
											int choiceAlaCarte = intScanner.nextInt();
											if(choiceAlaCarte == 0) break;

											restaurant.getMenu().update(index, choice,String.valueOf(choiceAlaCarte));
										}
										break;
									}
									}
								}
								else{
									
									while(true){
									System.out.println("select which attribute that needs to be updated:");
									System.out.println("0. Return");
									System.out.println("1. Name");
									System.out.println("2. Price");
									System.out.println("3. Description");
									System.out.println("4. Type");
									
									choice = intScanner.nextInt();
									if(choice == 0) break;
									
									switch(choice){
									
									case 1:
										System.out.println("Enter name");
										String name = stringScanner.nextLine();
										restaurant.getMenu().update(index, choice, name);
										break;
									case 2:
										System.out.println("Enter price");
										float price = intScanner.nextFloat();
										restaurant.getMenu().update(index, choice, String.valueOf(price));
										break;
									case 3:
										System.out.println("Enter description");
										String description = stringScanner.nextLine();
										restaurant.getMenu().update(index, choice, description);
										break;
									case 4:
										System.out.println("Enter new type: ");
										System.out.println("1. Main Course ");
										System.out.println("2. Drinks ");
										System.out.println("3. Desserts ");
										int type = intScanner.nextInt();
										
										restaurant.getMenu().update(index, choice,String.valueOf(type));
										break;
									}
									}
								}
								//restaurant.getMenu().update(choice);
								}
								break;
							case 3:
								restaurant.getMenu().printMenu(2);
								println("Select which menu to be removed:");
								choice = intScanner.nextInt();
								
								restaurant.getMenu().removeMenuItem(choice);
								break;
				
							}
							}while(!exitUpdate);
							
							break;
						case 3:
							// save
							saveRestaurant(restaurant);
							break;
						case 4:
							// save and exit
							saveRestaurant(restaurant);
							backMenuModification = true;
							exit = true;
							break;

						default:
							// handle invalid inputs
							break;
						}

					} while (!backMenuModification);

					break;
				case 4:
					// Staff
					boolean backToMenuFromStaff = false;
					char gender;
					String staffName, position;
					do {
						println(DIVIDER);
						println("Main Menu > Staff: ");
						println("0. Back");
						println("1. New Staff");
						println("2. View Staff");
						println("3. Update Staff Info");
						println("4. Remove Staff");
						println("5. Save");
						println("6. Save and Exit");
						print("Enter your option: ");
						choice = intScanner.nextInt();
						println(DIVIDER);

						// temporary storage
						switch (choice) {
						case 0:
							// Back
							backToMenuFromStaff = true;
							break;
						case 1:
							// New Staff
							println("Enter Name:");
							staffName = stringScanner.nextLine();
							println("Enter Gender:");
							gender = stringScanner.nextLine().charAt(0);
							println("Enter ID:");
							staffId = intScanner.nextInt();
							println("Enter Position:");
							position = stringScanner.nextLine();
							restaurant.addStaff(new Staff(staffName, gender, staffId, position));
							break;
						case 2:
							// View Staff
							restaurant.printStaff();
							break;
						case 3:
							//Update staff info
							println("Enter staff ID to update:");
							staffId = intScanner.nextInt();
							if (restaurant.isStaff(staffId)){
								println("Enter Name:");
								staffName = stringScanner.nextLine();
								println("Enter Gender:");
								gender = stringScanner.nextLine().charAt(0);
								println("Enter ID:");
								staffId = intScanner.nextInt();
								println("Enter Position:");
								position = stringScanner.nextLine();
								restaurant.updateStaff(staffName, gender, staffId, position);
							}
							else
								println("staffId is invalid");
							break;
						case 4:
							//remove staff
							println("Enter staff ID to be removed:");
							staffId = intScanner.nextInt();
							if (restaurant.isStaff(staffId)){
								restaurant.removeStaff(staffId);
							}
							else
								println("staffId is invalid");
							break;
						case 5:
							// save
							saveRestaurant(restaurant);
							break;
						case 6:
							// save and exit
							saveRestaurant(restaurant);
							backToMenuFromStaff = true;
							exit = true;
							break;
						default:
							// handle invalid inputs
							break;
						}

					} while (!backToMenuFromStaff);

					break;
				case 5:
					// Revenue Report
					boolean backRevenueReport = false;
					do{
						println("Main Menu > Revenue Report: ");
						println("0. Back");
						println("1. Print sale revenue report (by date)");
						println("2. Print sale revenue report (by month)");
						print("Enter your option: ");
						choice = intScanner.nextInt();
						println(DIVIDER);
						
						//temporary storage
						int tableNum;
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
						String inputDate;
						java.util.Date dateForReport;
						
						switch (choice){
						
						case 0: 
							backRevenueReport = true;
							break;
						case 1: 
							println("Main Menu > Revenue Report > Print sale revenue report (by date)");
							print("Revenue report for ");
						
							dateForReport = inputDateFormat(stringScanner);
							while (!dateForReport.before(new java.util.Date())){
								println("Future date entered. Please enter re-enter a valid date.");
								print("Revenue report for ");
								dateForReport = inputDateFormat(stringScanner);
							}
							restaurant.printDailySalesReport(dateForReport);
							
							break;
						case 2:
							//reset the global variable monthly sales
							restaurant.setMonthlySales(0.0f);
							println("Main Menu > Revenue Report > Print sale revenue report (by month)");
							print("Revenue report for ");
							dateForReport=inputMonthFormat(stringScanner);
							int monthEntered=dateForReport.getMonth();
							int currMonth=new java.util.Date().getMonth();
							while (monthEntered>currMonth){
								println("Future month entered. Please enter re-enter a valid month.");
								print("Revenue report for ");
								dateForReport = inputMonthFormat(stringScanner);
								monthEntered=dateForReport.getMonth();
								currMonth=new java.util.Date().getMonth();
							}
							restaurant.printMonthlySalesReport(dateForReport);
							break;
						}
					}while(!backRevenueReport);
					break;
				case 6:
					// save restaurant state
					saveRestaurant(restaurant);
					break;
				case 7:
					// save restaurant state
					saveRestaurant(restaurant);
					// exit
					exit = true;
					break;

				default:
					break;
				}

			} while (!exit);

			println("Program terminated.");

			intScanner.close();
			stringScanner.close();

		} catch (Exception e) {
			System.out.println("Exception >> " + e.getMessage());
			e.printStackTrace(System.out);
			
		}

	}
	private static java.util.Date inputMonthFormat(Scanner stringScanner) throws ParseException {
		print("Month(MM/YYYY): ");
		String inputDate = "01/"+stringScanner.nextLine();
		DateFormat df = new SimpleDateFormat("dd/MM/yy");
		java.util.Date startDate = df.parse(inputDate);
		return startDate;
	}
	private static java.util.Date inputDateFormat(Scanner stringScanner) throws ParseException{
		print("Date(DD/MM/YYYY): ");
		String inputDate = stringScanner.nextLine();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
		java.util.Date startDate = df.parse(inputDate);
		return startDate;
	}
	
	private static RestaurantCal inputDate(Scanner stringScanner){
		print("Date(DD/MM/YY): ");
		String inputDate = stringScanner.nextLine();
		String splitDate[] = inputDate.split("/");
		int day = Integer.parseInt(splitDate[0]);
		int month = Integer.parseInt(splitDate[1]);
		int year = Integer.parseInt(splitDate[2]);
		RestaurantCal date = new RestaurantCal(day, (month - 1), year, 0, 0);
		return date;
	}

	private static RestaurantCal inputDateAndTime(Scanner stringScanner) {

		print("Date(DD/MM/YYYY): ");
		String arrivalDate = stringScanner.nextLine();
		print("Time(hour:minute): ");
		String arrivalTime = stringScanner.nextLine();

		String splitDate[] = arrivalDate.split("/");
		String splitTime[] = arrivalTime.split("\\."); // use back slash to
														// escape the special
														// character "."

		int day = Integer.parseInt(splitDate[0]);
		int month = Integer.parseInt(splitDate[1]);
		int year = Integer.parseInt(splitDate[2]);

		int hour = Integer.parseInt(splitTime[0]);
		int min = Integer.parseInt(splitTime[1]);

		RestaurantCal date = new RestaurantCal(day, (month - 1), year, hour, min);
		return date;
	}

	private static RestaurantCal inputDateAndSession(Scanner stringScanner) {

		print("Date(DD/MM/YYYY): ");
		String arrivalDate = stringScanner.nextLine();
		println("Choose session");
		println("1. AM");
		println("2. PM");
		println("3. Invalid");
		String session = stringScanner.nextLine();

		String splitDate[] = arrivalDate.split("/");

		int day = Integer.parseInt(splitDate[0]);
		int month = Integer.parseInt(splitDate[1]);
		int year = Integer.parseInt(splitDate[2]);

		int hour;
		int min;

		if (session.equals("1")) {
			hour = 12;
			min = 0;
		} else if (session.equals("2")) {
			hour = 19;
			min = 0;
		}
		else{
			hour = 23;
			min = 0;
		}
		
		RestaurantCal date = new RestaurantCal(day, (month - 1), year, hour, min);
		return date;
	}

	private static void println(String message) {
		System.out.println(message);
	}

	private static void print(String message) {
		System.out.print(message);
	}

	private static void saveRestaurant(Restaurant restaurant) {
		DatabaseManager.writeRestaurantObject("restaurant.dat", restaurant);
		println("Restaurant information is saved.");
	}

}
