package com.fossil.tradeshow.repository;

import com.fossil.tradeshow.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    boolean existsBySku(String sku);

    Optional<Product> findBySkuOrEanOrUpcOrJan(String sku, String ean, String upc, String jpa);
}
