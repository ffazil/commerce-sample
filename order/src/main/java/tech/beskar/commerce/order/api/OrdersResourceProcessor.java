package tech.beskar.commerce.order.api;

import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class OrdersResourceProcessor implements RepresentationModelProcessor<RepositoryLinksResource> {

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.ResourceProcessor#process(org.springframework.hateoas.ResourceSupport)
     */
    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {

        resource.add(linkTo(methodOn(OrderController.class).createOrder()).withRel("create"));

        return resource;
    }
}
