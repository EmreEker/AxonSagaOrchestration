package com.eeker.OrdersService.core.events;

import com.eeker.OrdersService.core.model.OrderStatus;

import lombok.Value;

@Value
public class OrderApprovedEvent {

	public final String orderId;
	private final OrderStatus orderStatus=OrderStatus.APPROVED;
	
}
