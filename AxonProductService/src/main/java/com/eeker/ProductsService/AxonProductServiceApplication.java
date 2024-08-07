package com.eeker.ProductsService;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.eeker.ProductsService.command.interceptors.CreateProductCommandInterceptor;
import com.eeker.ProductsService.config.AxonConfig;
import com.eeker.ProductsService.error.ProductsServiceEventsErrorHandler;

@EnableDiscoveryClient
@SpringBootApplication
@Import({ AxonConfig.class })
public class AxonProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AxonProductServiceApplication.class, args);
	}
	
	@Autowired
	public void registerCreateProductIntercepter(ApplicationContext context,CommandBus commandbus) {
		commandbus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler("product-group",conf->new ProductsServiceEventsErrorHandler());
	}
	
	@Bean(name="productSnapshotTriggerDefinition")
	public SnapshotTriggerDefinition productSnapshotTriggerDefinition(Snapshotter snapshotter) {
		return new EventCountSnapshotTriggerDefinition(snapshotter, 5);
	}
}
