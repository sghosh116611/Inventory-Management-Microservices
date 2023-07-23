package com.personalproject.inventoryservice.service.dto;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {
	private String skuCode;
	private int quantity;
	private BigDecimal price;
}
