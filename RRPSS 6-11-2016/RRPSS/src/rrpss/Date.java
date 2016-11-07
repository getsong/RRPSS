package rrpss;

import java.io.Serializable;

public class Date implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int day;
	private int month;
	private int year;
	private boolean am_session;

	public Date(int day, int month, int year, boolean am_session) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.am_session = am_session;
	}

	public String getDateString() {
		String session;
		if (this.am_session) {
			session = "AM";
		} else {
			session = "PM";
		}
		return day + "/" + month + "/" + year + ", " + session;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean isAm_session() {
		return am_session;
	}

	public void setAm_session(boolean am_session) {
		this.am_session = am_session;
	}

	public boolean equals(Date date) {

		if (date.getDay() != this.day) {
			return false;
		}
		if (date.getMonth() != this.month) {
			return false;
		}
		if (date.getYear() != this.year) {
			return false;
		}
		if (date.getYear() != this.year) {
			return false;
		}
		if(date.isAm_session() ^ this.am_session){
			return false;
		}
		return true;
	}

}
