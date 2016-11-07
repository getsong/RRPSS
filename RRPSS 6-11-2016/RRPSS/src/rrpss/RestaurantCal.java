package rrpss;


import java.io.Serializable;
import java.util.Calendar;

public class RestaurantCal extends Calendar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static RestaurantCal getCurrentDate(Calendar calendar){
		return new RestaurantCal(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
	}
	
	public RestaurantCal(int day, int month, int year, int hour, int min){
		this.set(RestaurantCal.DAY_OF_MONTH, day);
		this.set(RestaurantCal.MONTH, month);
		this.set(RestaurantCal.YEAR, year);
		this.set(RestaurantCal.HOUR_OF_DAY, hour);
		this.set(RestaurantCal.MINUTE, min);
	}
	
	public String getString(){
		int day = this.get(RestaurantCal.DAY_OF_MONTH);
		int month = this.get(RestaurantCal.MONTH);
		int year = this.get(RestaurantCal.YEAR);
		int hour = this.get(RestaurantCal.HOUR_OF_DAY);
		int minute = this.get(RestaurantCal.MINUTE);
		return day + "/" + (month+1) + "/" + year + ", " + hour + ":" + String.format("%02d", minute) + " (" + this.getSession() + ") ";
	}
	
	public String getSession(){
		int hour = this.get(HOUR_OF_DAY);
		if(hour>=11 && hour<=15){
			return "AM";
		}
		else if(hour>=18 && hour<=22){
			return "PM";
		}
		else{
			return "invalid";
		}
	}
	
	public boolean equals(RestaurantCal date){
		if(this.get(RestaurantCal.YEAR) != date.get(RestaurantCal.YEAR)){
			return false;			
		}
		else if(this.get(RestaurantCal.MONTH) != date.get(RestaurantCal.MONTH)){
			return false;
		}
		else if(this.get(RestaurantCal.DAY_OF_MONTH) != date.get(RestaurantCal.DAY_OF_MONTH)){
			return false;
		}
		else if(!this.getSession().equals(date.getSession())){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean isBefore(RestaurantCal date){
		
		// define expired time here
		int late = 30;
		
		int cDay = this.get(RestaurantCal.DAY_OF_MONTH);
		int cMonth = this.get(RestaurantCal.MONTH);
		int cYear = this.get(RestaurantCal.YEAR);
		int cHour = this.get(RestaurantCal.HOUR_OF_DAY);
		int cMinute = this.get(RestaurantCal.MINUTE);
				
		int day = date.get(RestaurantCal.DAY_OF_MONTH);
		int month = date.get(RestaurantCal.MONTH);
		int year = date.get(RestaurantCal.YEAR);
		int hour = date.get(RestaurantCal.HOUR_OF_DAY);
		int minute = date.get(RestaurantCal.MINUTE);
		
		// fast forward current time (Customer can be late for "late" minute)
		minute += late;
		
		hour += minute/60;
		minute %= 60;
		
		day += hour/24;
		hour %= 24;
		
		if(cYear < year){
			return true;			
		}
		else if(cYear > year){
			return false;			
		}
		else if(cMonth < month){
			return true;
		}
		else if(cMonth > month){
			return false;
		}
		else if(cDay < day){
			return true;
		}
		else if(cDay > day){
			return false;
		}
		else if(cHour < hour){
			return true;
		}
		else if(cHour > hour){
			return false;
		}
		else if(cMinute < minute){
			return true;
		}
		else if(cMinute > minute){
			return false;
		}
		else{
			return false;
		}

	}

	@Override
	public void add(int field, int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void computeFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void computeTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getGreatestMinimum(int field) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLeastMaximum(int field) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximum(int field) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinimum(int field) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void roll(int field, boolean up) {
		// TODO Auto-generated method stub
		
	}	
	

}
