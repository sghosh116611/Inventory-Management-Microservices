package com.personalproject.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.personalproject.productservice.dto.ProductRequest;
import com.personalproject.productservice.dto.ProductResponse;
import com.personalproject.productservice.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductServiceController {

	@Autowired
	private ProductService productService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {
		String id = productService.createProduct(productRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("Item created with ID = " + id);
	}

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<ProductResponse> getProduct() {
		return productService.getProduct();
	}
}
