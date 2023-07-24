package com.personalproject.inventoryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.personalproject.inventoryservice.dto.InventoryRequest;
import com.personalproject.inventoryservice.dto.InventoryResponse;
import com.personalproject.inventoryservice.model.Inventory;
import com.personalproject.inventoryservice.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	@GetMapping("/{skuCode}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Inventory> getInventoryItem(@PathVariable("skuCode") String skuCode) {
		Inventory inventoryItem = inventoryService.getItemBySKU(skuCode);
		return ResponseEntity.status(HttpStatus.OK).body(inventoryItem);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<String> createStock(@RequestBody InventoryRequest inventoryRequest) {
		inventoryService.createStock(inventoryRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("Stock created");

	}

	@GetMapping("/sku")
	@ResponseStatus(code = HttpStatus.OK)
	public List<InventoryResponse> findBySkuCode(@RequestParam("skuCode") List<String> skuCode) {
		return inventoryService.isInStock(skuCode);
	}

	@PutMapping("/sku")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<String> updateStock(@RequestBody List<InventoryRequest> inventoryRequest) {
		inventoryService.updateInventory(inventoryRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("Stock updated");

	}
}
