package tech.beskar.commerce.inventory;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface Inventory extends CrudRepository<InventoryItem, UUID> {

    /**
     * Returns the {@link example.sos.messaging.inventory.InventoryItem} with the given product identifier.
     *
     * @param id
     * @return
     */
    Optional<InventoryItem> findByProductId(UUID id);

    /**
     * @param productId the identifer of the product to be updated.
     * @param amount    the amount to substract from the current stock.
     */
    default void updateInventoryItem(UUID productId, long amount) {

        InventoryItem item = findByProductId(productId) //
                .orElseThrow(() -> new IllegalArgumentException("Unknown product!"));

        save(item.decrease(amount));
    }

    default void registerShipment(long amount) {
        findAll().forEach(it -> save(it.increase(amount)));
    }
}
