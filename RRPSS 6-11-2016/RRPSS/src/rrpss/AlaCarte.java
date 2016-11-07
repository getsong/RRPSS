package rrpss;

public class AlaCarte extends MenuItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	
	public AlaCarte(String name,float price,String description, int type){
		
		this.type = type;
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public int getType() {
		// type will be 1 for main course, 2 for drink , 3 for dessert , 4 for package
		
		switch(this.type){
		
		case 1: 
			System.out.println("main course");
			break;
		case 2:
			System.out.println("drinks");
			break;
		case 3:
			System.out.println("desserts");
			break;
		}
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void addAlaCarte(AlaCarte item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAlaCarte(int choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printDefaultDescription() {
		// TODO Auto-generated method stub
		
	}

}
