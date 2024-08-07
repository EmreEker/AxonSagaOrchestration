package com.eeker.ProductsService.event;

import lombok.Data;

@Data
public class ProductDeletedEvent {
	private String productId;
}
