package com.fossil.tradeshow.repository;

import com.fossil.tradeshow.model.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrdersRepository extends MongoRepository<Orders, String> {
    List<Orders> findByEmailId(String userEmail);
}