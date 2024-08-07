package com.eeker.OrdersService.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eeker.OrdersService.core.data.OrderEntity;
import com.eeker.OrdersService.core.data.OrdersRepository;
import com.eeker.OrdersService.core.model.OrderSummary;

@Component
public class OrderQueriesHandler {
	
	@Autowired
	OrdersRepository orderRepository;

	@QueryHandler
	public OrderSummary findOrder(FindOrderQuery findOrderQuery) {
		OrderEntity orderEntity=orderRepository.findByOrderId(findOrderQuery.getOrderId());
		return new OrderSummary(orderEntity.getOrderId(), orderEntity.getOrderStatus(),"");
	}
	
}
