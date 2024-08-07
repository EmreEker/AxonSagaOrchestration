package com.eeker.ProductsService.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;

import com.eeker.ProductsService.data.ProductLookupEntity;
import com.eeker.ProductsService.data.ProductLookupRepository;
import com.eeker.ProductsService.event.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {
	
	private final ProductLookupRepository productLookupRepository;

	public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
		
		this.productLookupRepository = productLookupRepository;
	}
	
	
	@EventHandler
	public void on(ProductCreatedEvent event) {
		
		ProductLookupEntity productLookupEntity=new ProductLookupEntity(event.getProductId(),event.getTitle());
		productLookupRepository.save(productLookupEntity);
		
	}
	@ResetHandler
	public void reset() {
		productLookupRepository.deleteAll();
	}

}
