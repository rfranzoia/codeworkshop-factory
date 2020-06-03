package de.conrad.codeworkshop.factory.services.order.api;

import static de.conrad.codeworkshop.factory.services.order.api.OrderStatus.ACCEPTED;
import static de.conrad.codeworkshop.factory.services.order.api.OrderStatus.DECLINED;
import static de.conrad.codeworkshop.factory.services.order.api.OrderStatus.PENDING;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Andreas Hartmann
 */
public class Order {
    private List<Position> positions;
    private OrderConfirmation orderConfirmation;
    private OrderStatus status = PENDING;

    public void validate() {
        if (!positions.isEmpty() && arePositionsValid() && status == PENDING) {
            status = ACCEPTED;
        } else {
            status = DECLINED;
        }
    }

    public boolean arePositionsValid() {
    	
    	final Predicate<Position> validateProductIdSize = position -> {
    		final int size = position.getProductId().toString().length();
    		return (size >= 6 && size <= 9);
    	};
    	
    	
    	final Predicate<Position> validateQuantityIsDivisibleByTen = position -> 
    					position.getQuantity().doubleValue() % 10 == 0;
    	
    	final Predicate<Position> validateLessOneGreaterZero = position -> 
    					position.getQuantity().doubleValue() > 0.0 && position.getQuantity().doubleValue() < 1.0;
    					
		final double fixedValue = 42.42;
    	Predicate<Position> validateFixedValue = position -> position.getQuantity().doubleValue() == fixedValue;
    	
    	return positions.stream().allMatch(validateProductIdSize
    										.and(validateQuantityIsDivisibleByTen
													.or(validateLessOneGreaterZero)
													.or(validateFixedValue)));
    }
    
    public void setOrderConfirmation(final OrderConfirmation orderConfirmation) {
        this.orderConfirmation = orderConfirmation;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
}
