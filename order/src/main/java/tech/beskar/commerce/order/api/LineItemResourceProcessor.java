package tech.beskar.commerce.order.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.hypermedia.RemoteResource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import tech.beskar.commerce.order.Order;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class LineItemResourceProcessor implements RepresentationModelProcessor<EntityModel<Order.LineItem>> {

    private final @NonNull RemoteResource productResource;

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.server.RepresentationModelProcessor#process(org.springframework.hateoas.RepresentationModel)
     */
    @Override
    public EntityModel<Order.LineItem> process(EntityModel<Order.LineItem> resource) {

        Order.LineItem content = resource.getContent();

        Optional.ofNullable(productResource.getLink())//
                .map(it -> it.expand(content.getProductNumber().getValue())) //
                .map(it -> it.withRel("product")) //
                .ifPresent(it -> resource.add(it));

        return resource;
    }
}
