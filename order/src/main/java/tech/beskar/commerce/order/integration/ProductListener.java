package tech.beskar.commerce.order.integration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.JsonPath;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tech.beskar.commerce.order.ProductInfo;
import tech.beskar.commerce.order.ProductInfoRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class ProductListener {

    private final @NonNull ProductInfoRepository productInfos;

    @KafkaListener(topics = "products")
    void on(ProductAdded event) {

        UUID productNumber = event.getProductNumber();

        productInfos.save(ProductInfo.of(ProductInfo.ProductNumber.of(productNumber), event.getDescription(), event.getPrice()));
    }

    interface ProductAdded {

        @JsonPath("$.product.id")
        UUID getProductNumber();

        @JsonPath("$.product.name")
        String getDescription();

        @JsonPath("$.product.price")
        BigDecimal getPrice();
    }
}
