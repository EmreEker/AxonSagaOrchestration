package com.eeker.ProductsService.command.controller;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRestUpdateModel {

	private String productId;

	private String title;
	private BigDecimal price;
	private Integer quantity;

}
