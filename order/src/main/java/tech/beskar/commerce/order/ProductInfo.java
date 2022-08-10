package tech.beskar.commerce.order;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductInfo {

    ObjectId id;
    ProductNumber productNumber;
    @With
    String description;
    @With
    BigDecimal price;

    LocalDateTime created;

    public static ProductInfo of(String description, BigDecimal price) {
        return of(ProductNumber.of(UUID.randomUUID()), description, price);
    }

    public static ProductInfo of(ProductNumber number, String description, BigDecimal price) {
        return new ProductInfo(new ObjectId(), number, description, price, LocalDateTime.now());
    }

    @Value(staticConstructor = "of")
    @JsonSerialize(using = ToStringSerializer.class)
    public static class ProductNumber {

        @NonNull UUID value;

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return value.toString();
        }
    }
}
