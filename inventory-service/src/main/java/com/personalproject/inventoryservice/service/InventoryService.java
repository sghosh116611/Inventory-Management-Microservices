package com.personalproject.inventoryservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personalproject.inventoryservice.dto.InventoryRequest;
import com.personalproject.inventoryservice.dto.InventoryResponse;
import com.personalproject.inventoryservice.model.Inventory;
import com.personalproject.inventoryservice.repository.InventoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	public Inventory getItemBySKU(String skuCode) {
		return inventoryRepository.findBySkuCode(skuCode).get();
	}

	public List<InventoryResponse> isInStock(List<String> skuCode) {
		return inventoryRepository.findBySkuCodeIn(skuCode).stream()
				.map(inventoryItem -> InventoryResponse.builder().id(inventoryItem.getId())
						.skuCode(inventoryItem.getSkuCode()).isPresent(inventoryItem.getQuantity() > 0).build())
				.toList();
	}

	public void createStock(InventoryRequest inventoryRequest) {

		Inventory inventory = Inventory.builder().skuCode(inventoryRequest.getSkuCode())
				.quantity(inventoryRequest.getQuantity()).price(inventoryRequest.getPrice()).build();
		inventoryRepository.save(inventory);
	}

	public void updateInventory(List<InventoryRequest> updateInventoryItems) {
		for (InventoryRequest item : updateInventoryItems) {
			Optional<Inventory> optionalInventoryUpdate = inventoryRepository.findBySkuCode(item.getSkuCode());
			Inventory inventoryUpdate = optionalInventoryUpdate.get();
			int updatedQuantity = inventoryUpdate.getQuantity() - item.getQuantity();
			if (updatedQuantity < 0) {
				throw new IllegalArgumentException("Don't have sufficient quantity for" + item.getSkuCode()
						+ " Please try again after some time!");
			}
			inventoryUpdate.setQuantity(updatedQuantity);
			inventoryRepository.save(inventoryUpdate);
		}

	}
}
