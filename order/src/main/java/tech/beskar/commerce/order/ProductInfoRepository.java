package tech.beskar.commerce.order;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "productInfos", collectionResourceRel = "productInfos")
public interface ProductInfoRepository extends PagingAndSortingRepository<ProductInfo, ObjectId> {

    Optional<ProductInfo> findByProductNumber(ProductInfo.ProductNumber number);
}
