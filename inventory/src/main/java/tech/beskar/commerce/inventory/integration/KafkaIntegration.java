package tech.beskar.commerce.inventory.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.JsonPath;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.beskar.commerce.inventory.Inventory;
import tech.beskar.commerce.inventory.InventoryItem;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
class KafkaIntegration {

    private final Inventory inventory;

    /**
     * Creates a new {@link InventoryItem} for the product that was added.
     *
     * @param message will never be {@literal null}.
     */
    @KafkaListener(topics = "products")
    public void onProductAdded(ProductAdded message) throws IOException {

        Optional<InventoryItem> item = inventory.findByProductId(message.getProductId());

        if (item.isPresent()) {
            log.info("Inventory item for product {} already available!", message.getProductId());
            return;
        }

        log.info("Creating inventory item for product {}.", message.getProductId());

        item.orElseGet(() -> inventory.save(InventoryItem.of(message.getProductId(), message.getName(), 0L)));
    }

    /**
     * Updates the current stock by reducing the inventory items by the amount of the individual line items in the order.
     *
     * @param message will never be {@literal null}.
     */
    @Transactional
    @KafkaListener(topics = "orders")
    void on(OrderCompleted message) {

        log.info("Order completed: {}", message.getOrderId().toString());

        message.getLineItems().stream() //
                .peek(it -> log.info("Decreasing quantity of product {} by {}.", it.getProductNumber(), it.getQuantity()))
                .forEach(it -> inventory.updateInventoryItem(it.getProductNumber(), it.getQuantity()));
    }

    interface ProductAdded {

        @JsonPath("$.product.id")
        UUID getProductId();

        @JsonPath("$.product.name")
        String getName();
    }

    interface OrderCompleted {

        @JsonPath("$.order.id")
        UUID getOrderId();

        @JsonPath("$.order.lineItems")
        Collection<LineItem> getLineItems();

        interface LineItem {

            UUID getProductNumber();

            Long getQuantity();
        }
    }
}
