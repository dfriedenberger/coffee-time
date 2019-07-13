package de.frittenburger.subscriber.mqtt.model;

import java.util.List;

public class MqttWaypointList extends MqttElement {
	

	private List<MqttWaypoint> waypoints;

	public List<MqttWaypoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<MqttWaypoint> waypoints) {
		this.waypoints = waypoints;
	}

	@Override
	public String toString() {
		return "MqttWaypointList [waypoints=" + waypoints + "]";
	}
	
	
	
}
