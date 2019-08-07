package de.frittenburger.coffee.model;

import de.frittenburger.geo.model.GeoPoint;

public class ProfilStaying extends ProfilLink {

	private GeoPoint point;
	
	public GeoPoint getPoint() {
		return point;
	}

	public void setPoint(GeoPoint point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "ProfilStaying "+super.toTimeString()+" [point=" + point + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfilStaying other = (ProfilStaying) obj;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}

	
	
	
}
