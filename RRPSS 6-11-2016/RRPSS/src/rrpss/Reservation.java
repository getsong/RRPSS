package rrpss;

import java.io.Serializable;

public class Reservation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tableId;
	private RestaurantCal arrivalDateAndSession;
	private int pax;
	private String name;
	private String contact;

	
	
	public int getTableId() {
		return tableId;
	}



	public void setTableId(int tableId) {
		this.tableId = tableId;
	}



	public Reservation(RestaurantCal arrivalDateAndTime, int pax, String name, String contact, int tableId) {
		this.arrivalDateAndSession = arrivalDateAndTime;
		this.pax = pax;
		this.name = name;
		this.contact = contact;
		this.tableId = tableId;
	}

	

	public RestaurantCal getArrivalDateAndTime() {
		return arrivalDateAndSession;
	}



	public void setArrivalDateAndTime(RestaurantCal arrivalDateAndTime) {
		this.arrivalDateAndSession = arrivalDateAndTime;
	}



	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public void printReservationDetail(){
		System.out.println("Customer's name       : " + name);
		System.out.println("Table ID              : " + tableId);
		System.out.println("PAX                   : " + pax);
		System.out.println("Arrival Date and Time : " + arrivalDateAndSession.getString());
		System.out.println("Contact               : " + contact);
	}
	
	public boolean equals(Reservation reservation) {
	    if (this.arrivalDateAndSession.equals(reservation.getArrivalDateAndTime())) return true;
	    return false;
	}

}
