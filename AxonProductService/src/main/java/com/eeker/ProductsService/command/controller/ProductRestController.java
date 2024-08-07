package com.eeker.ProductsService.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeker.ProductsService.command.CreateProductCommand;
import com.eeker.ProductsService.command.DeleteProductCommand;
import com.eeker.ProductsService.command.UpdateProductCommand;

@RestController
@RequestMapping("/products")
public class ProductRestController {

	@Autowired
	private final CommandGateway commandGateway;
	
	
	public ProductRestController(CommandGateway commandGateway) {
		this.commandGateway=commandGateway;
	}

	@PostMapping
	public String createProduct(@RequestBody ProductRestModel productRestModel) {
		
		
		CreateProductCommand createProductCommand = CreateProductCommand.builder().price(productRestModel.getPrice())
				.quantity(productRestModel.getQuantity()).title(productRestModel.getTitle())
				.productId(UUID.randomUUID().toString()).build();
		String value;
		try {
		 value = commandGateway.sendAndWait(createProductCommand);
		 return value;
		}
		catch(Exception ex) {
			value=ex.getLocalizedMessage();
		}
		return value;

	}
	
	@PatchMapping
	public String updateProduct(@RequestBody ProductRestUpdateModel productUpdateModel) {
		
		
		UpdateProductCommand updateProductCommand = UpdateProductCommand.builder().price(productUpdateModel.getPrice())
				.quantity(productUpdateModel.getQuantity()).title(productUpdateModel.getTitle())
				.productId(productUpdateModel.getProductId()).build();
		String value;
		try {
		 value = commandGateway.sendAndWait(updateProductCommand);
		 return value;
		}
		catch(Exception ex) {
			value=ex.getLocalizedMessage();
		}
		return value;

	}
	
	@DeleteMapping
	public String deleteProduct(@RequestBody UpdateProductCommand deleteProductRestModel) {
		
		
		DeleteProductCommand deleteProductCommand = DeleteProductCommand.builder().productId(deleteProductRestModel.getProductId()).build();
		String value;
		try {
		 value = commandGateway.sendAndWait(deleteProductCommand);
		 return value;
		}
		catch(Exception ex) {
			value=ex.getLocalizedMessage();
		}
		return value;

	}

}
