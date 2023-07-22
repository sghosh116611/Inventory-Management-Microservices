package com.personalproject.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personalproject.productservice.dto.ProductResponse;
import com.personalproject.productservice.dto.ProductRequest;
import com.personalproject.productservice.model.Product;
import com.personalproject.productservice.model.Product.ProductBuilder;
import com.personalproject.productservice.repository.ProductServiceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private ProductServiceRepository productServiceRepository;

	public String createProduct(ProductRequest productRequest) {
		Product product = Product.builder().name(changeToCamelCase(productRequest.getName()))
				.description(productRequest.getDescription()).price(productRequest.getPrice()).build();

		productServiceRepository.save(product);
		log.info("Product {} is saved", product.getId());
		return product.getId();
	}

	public List<ProductResponse> getProduct() {
		List<Product> product = productServiceRepository.findAll();

		return product.stream().map(p -> mapToProductResponse(p)).toList();
	}

	private ProductResponse mapToProductResponse(Product p) {
		return ProductResponse.builder().id(p.getId()).name(p.getName()).description(p.getDescription())
				.price(p.getPrice()).build();
	}

	private String changeToCamelCase(String input) {
		String words[] = input.split(" ");
		String result = "";
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			if (word.length() > 1)
				result += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";
			else
				result += word.charAt(0) + " ";
		}
		return result.trim();
	}
}
