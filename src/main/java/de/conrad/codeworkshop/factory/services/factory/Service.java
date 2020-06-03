package de.conrad.codeworkshop.factory.services.factory;

import de.conrad.codeworkshop.factory.services.order.api.Order;
import de.conrad.codeworkshop.factory.services.order.api.OrderStatus;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Andreas Hartmann
 */
@org.springframework.stereotype.Service("factoryService")
public class Service {

	private static final int FIVE_SECONDS = 5000;
	
	@Autowired
	private de.conrad.codeworkshop.factory.services.notification.Service notificationService;
	
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ConcurrentLinkedQueue<Order> manufacturingQueue = new ConcurrentLinkedQueue<>();

    void enqueue(final Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        manufacturingQueue.add(order);
    }
    
    public void dequeueAndNotify() {
    	Runnable dequeueAndNotify = () -> {
			try {
				if (!manufacturingQueue.isEmpty()) {
					Order order = manufacturingQueue.poll();
					order.setStatus(OrderStatus.COMPLETED);
					Thread.sleep(FIVE_SECONDS);
					notificationService.notifyCustomer(order);
				}
			} catch (Throwable t) {
				// log the error
			}
    	};
    	
    	new Thread(dequeueAndNotify).start();
    	
    }
}
