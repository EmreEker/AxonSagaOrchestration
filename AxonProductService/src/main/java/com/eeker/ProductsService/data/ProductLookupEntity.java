package com.eeker.ProductsService.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="productlookup")
public class ProductLookupEntity implements Serializable {

	
	private static final long serialVersionUID = 2758881940181647180L;
	
	@Id
	private String productId;
	
	@Column(unique=true)
	private String title;

}
