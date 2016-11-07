package rrpss;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class Restaurant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Table[] tableList;
	private Menu menu;
	private ArrayList<Staff> staffList;
	private ArrayList<Record> recordList;
	final String DIVIDER = "***************************************";
	final String DIVIDER1 = "---------------------------------------";
	private Record record;

	public Restaurant(Table[] tableList) {
		this.tableList = tableList;
		staffList = new ArrayList<Staff>();
		recordList = new ArrayList<Record>();
		this.menu = new Menu();
	}
	
	public void updateReservation() {
		RestaurantCal currentDate = RestaurantCal.getCurrentDate(Calendar.getInstance());
		ArrayList<Reservation> tempArrayList = new ArrayList<Reservation>();
		for(int i =0 ; i<tableList.length;i++){
			for(Reservation reservation: tableList[i].getReservationList()){
				if(!currentDate.isBefore(reservation.getArrivalDateAndTime())){
					tempArrayList.add(reservation);
				}
			}
		}
		for(Reservation reservation:tempArrayList){
			tableList[reservation.getTableId()-1].getReservationList().remove(reservation);
		}
		
	}
	
	
	public Table getTable(int id) {
		return tableList[id - 1];
	}
	
	public void viewAllOrders(int tableIndex){
		for (tableIndex=1; tableIndex<(tableList.length+1); tableIndex++){
			System.out.println("Table "+ tableIndex);
			tableList[tableIndex-1].viewOrder();
		}
	}
	
	public void viewOrder(int tableIndex){
		tableList[tableIndex-1].viewOrder();
	}
	
	public void addOrder(int tableIndex, ArrayList<MenuItem> menuItemList, int staffId){
		Staff staff=getStaff(staffId);
		tableList[tableIndex-1].addOrder(new Order(menuItemList, staff));
	}
	
	public void removeOrder(int tableIndex){
		tableList[tableIndex-1].removeOrder();
	}
	
	public void addItemToOrder(int tableIndex, int itemIndexInMenu){
		MenuItem menuItem = getMenu().getMenuItem(itemIndexInMenu-1);
		tableList[tableIndex-1].addItemToOrder(menuItem);
	}
	
	public void removeItemFromOrder(int tableIndex, int itemIndexInOrder){
		tableList[tableIndex-1].removeItemFromOrder(itemIndexInOrder);
	}

	public void addStaff(Staff staff) {
		staffList.add(staff);
	}
	
	public boolean isStaff(int staffId) {
		for (int i=0; i<staffList.size(); i++){
	        if (staffList.get(i).getId() == staffId)
	        	return true;
	    }
		return false;
	}
	
	public void updateStaff(String name, char gender, int id, String position) {
		for (int i=0; i<staffList.size(); i++){
	        if (staffList.get(i).getId() == id){
	        	staffList.remove(i);
	        	staffList.add(new Staff(name, gender, id, position));
	        }
	    }
	}
	
	public void removeStaff(int id) {
		for (int i=0; i<staffList.size(); i++){
	        if (staffList.get(i).getId() == id){
	        	staffList.remove(i);
	        }
	    }
	}
	
	public void printStaff(){
		int i;
		for (i=0; i<staffList.size(); i++){
	        Staff temp= staffList.get(i);
			System.out.println("Name:"+temp.getName()+" Gender:"+temp.getGender()+" ID:"+temp.getId()+" Position:"+temp.getPosition());
		}
		if (i==0)
			System.out.println("There is no staff in the restaurant now. :(");
	}
	
	public Staff getStaff(int staffId){
		for (int i=0; i<staffList.size(); i++){
			if (staffList.get(i).getId() == staffId){
	        	return staffList.get(i);
	        }
		}
		return null;
	}

	public void createRecord(int tableId){
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		java.util.Date date = calendar.getTime();
		ArrayList<MenuItem> menuItemList = tableList[tableId].getOrder().getOrderItemList();
	    record = new Record(date,menuItemList,tableId,"",0.0f);
	    addRecord(record);
	}
	
	public void addRecord(Record record) {
		recordList.add(record);
	}

	public void printTablesAvailable(RestaurantCal date, int size) {

		if (isTablesAvailable(date, size)) {
			System.out.println("Available tables of size " + size + " on " + date.getString() + ": ");
			for (int i = 0; i < tableList.length; i++) {
				boolean available = true;
				if (tableList[i].getSize() >= size) {
					for (Reservation reservation : tableList[i].getReservationList()) {
						if (date.equals(reservation.getArrivalDateAndTime())) {
							available = false;
							break;
						}
					}
					if (available) {
						System.out.println("Table " + tableList[i].getTableId() + " : size " + tableList[i].getSize());
					}
				}

			}
		} else {
			System.out.println("Tables for " + size + "persons on " + date.getString() + " (" + date.getSession() + ") " + " are not available.");
		}

	}

	public boolean isTablesAvailable(RestaurantCal date, int size) {
		// update restaurant
		this.updateReservation();
		// before checking available table, update reservation every time
		boolean available = true;
		for (int i = 0; i < tableList.length; i++) {
			if (tableList[i].getSize() >= size) {
				available = true;
				for (Reservation reservation : tableList[i].getReservationList()) {
					if (date.equals(reservation.getArrivalDateAndTime())) {
						available = false;
						break;
					}
				}
				if(available){
					return available;
				}
			}
		}
		return available;
	}
	
	public void setMonthlySales(float monthlySales){
		this.monthlySales=monthlySales;
	}
	
	//Sale Revenue Report
	float monthlySales=0.0f;
	SimpleDateFormat dateFormat;
	
	public void printDailySalesReport(java.util.Date dateForReport) {
		float tempDailySales=0.0f; 
		boolean sameDate;
		int counter=0;
		dateFormat = new SimpleDateFormat("ddMMyyyy");
		System.out.println("");
		
		//print by date
		for (Record record:recordList){
			//find the records which are on dateForReport date
			sameDate=dateFormat.format(dateForReport).equals(dateFormat.format(record.getSaleTime()));
			if(sameDate){
				System.out.println("Sale Time: "+record.getSaleTime()+", Table Id: "+record.getTableId());
				record.printItems(menu);
				System.out.println("");
				tempDailySales+=record.getSale();
			}
		}
		//save the tempDailySales in dailySales
		monthlySales+=tempDailySales;	
		println("Daily Sales: "+ tempDailySales);
	}
	
	public void printMonthlySalesReport(java.util.Date dateForReport){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateForReport);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		boolean[] daysWithRecordArr = getDaysWithRecord(maxDay, dateForReport);
		for (int dateCounter=1; dateCounter<=30; dateCounter++){
			if(daysWithRecordArr[dateCounter-1]==true){
				//do only if there is a record for that day
				println(DIVIDER1);
				dateForReport.setDate(dateCounter);
				printDailySalesReport(dateForReport);
			}
		}
		println(DIVIDER1);
		println("Monthly Total Sales: "+monthlySales);
	}
	
	private boolean[] getDaysWithRecord(int maxDay, Date saleTime){
		//check which days of the month has a record
		boolean[] dayArr=new boolean[maxDay];
		for(int i=1; i<=maxDay; i++){//iterate through each day of the month
			saleTime.setDate(i);
			if(hasRecord(saleTime)) {
				dayArr[i-1]=true;
				System.out.println("day "+i+" has a record");
			}else{
				dayArr[i-1]=false;		
			}
		}
		return dayArr;
	}
	
	private boolean hasRecord(Date saleTime){
		boolean sameDate;
		dateFormat = new SimpleDateFormat("ddMMyyyy");
		for (Record record: recordList){
			sameDate=dateFormat.format(saleTime).equals(dateFormat.format(record.getSaleTime()));
			if (sameDate)
				return true;
		}
		return false;
	}
	//
	
	public Boolean hasRecord(Date saleTime,int tableId){
		int recordIndex = returnIndex(saleTime, tableId);
		if (recordIndex==-1) return false;
		else return true;
	}
	
	public void printOrderInvoice(Date saleTime,int tableId){
			int recordIndex = returnIndex(saleTime, tableId);
			Record record=recordList.get(recordIndex);
			//print
			println("");
			println(DIVIDER);
			println("---------------INVOICE-----------------");
			println("Table "+(tableId+1));
			println("Time "+saleTime.toString());
			println(DIVIDER1);
			//print items
			int i=0;
			float price=0;
			float totalSales=0;
			DecimalFormat oneDigit = new DecimalFormat("#,##0.0");
			//get items in menu
			ArrayList<MenuItem> temp=menu.getMenuItemList();
			for (MenuItem menuItem: temp){
				//count number of occurrences of each type of menuItem
				MenuItem mi = menu.getMenuItem(i);
				ArrayList<MenuItem> itemSoldList = record.getItemSold();
				int numOrders = Collections.frequency(itemSoldList, mi);
				i++;
				price=numOrders*mi.price;
				if(numOrders>0){
					price=numOrders*mi.price;
					totalSales+=price;
					println(numOrders+" "+mi.getName()+" $"+price);
				}
			}
			println(DIVIDER1);
			//print sums
			println("SUBTOTAL:  $"+totalSales);
			//saving the value of sales revenue as tempSales
			float tempSales=(float) (1.1*totalSales);
			record.setSale(tempSales);
			System.out.println("assigning value of tempSales as "+tempSales);
			println("10% SERVICE CHARGE:  $"+oneDigit.format(0.1*totalSales));
			println("7% GST:  $"+oneDigit.format(0.07*1.1*totalSales));
			println(DIVIDER1);
			println("TOTAL DUE:  $"+oneDigit.format(1.07*1.1*totalSales));
			println("");
			println(DIVIDER);
		
	}
		private int returnIndex(Date saleTime,int tableId){
			//need to find the one with the most recent
			int index=-1;//returns -1 if cannot find record
			int i=0;
			ArrayList<Integer> indexList = new ArrayList();;
			
			int month=saleTime.getMonth();
			int day=saleTime.getDay();
			int year=saleTime.getYear();
			for(Record record:recordList){
				if(record.getSaleTime().getMonth()==month&&
					record.getSaleTime().getDay()==day&&
					record.getSaleTime().getYear()==year&&
					record.getTableId()==tableId){
					index=i;
				}
				i++;
			}
			indexList.add(index);
			int size=indexList.size();
			//return the last index only
			return indexList.get(size-1);
		}
	

	public Table[] getTableList() {
		return tableList;
	}

	public Menu getMenu() {
		return menu;
	}
	
	public void resetMenu() {
		this.menu = new Menu();
	}
	private void println(String s){
		System.out.println(s);
	}
	
	private void print(String s){
		System.out.print(s);
	}

}
