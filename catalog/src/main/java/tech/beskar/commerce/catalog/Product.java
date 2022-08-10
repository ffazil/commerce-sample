package tech.beskar.commerce.catalog;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NON_PRIVATE;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@JsonAutoDetect(creatorVisibility = NON_PRIVATE)
public class Product extends AbstractAggregateRoot<Product> {

    BigDecimal price;
    private final UUID id;
    private final String name;
    private final String manufacturer;

    protected Product(String name, String manufacturer, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    @PersistenceConstructor
    protected Product(UUID id, String name, String manufacturer, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    @JsonCreator
    public static Product of(String name, String manufacturer, BigDecimal price) {

        Product product = new Product(name, manufacturer, price);
        product.registerEvent(ProductAdded.of(product));

        return product;
    }

    interface Typed {

        default String getType() {
            return getClass().getSimpleName();
        }
    }

    @Value(staticConstructor = "of")
    public static class ProductAdded implements Typed {

        Product product;

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return "ProductAdded";
        }
    }
}
