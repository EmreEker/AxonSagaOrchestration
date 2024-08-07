
package com.eeker.ProductsService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.core.events.ProductReservationCancelledEvent;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import com.eeker.ProductsService.data.ProductEntity;
import com.eeker.ProductsService.data.ProductRepository;
import com.eeker.ProductsService.event.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

	@Autowired
	ProductRepository productRepository;

	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception ex) throws Exception {
		System.out.println(ex.getMessage());
		throw ex;
	}

	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException ex) throws Exception {
		System.out.println(ex.getMessage());
		throw ex;
	}

	@EventHandler
	public void on(ProductCreatedEvent productCreatedEvent) {

		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(productCreatedEvent, productEntity);
		productRepository.save(productEntity);
	}

	@EventHandler
	public void on(ProductReservedEvent productReservedEvent) {
		ProductEntity productEntity = productRepository.findByProductId(productReservedEvent.getProductId());
		productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());

		productRepository.save(productEntity);
	}

	@EventHandler
	public void on(ProductReservationCancelledEvent productReservationCancelledEvent) {
		ProductEntity currentlyStoredProduct=productRepository .findByProductId(productReservationCancelledEvent.getProductId());
		int newQuantity=currentlyStoredProduct.getQuantity()+productReservationCancelledEvent.getQuantity();
		currentlyStoredProduct.setQuantity(newQuantity);
		productRepository.save(currentlyStoredProduct);
	}
	
	@ResetHandler
	public void reset() {
		productRepository.deleteAll();
	}
	
}
