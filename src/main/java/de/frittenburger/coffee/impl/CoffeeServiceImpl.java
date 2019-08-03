package de.frittenburger.coffee.impl;

import java.util.ArrayList;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.coffee.interfaces.CoffeeJob;
import de.frittenburger.coffee.interfaces.CoffeeService;

public class CoffeeServiceImpl implements CoffeeService , Runnable {

	
	
	
	private static final Logger logger = LogManager.getLogger(CoffeeServiceImpl.class);

	private boolean shouldRun = true;

	private List<CoffeeJob> jobs = new ArrayList<>();
	
	@Override
	public void addJob(CoffeeJob job) {
		jobs.add(job);
	}


	
	@Override
	public void run() {

		long cycle = 0;
		while(shouldRun)
		{
			
			logger.trace("step");
			for(CoffeeJob job : jobs)
			{
				try
				{
					job.exec(cycle);
				} catch(Exception e) {
					logger.error(e);
				}
			}
			
			
			try {
				Thread.sleep(1000);
				cycle += 1000;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	

	

}
