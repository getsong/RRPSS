package rrpss;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<MenuItem> orderItemList;
	private Staff staffInCharge;
	
	
	
	public Order(ArrayList<MenuItem> itemList, Staff staffInCharge) {
		orderItemList= new ArrayList<MenuItem>();
		for (MenuItem temp : itemList)
			addOrderItem(temp);
		this.staffInCharge = staffInCharge;
	}
	
	public ArrayList<MenuItem> getOrderItemList(){
		return this.orderItemList;
	}

	public void viewOrderItemList(){
		int i = 1;
		for(MenuItem menuItem : orderItemList){
			System.out.print(i + ". ");
			System.out.println(menuItem.name);
			i++;
		}
	}

	public void addOrderItem(MenuItem menuItem) {
		orderItemList.add(menuItem);
	}

	public void removeOrderItem(int itemIndexInOrder) {
		orderItemList.remove(itemIndexInOrder - 1);
	}

	public Staff getStaffInCharge() {
		return staffInCharge;
	}

	public void setStaffInCharge(Staff staffInCharge) {
		this.staffInCharge = staffInCharge;
	}

}
