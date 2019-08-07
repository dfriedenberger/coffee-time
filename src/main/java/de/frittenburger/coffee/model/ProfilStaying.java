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
	public boolean isEqualState(ProfilLink obj) {
		if (this == obj)
			return true;
		if(obj == null)
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
