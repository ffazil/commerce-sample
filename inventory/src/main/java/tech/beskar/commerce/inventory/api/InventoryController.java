package tech.beskar.commerce.inventory.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.beskar.commerce.inventory.Inventory;

@RestController
@RequiredArgsConstructor
class InventoryController {

    private final @NonNull Inventory inventory;

    @PostMapping("shipment")
    HttpEntity<?> shipment() {

        inventory.registerShipment(5);

        return ResponseEntity.ok().build();
    }
}
