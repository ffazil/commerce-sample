package tech.beskar.commerce.inventory.api;

import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class InventoryResourceProcessor implements RepresentationModelProcessor<RepositoryLinksResource> {

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.ResourceProcessor#process(org.springframework.hateoas.ResourceSupport)
     */
    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {

        resource.add(linkTo(methodOn(InventoryController.class).shipment()).withRel("shipment"));

        return resource;
    }
}
