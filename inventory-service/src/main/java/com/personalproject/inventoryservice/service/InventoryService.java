package com.personalproject.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personalproject.inventoryservice.model.Inventory;
import com.personalproject.inventoryservice.repository.InventoryRepository;
import com.personalproject.inventoryservice.service.dto.InventoryRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	public boolean isInStock(String skuCode) {
		return inventoryRepository.findBySkuCode(skuCode).isPresent();
	}

	public void createStock(InventoryRequest inventoryRequest) {

		Inventory inventory = Inventory.builder().skuCode(inventoryRequest.getSkuCode())
				.quantity(inventoryRequest.getQuantity()).price(inventoryRequest.getPrice()).build();
		inventoryRepository.save(inventory);
	}
}
