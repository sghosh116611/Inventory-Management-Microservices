package com.personalproject.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personalproject.orderservice.dto.OrderLineItemDto;
import com.personalproject.orderservice.dto.OrderRequest;
import com.personalproject.orderservice.model.Order;
import com.personalproject.orderservice.model.OrderLineItem;
import com.personalproject.orderservice.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	public int createOrder(OrderRequest orderRequest) {

		List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItem().stream().map(this::mapToDto).toList();
		Order order = Order.builder().orderNumber(UUID.randomUUID().toString()).orderLineItemsList(orderLineItems)
				.build();
		log.info("Order obj -> " + order.toString());
		Order savedOrder = orderRepository.save(order);
		log.info("Order {} created", savedOrder.getId());
		return savedOrder.getId();
	}

	private OrderLineItem mapToDto(OrderLineItemDto orderLineItem) {
		// TODO Auto-generated method stub
		return OrderLineItem.builder().skuCode(orderLineItem.getSkuCode()).quantity(orderLineItem.getQuantity())
				.price(orderLineItem.getPrice()).build();
	}

}
