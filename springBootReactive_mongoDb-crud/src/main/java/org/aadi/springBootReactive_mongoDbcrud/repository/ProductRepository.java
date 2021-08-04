package org.aadi.springBootReactive_mongoDbcrud.repository;

import org.aadi.springBootReactive_mongoDbcrud.dto.ProductDto;
import org.aadi.springBootReactive_mongoDbcrud.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product,String> {

    Flux<ProductDto> findByPriceBetween(Range<Double> priceRange);


}
