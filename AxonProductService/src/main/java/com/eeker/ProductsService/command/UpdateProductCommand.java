package com.eeker.ProductsService.command;



import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UpdateProductCommand {

	
	
		@TargetAggregateIdentifier
		private String productId;
		private String title;
		private BigDecimal price;
		private Integer quantity;
	}

	

