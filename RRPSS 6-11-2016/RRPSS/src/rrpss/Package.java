package rrpss;

import java.util.ArrayList;

public class Package extends MenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<AlaCarte> alaCarteList = new ArrayList<AlaCarte>();
	
	public Package(String name,float price,String description){
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public void addAlaCarte(AlaCarte item) {
		
			alaCarteList.add(item);
			System.out.println("Package: " + name + " is updated.");
	}

	public void removeAlaCarte(int choice) {
		
		alaCarteList.remove(choice - 1);
		System.out.println("Package: " + name + " is updated.");
	}

	public void printDefaultDescription() {

		System.out.println(name + " comprises: ");
		int i = 1;
		for (AlaCarte alaCarte : alaCarteList) {
			System.out.print(i + ". ");
			System.out.println(alaCarte.name);
			i++;
		}
	}

	@Override
	public void setType(int type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
