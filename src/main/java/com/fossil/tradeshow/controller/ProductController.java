package com.fossil.tradeshow.controller;

import com.fossil.tradeshow.exceptions.ProductNotFoundException;
import com.fossil.tradeshow.model.Product;
import com.fossil.tradeshow.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/addProduct")
    public ResponseEntity<Product> createNewProduct(@RequestBody Product product) {
        Product createdProduct = productService.addNewProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PostMapping(value = "/searchUniqueProduct")
    public ResponseEntity<Product> searchUniqueProduct(@RequestBody Map<String, String> payload) {
        String searchValue = payload.get("searchValue");
        Optional<Product> product = productService.searchUniqueProduct(searchValue);

        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found with search value: " + searchValue);
        }

        return ResponseEntity.ok(product.get());
    }
}
