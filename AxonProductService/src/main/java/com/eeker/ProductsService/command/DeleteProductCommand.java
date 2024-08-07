package com.eeker.ProductsService.command;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class DeleteProductCommand {

	@TargetAggregateIdentifier
	private String productId;
	
}
