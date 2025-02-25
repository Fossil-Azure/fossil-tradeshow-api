package com.fossil.tradeshow.repository;

import com.fossil.tradeshow.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RatingRepository extends MongoRepository<Rating, String> {
    Optional<Rating> findByProductSkuAndUserId(String productSku, String userId);
}