package com.eeker.ProductsService.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.estore.core.commands.CancelProductReservationCommand;
import com.appsdeveloperblog.estore.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.estore.core.events.ProductReservationCancelledEvent;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import com.eeker.ProductsService.event.ProductCreatedEvent;
import com.eeker.ProductsService.event.ProductDeletedEvent;
import com.eeker.ProductsService.event.ProductUpdateEvent;

@Aggregate(snapshotTriggerDefinition = "productSnapshotTriggerDefinition")
public class ProductAggregate {

	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;

	public ProductAggregate() {
		// Default constructor
	}

	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
		AggregateLifecycle.apply(productCreatedEvent);

	}

	@CommandHandler
	public void handle(UpdateProductCommand updateProductCommand) {
		ProductUpdateEvent productUpdateEvent = new ProductUpdateEvent();
		productUpdateEvent.setProductId(this.productId);
		BeanUtils.copyProperties(updateProductCommand, productUpdateEvent);
		AggregateLifecycle.apply(productUpdateEvent);
	}

	@CommandHandler
	public void handle(DeleteProductCommand deleteProductCommand) {
		ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent();
		productDeletedEvent.setProductId(deleteProductCommand.getProductId());
		AggregateLifecycle.apply(productDeletedEvent);
	}

	@CommandHandler
	public void handle(ReserveProductCommand reserveProductCommand) {

		if (quantity < reserveProductCommand.getQuantity()) {
			throw new IllegalArgumentException("Insufficient number of items in stock");
		}
		ProductReservedEvent productReserverdEvent = ProductReservedEvent.builder()
				.orderId(reserveProductCommand.getOrderId()).productId(reserveProductCommand.getProductId())
				.quantity(reserveProductCommand.getQuantity()).userId(reserveProductCommand.getUserId()).build();

		AggregateLifecycle.apply(productReserverdEvent);
	}

	@CommandHandler
	public void handle(CancelProductReservationCommand cancelProductReservationCommand) {

		ProductReservationCancelledEvent productReservationCancelledEvent = ProductReservationCancelledEvent.builder()
				.orderId(cancelProductReservationCommand.getOrderId())
				.productId(cancelProductReservationCommand.getProductId())
				.quantity(cancelProductReservationCommand.getQuantity())
				.reason(cancelProductReservationCommand.getReason())
				.userId(cancelProductReservationCommand.getUserId())
				.build();

		AggregateLifecycle.apply(productReservationCancelledEvent);

	}

	@EventSourcingHandler
	public void on(ProductReservationCancelledEvent productReservationCancelledEvent) {
		this.quantity += productReservationCancelledEvent.getQuantity();
	}

	@EventSourcingHandler
	public void on(ProductReservedEvent productReservedEvent) {
		this.quantity -= productReservedEvent.getQuantity();
	}

	@EventSourcingHandler
	public void on(ProductCreatedEvent productCreatedEvent) {
		this.productId = productCreatedEvent.getProductId();
		this.title = productCreatedEvent.getTitle();
		this.quantity = productCreatedEvent.getQuantity();
		this.price = productCreatedEvent.getPrice();

	}

	@EventSourcingHandler
	public void on(ProductDeletedEvent productDeletedEvent) {
		AggregateLifecycle.markDeleted();
	}

	@EventSourcingHandler
	public void on(ProductUpdateEvent productUpdateEvent) {
		this.productId = productUpdateEvent.getProductId();
		this.title = productUpdateEvent.getTitle();
		this.quantity = productUpdateEvent.getQuantity();
		this.price = productUpdateEvent.getPrice();
	}
}
