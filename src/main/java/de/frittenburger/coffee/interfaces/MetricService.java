package de.frittenburger.coffee.interfaces;

import de.frittenburger.coffee.model.MetricException;

public interface MetricService {

	void permit(String key) throws MetricException;

}
