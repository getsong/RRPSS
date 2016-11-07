package rrpss;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Record implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date saleTime;
	private ArrayList<MenuItem> itemSold;
	private String invoice;
	private float sale;
	private int tableId;
	final String DIVIDER = "***************************************";
	final String DIVIDER1 = "---------------------------------------";

	public Record(Date saleTime, ArrayList<MenuItem> itemSold, int tableId, String invoice, float sale) {
		super();
		this.saleTime = saleTime;
		this.itemSold = itemSold;
		this.tableId=tableId;
		this.invoice = invoice;
		this.sale = sale;
		
	}

	public int getTableId(){
		return tableId;
	}
		
	public Date getSaleTime() {
		return saleTime;
		
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public ArrayList<MenuItem> getItemSold() {
		return itemSold;
	}

	public void setItemSold(ArrayList<MenuItem> itemSold) {
		this.itemSold = itemSold;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public float getSale() {
		return sale;
	}

	public void setSale(float sale) {
		this.sale = sale;
	}

	public void printItems(Menu menu){
		int i = 1;
		for(MenuItem menuItem : itemSold){
			System.out.print(i + ". ");
			System.out.println(menuItem.name+" $"+menuItem.price);
			i++;
		}
		System.out.println("Order Revenue: $"+sale);
	}
	
}
