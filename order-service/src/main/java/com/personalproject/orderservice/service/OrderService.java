package com.personalproject.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.personalproject.orderservice.config.WebClientConfig;
import com.personalproject.orderservice.dto.InventoryRequest;
import com.personalproject.orderservice.dto.InventoryResponse;
import com.personalproject.orderservice.dto.OrderLineItemDto;
import com.personalproject.orderservice.dto.OrderRequest;
import com.personalproject.orderservice.model.Order;
import com.personalproject.orderservice.model.OrderLineItem;
import com.personalproject.orderservice.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private WebClient webClient;

	public int createOrder(OrderRequest orderRequest) {

		List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItem().stream().map(this::mapToDto).toList();
		Order order = Order.builder().orderNumber(UUID.randomUUID().toString()).orderLineItemsList(orderLineItems)
				.build();
		log.info("Order obj -> " + order.toString());

		List<String> skuCodes = order.getOrderLineItemsList().stream().map(orderLineItem -> orderLineItem.getSkuCode())
				.toList();

//		Calling inventory API for checking stock of the skuCodes
		InventoryResponse[] inventoryResponses = webClient.get()
				.uri("http://localhost:8082/api/inventory/sku",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve().bodyToMono(InventoryResponse[].class).block();

		// Checking if every SKU has a stock
		boolean skusPresent = Arrays.stream(inventoryResponses)
				.allMatch(inventoryResponse -> inventoryResponse.isPresent())
				&& inventoryResponses.length == order.getOrderLineItemsList().size();

		if (skusPresent) {
			Order savedOrder = orderRepository.save(order);
			log.info("Order {} created", savedOrder.getId());
			if (savedOrder.getOrderNumber() != null && !savedOrder.getOrderNumber().equalsIgnoreCase("null")) {
				log.info("----------- Updating Items ----------");
				updateInventoryStockForSKU(orderLineItems, webClient);
			}
			return savedOrder.getId();
		} else {
			throw new IllegalArgumentException("Product is not in stock. Please try again later!");
		}

	}

	private OrderLineItem mapToDto(OrderLineItemDto orderLineItem) {
		// TODO Auto-generated method stub
		return OrderLineItem.builder().skuCode(orderLineItem.getSkuCode()).quantity(orderLineItem.getQuantity())
				.price(orderLineItem.getPrice()).build();
	}

	private void updateInventoryStockForSKU(List<OrderLineItem> orderLineItems, WebClient webClient) {
		List<InventoryRequest> updateItemslist = orderLineItems.stream()
				.map(orderLineItem -> mapToInventoryRequest(orderLineItem)).toList();
		System.out.println(("Update Items list ->"+updateItemslist));
		webClient.put().uri("http://localhost:8082/api/inventory/sku")
				.body(Mono.just(updateItemslist), InventoryRequest.class).retrieve().bodyToMono(Void.class).block();
	}

	private InventoryRequest mapToInventoryRequest(OrderLineItem orderLineItem) {
		return InventoryRequest.builder().quantity(orderLineItem.getQuantity())
				.price(orderLineItem.getPrice()).skuCode(orderLineItem.getSkuCode()).build();

	}

}
