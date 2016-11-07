package rrpss;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DatabaseManager {

	public static Restaurant readRestaurantObject(String filename) {
		Restaurant restaurant = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			restaurant = (Restaurant) in.readObject();
			// to initialize menu if not found
			if(restaurant.getMenu() == null)
				restaurant.resetMenu();
			in.close();
		} catch (FileNotFoundException e) {
			restaurant = new Restaurant(Table.initTables());
			return restaurant;
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		// print out the size
		// System.out.println(" Details Size: " + pDetails.size());
		// System.out.println();
		return restaurant;
	}

	public static void writeRestaurantObject(String filename, Restaurant restaurant) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(restaurant);
			out.close();
			// System.out.println("Object Persisted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
