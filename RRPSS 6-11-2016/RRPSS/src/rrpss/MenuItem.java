package rrpss;

import java.io.Serializable;

public abstract class MenuItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected float price;
	protected String description;
	protected boolean typePackage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	// abstrct method below is the specific method for each class , 
	// but since we are calling it from menuItem we need these abstract method here

	public abstract void setType(int type);

	public abstract int getType();

	public abstract void addAlaCarte(AlaCarte item);
	
	public abstract void removeAlaCarte(int choice);
	
	public abstract void printDefaultDescription();

}
