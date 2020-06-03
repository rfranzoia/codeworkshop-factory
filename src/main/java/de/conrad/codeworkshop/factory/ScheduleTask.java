package de.conrad.codeworkshop.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.conrad.codeworkshop.factory.services.factory.Service;

@Component
public class ScheduleTask {

	private static final int MINUTE = 60000;
	
	@Autowired
	private Service factoryService;
	
	@Scheduled(fixedRate = MINUTE)
	public void dequeueAndNotify() {
		System.out.println("processing...");
		factoryService.dequeueAndNotify();
	}
}
