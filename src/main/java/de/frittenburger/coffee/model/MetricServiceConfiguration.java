package de.frittenburger.coffee.model;

import java.util.Map;

public class MetricServiceConfiguration {

	private Map<String,MetricLimit> metrics;

	public Map<String,MetricLimit> getMetrics() {
		return metrics;
	}

	public void setMetrics(Map<String,MetricLimit> metrics) {
		this.metrics = metrics;
	}
	
	
}
