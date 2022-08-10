package tech.beskar.commerce.catalog.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Component;
import tech.beskar.commerce.catalog.Product;


@Slf4j
@Configuration
@RequiredArgsConstructor
@Component
class KafkaIntegration {

	private final KafkaOperations<Object, Object> kafka;
	private final ObjectMapper mapper;

	@EventListener
	void on(Product.ProductAdded event) throws Exception {

		String payload = mapper.writeValueAsString(event);

		log.info("Publishing {} to Kafka…", payload);

		kafka.send("products", payload);
	}
}
