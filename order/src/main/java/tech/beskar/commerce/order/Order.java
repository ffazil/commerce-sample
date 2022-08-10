package tech.beskar.commerce.order;

import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Value
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends AbstractAggregateRoot<Order> {

    UUID id;
    List<LineItem> lineItems;
    @With(AccessLevel.PRIVATE)
    Status status;

    public static Order newOrder() {
        return new Order(UUID.randomUUID(), new ArrayList<>(), Status.PLACED);
    }

    public Order add(ProductInfo product, long quantity) {

        if (status == Status.COMPLETED) {
            throw new IllegalStateException("Order already completed.");
        }

        lineItems.stream() //
                .filter(it -> it.hasProductNumber(product.getProductNumber())) //
                .findFirst() //
                .map(it -> it.addAmount(quantity)) //
                .orElseGet(() -> {

                    LineItem item = LineItem.of(product, quantity);
                    lineItems.add(item);

                    return item;
                });

        return this;
    }

    public Order complete() {

        Order order = withStatus(Status.COMPLETED).andEventsFrom(this);

        return order.andEvent(OrderCompleted.of(order));
    }

    private enum Status {
        PLACED, COMPLETED
    }

    @Getter
    @AllArgsConstructor
    public static class LineItem {

        private final ProductInfo.ProductNumber productNumber;
        private final BigDecimal price;
        private final String description;
        private Long quantity;

        public static LineItem of(ProductInfo info, Long quantity) {
            return new LineItem(info.getProductNumber(), info.getPrice(), info.getDescription(), quantity);
        }

        boolean hasProductNumber(ProductInfo.ProductNumber number) {
            return this.productNumber == number;
        }

        LineItem addAmount(Long delta) {

            this.quantity = this.quantity + delta;

            return this;
        }
    }

    @Value
    @RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
    public static class OrderCompleted {
        Order order;
    }
}
