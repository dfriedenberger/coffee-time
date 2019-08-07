package de.frittenburger.coffee.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ProfilLink {

	
	private long time1 = 0;
	private long time2 = 0;

	public void setTimeRange(long time1, long time2) {
		this.time1 = time1;
		this.time2 = time2;
	}

	public String toTimeString() {
		
		Date date1 = new Date(time1 * 1000);
		Date date2 = new Date(time2 * 1000);
		DateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(date1)+" Uhr - "+df.format(date2)+" Uhr";
	
	}

	public abstract boolean isEqualState(ProfilLink lastLink);
		

}
