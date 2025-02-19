package com.fossil.tradeshow.service;

import com.fossil.tradeshow.exceptions.UserAlreadyExistsException;
import com.fossil.tradeshow.model.Product;
import com.fossil.tradeshow.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product addNewProduct(Product product) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new Error("Product with the SKU " + product.getSku() + " already exists.");
        }
        return productRepository.save(product);
    }

    public Optional<Product> searchUniqueProduct(String searchValue) {
        return productRepository.findBySkuOrEanOrUpcOrJan(searchValue, searchValue, searchValue, searchValue);
    }

}
