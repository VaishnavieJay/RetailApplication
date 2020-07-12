/**
 * 
 */
package com.tgt.myretail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tgt.myretail.dto.Product;
import com.tgt.myretail.orchestrator.ProductOrchestrator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
public class ProductController {
	
	private ProductOrchestrator productOrchestrator;

	//to be accessed from JUnit test
	@Autowired
	public ProductController(ProductOrchestrator productOrchestrator) {
		this.productOrchestrator = productOrchestrator;
	}

	@GetMapping(value= "/product/{product-id}", produces = {"application/json"})
	public Product getByProductId(@PathVariable(value= "product-id") int id){
		log.info("ProductController: getByProductId invoked");
		return productOrchestrator.getProductDetails(id);
	}
	
	@PutMapping(value="/product/{product-id}", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public void updateProductPrice(@RequestBody Product product,@PathVariable(value= "product-id") int id) {
		productOrchestrator.updateProductPrice(product, id);
		log.info("ProductController: updateProductPrice price details updated");
	}
}
