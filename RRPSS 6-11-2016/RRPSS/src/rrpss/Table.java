package rrpss;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tableId;
	private int size;
	private String status;
	private Order order;
	private ArrayList<Reservation> reservationList;

	public static final int[][] TABLES = { { 5, 10 }, { 5, 8 }, { 10, 4 }, { 10, 2 } };
	public static final int NUMBER_OF_TABLE = 30;

	public Table(int tableId, int size) {
		this.tableId = tableId;
		this.size = size;
		this.status = "vacated";
		order=null;
		reservationList = new ArrayList<Reservation>();
	}

	public static Table[] initTables() {
		Table[] tableList = new Table[NUMBER_OF_TABLE];
		int tableId = 0;

		// i is the number of table types
		for (int i = 0; i < TABLES.length; i++) {
			int numberOfTables = TABLES[i][0];
			// j is the number of tables for type i
			for (int j = 0; j < numberOfTables; j++) {
				int tableSize = TABLES[i][1];
				tableList[tableId] = new Table((tableId + 1), tableSize);
				tableId++;
			}
		}
		return tableList;
	}

	public int getTableId() {
		return tableId;
	}
	
	public Order getOrder(){
		return this.order;
	}
	
	public void viewOrder(){
		if (order == null)
			System.out.println("There is no order for the table");
		else
			order.viewOrderItemList();
	}

	public void addOrder(Order order) {
		this.order=order;
		status="occupied";
	}
	
	public void removeOrder() {
		this.order=null;
		status="vacated";
	}
	
	public void addItemToOrder(MenuItem menuItem){
		order.addOrderItem(menuItem);
	}
	
	public void removeItemFromOrder(int itemIndexInOrder){
		order.removeOrderItem(itemIndexInOrder);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void addReservationList(Reservation reservation) {
		reservationList.add(reservation);
	}
	
	public void viewReservation() {
		int i = 1;
		for(Reservation reservation:reservationList){
			System.out.println(i + ". ");
			reservation.printReservationDetail();
			i++;
		}
	}
	
	public ArrayList<Reservation> viewReservation(String name) {
		ArrayList<Reservation> reservationSearchResult = new ArrayList<Reservation>();
		for(Reservation reservation:reservationList){
			if(reservation.getName().equals(name)){
				reservationSearchResult.add(reservation);
			}
		}
		return reservationSearchResult;
	}
	
	public ArrayList<Reservation> viewReservation(RestaurantCal date) {
		ArrayList<Reservation> reservationSearchResult = new ArrayList<Reservation>();
		for(Reservation reservation:reservationList){
			if(reservation.getArrivalDateAndTime().equals(date)){
				reservationSearchResult.add(reservation);
			}
		}
		return reservationSearchResult;
	}
	

	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}



	public void getSaleRecord() {
		if(status=="occupied"){
			order.viewOrderItemList();
		}else System.out.println("table is vacated");
	}
	
	public void printOrderInvoice(){
		
	}

}
