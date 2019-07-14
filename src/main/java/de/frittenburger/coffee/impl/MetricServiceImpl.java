package de.frittenburger.coffee.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.coffee.interfaces.MetricService;
import de.frittenburger.coffee.model.Call;
import de.frittenburger.coffee.model.MetricException;
import de.frittenburger.coffee.model.MetricLimit;
import de.frittenburger.coffee.model.MetricServiceConfiguration;

public class MetricServiceImpl implements MetricService {

	private static final Logger logger = LogManager.getLogger(MetricServiceImpl.class);

	private static final int HOUR = 3600 * 1000;
	private static final int DAY = HOUR * 24;
	private MetricServiceConfiguration config;
	private List<Call> calls = new ArrayList<>();


	public MetricServiceImpl(MetricServiceConfiguration config)
	{
		this.config = config;
	}
	
	
	@Override
	public synchronized void  permit(String key) throws MetricException {
	
		
		if(!config.getMetrics().containsKey(key))
			throw new MetricException("Key "+key+" not configured");

		MetricLimit limit = config.getMetrics().get(key);
		
		int cnt_per_hour = getCounter(key,HOUR);
		if(cnt_per_hour >= limit.getMax_per_hour())
			throw new MetricException("Raise max per hour condition "+key+" cnt=" + cnt_per_hour);

		int cnt_per_day = getCounter(key,DAY);
		if(cnt_per_day >= limit.getMax_per_day())
			throw new MetricException("Raise max per day condition "+key+" cnt=" + cnt_per_day);
		
		
		incrCounter(key);
		
		logger.info("permit {} hour({}/{}) day({}/{}) ",key,cnt_per_hour+1,limit.getMax_per_hour(),
				cnt_per_day+1,limit.getMax_per_day());

	}


	private void incrCounter(String key) {
		
		//ToDo add to Log
		calls.add(new Call(new Date().getTime(),key));
	}


	private int getCounter(String key, long offset) {

		long time = new Date().getTime() - offset;
		
		int cnt = 0;
		for(int i = calls.size() - 1;i >= 0;i--)
		{
			Call call = calls.get(i);
			if(call.getTime() < time) break; //To old
			if(call.getKey().equals(key)) cnt++;
		}
	
		return cnt;
	}





	
}
