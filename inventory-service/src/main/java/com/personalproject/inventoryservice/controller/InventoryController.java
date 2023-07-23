package com.personalproject.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.personalproject.inventoryservice.service.InventoryService;
import com.personalproject.inventoryservice.service.dto.InventoryRequest;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	@GetMapping("/{sku-code}")
	@ResponseStatus(code = HttpStatus.OK)
	public boolean findBySkuCode(@PathVariable("sku-code") String skuCode) {
		return inventoryService.isInStock(skuCode);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<String> createStock(@RequestBody InventoryRequest inventoryRequest) {
		inventoryService.createStock(inventoryRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("Stock created");

	}

}
