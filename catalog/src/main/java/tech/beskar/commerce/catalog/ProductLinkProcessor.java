package tech.beskar.commerce.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductLinkProcessor implements RepresentationModelProcessor<RepositoryLinksResource> {

    private final RepositoryEntityLinks entityLinks;
    private final LinkRelationProvider relProvider;

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.ResourceProcessor#process(org.springframework.hateoas.ResourceSupport)
     */
    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {

        Link products = entityLinks.linkToCollectionResource(Product.class);
        UriTemplate template = UriTemplate.of(products.getHref().concat("/{id}"));

        resource.add(Link.of(template, relProvider.getItemResourceRelFor(Product.class)));

        return resource;
    }
}
