package com.eeker.ProductsService.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

	ProductEntity	findByProductId(String id);
	ProductEntity	findByProductIdOrTitle(String id,String title);
}
