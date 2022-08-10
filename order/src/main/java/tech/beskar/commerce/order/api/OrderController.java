package tech.beskar.commerce.order.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.beskar.commerce.order.Order;
import tech.beskar.commerce.order.OrderRepository;
import tech.beskar.commerce.order.ProductInfo;
import tech.beskar.commerce.order.ProductInfoRepository;

@RestController
@RequiredArgsConstructor
class OrderController {

    private final @NonNull ProductInfoRepository productInfos;
    private final @NonNull OrderRepository orders;
    private final @NonNull RepositoryEntityLinks links;

    @PostMapping("/orders/new")
    HttpEntity<?> createOrder() {

        Iterable<ProductInfo> infos = productInfos.findAll(Sort.by("createdDate").descending());

        ProductInfo info = Streamable.of(infos).stream() //
                .findFirst() //
                .orElseThrow(() -> new IllegalStateException("No ProductInfo found!"));

        Order order = Order.newOrder();
        order.add(info, 2);

        orders.save(order.complete());

        return ResponseEntity //
                .created(links.linkForItemResource(Order.class, order.getId()).toUri()) //
                .build();
    }
}
