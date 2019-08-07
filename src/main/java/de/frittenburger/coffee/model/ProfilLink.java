package de.frittenburger.coffee.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfilLink {

	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time1 ^ (time1 >>> 32));
		result = prime * result + (int) (time2 ^ (time2 >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfilLink other = (ProfilLink) obj;
		if (time1 != other.time1)
			return false;
		if (time2 != other.time2)
			return false;
		return true;
	}

	

}
