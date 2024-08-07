package com.eeker.ProductsService.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.eeker.ProductsService.command.controller.ProductRestModel;
import com.eeker.ProductsService.data.ProductEntity;
import com.eeker.ProductsService.data.ProductRepository;

@Component
public class ProductsQueryHandler {

	private final ProductRepository productRepository;
	
	public ProductsQueryHandler(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}
	
	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductsQuery query){
		
		List<ProductRestModel> productRest=new ArrayList<>();
		List<ProductEntity> storedProducts=productRepository.findAll();
		for(ProductEntity productEntity:storedProducts) {
			ProductRestModel productRestModel=new ProductRestModel();
			BeanUtils.copyProperties(productEntity, productRestModel);
			productRest.add(productRestModel);
		}
		
		return productRest;
	}
	
}
