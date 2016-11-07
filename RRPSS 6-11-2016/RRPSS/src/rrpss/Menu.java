package rrpss;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();
	private int mainCourseSize = 0;
	private int drinkSize = 0;
	private int dessertSize = 0;
	
	//constructor
	public Menu(){};
	
	public void addMenuItem(MenuItem menuItem , int type) {
		// type will be 1 for main course, 2 for drink , 3 for dessert , 4 for package
		switch(type){
		
		case 1:
			menuItemList.add(0, menuItem);
			mainCourseSize++;
			break;
		case 2:
			menuItemList.add(mainCourseSize, menuItem);
			drinkSize++;
			break;
		case 3:
			menuItemList.add(mainCourseSize+drinkSize , menuItem);
			dessertSize++;
			break;
		case 4:
			// for package
			menuItemList.add(mainCourseSize+drinkSize+dessertSize , menuItem);
			
			break;
		default:
			break;
		}
	}

	public void removeMenuItem(int index) {
		
		// if the item to be removed is package do nothing
		if(index >= mainCourseSize+drinkSize+dessertSize);
		
		//if the item to be removed is dessert , decrement dessertSize
		else if(index >= mainCourseSize+drinkSize)
			dessertSize--;
		
		else if(index >= mainCourseSize)
			drinkSize--;
		else
			mainCourseSize--;
			
		menuItemList.remove(index - 1);
		System.out.println("Menu is updated.");
	}

	public void printMenu(int id) {
		// id = 1 to print ala carte only , id = 0 to print package only , id = any other numbr to print all
		int i = 1;
		for (MenuItem menuItem : menuItemList) {
			
			if(i <= mainCourseSize+drinkSize+dessertSize && id ==0)
				continue;
			if(i == mainCourseSize+drinkSize+dessertSize+1 && id==1)
				continue;
			
			System.out.print(i + ". ");
			System.out.println("name: " + menuItem.getName());
			System.out.println("description: " + menuItem.getDescription());
			System.out.println("price: " + menuItem.getPrice());
			if(menuItem instanceof AlaCarte){
				// temporary is just used to store the return type as integer but it will not be used here
				@SuppressWarnings("unused")
				int temporary = menuItem.getType();
			}
			//print the detailed package content
			else{
				menuItem.printDefaultDescription();
			}
			i++;
			
		}
	}
	
	
	public ArrayList<MenuItem> getMenuItemList(){
		return menuItemList;
	}
	
	public MenuItem getMenuItem(int index){
		return menuItemList.get(index);
	}
	
	public void update(int index,int choice,String parameter){
		
		if(menuItemList.get(index-1) instanceof Package){
			
			switch(choice){
			case 1:
				menuItemList.get(index-1).setName(parameter);
				break;
			case 2:
				menuItemList.get(index-1).setPrice(Float.parseFloat(parameter));
				break;
			case 3:
				menuItemList.get(index-1).setDescription(parameter);
				break;
			case 4:
				menuItemList.get(index-1).addAlaCarte((AlaCarte) menuItemList.get(Integer.parseInt(parameter)-1));
				break;
			case 5:
				menuItemList.get(index-1).removeAlaCarte(Integer.parseInt(parameter));
				break;
			}
			
		}
		else{
			
			switch(choice){
			
			case 1:
				menuItemList.get(index-1).setName(parameter);
				break;
			case 2:
				menuItemList.get(index-1).setPrice(Float.parseFloat(parameter));
				break;
			case 3:
				menuItemList.get(index-1).setDescription(parameter);
				break;
			case 4:
				menuItemList.get(index-1).setType(Integer.parseInt(parameter));
				MenuItem menuTemporary = menuItemList.get(index-1);
				
				removeMenuItem(index);
				
				addMenuItem(menuTemporary,Integer.parseInt(parameter));
				break;
			}
		}
	}
}
