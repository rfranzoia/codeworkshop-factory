package de.conrad.codeworkshop.factory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import de.conrad.codeworkshop.factory.services.factory.Service;

/**
 * @author Andreas Hartmann
 */
@SpringBootApplication
@EnableAsync
public class FactoryApplication {	
	
	@Autowired
	private Service factoryService;
	
	@Bean
	public Executor taskExecutor() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable task = () -> {
			try {
				System.out.println("processing...");
				factoryService.dequeueAndNotify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.MINUTES);
		return executor;
	}
}
