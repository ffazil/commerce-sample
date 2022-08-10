package tech.beskar.commerce.order.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Component;
import tech.beskar.commerce.order.Order;

@Component
@RequiredArgsConstructor
class KafkaIntegration {

    private final @NonNull KafkaOperations<Object, Object> kafka;
    private final @NonNull ObjectMapper mapper;

    @EventListener
    void on(Order.OrderCompleted event) throws Exception {
        kafka.send("orders", mapper.writeValueAsString(event));
    }
}
