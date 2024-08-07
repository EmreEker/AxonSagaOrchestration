package com.eeker.ProductsService.query;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRestmodel {

	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
}
