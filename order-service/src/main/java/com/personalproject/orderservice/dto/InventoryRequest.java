package com.personalproject.orderservice.dto;

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
	private Long id;
	private String skuCode;
	private int quantity;
	private BigDecimal price;
}
