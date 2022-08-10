package tech.beskar.commerce.inventory;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Setter(value = AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class InventoryItem extends AbstractAggregateRoot<InventoryItem> {

    private final UUID id;

    private @Indexed(unique = true)
    UUID productId;
    private String name;
    private long amount;

    @PersistenceConstructor
    protected InventoryItem(UUID id, String name, long amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public static InventoryItem of(UUID productId, String name, long amount) {

        InventoryItem item = new InventoryItem(productId, name, amount);
        item.registerEvent(ProductOutOfStock.of(productId));

        return item;
    }

    InventoryItem decrease(long amount) {

        this.amount = this.amount - amount;

        log.info("Decreasing amount of inventory for product {} by {} to {}.", productId, amount, this.amount);

        if (this.amount < 1) {
            registerEvent(ProductOutOfStock.of(productId));
        }

        return this;
    }

    InventoryItem increase(long amount) {

        this.amount = this.amount + amount;

        return this;
    }

    @Value(staticConstructor = "of")
    public static class ProductOutOfStock {
        UUID productId;
    }
}
