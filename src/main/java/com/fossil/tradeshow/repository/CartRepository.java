package com.fossil.tradeshow.repository;

import com.fossil.tradeshow.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByEmailId(String emailId);

    void deleteByEmailId(String emailId);
}
