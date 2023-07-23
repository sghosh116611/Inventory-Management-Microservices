package com.personalproject.orderservice.dto;

import java.util.List;

import com.personalproject.orderservice.model.OrderLineItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
	private List<OrderLineItemDto> orderLineItem;
}
