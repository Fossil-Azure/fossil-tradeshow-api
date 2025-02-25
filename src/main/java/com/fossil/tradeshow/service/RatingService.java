package com.fossil.tradeshow.service;

import com.fossil.tradeshow.model.Rating;
import com.fossil.tradeshow.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public void saveOrUpdateRating(String productSku, int rating, String userId) {
        Optional<Rating> existingRating = ratingRepository.findByProductSkuAndUserId(productSku, userId);

        if (existingRating.isPresent()) {
            Rating ratingToUpdate = existingRating.get();
            ratingToUpdate.setRating(rating);
            ratingRepository.save(ratingToUpdate);
        } else {
            Rating newRating = new Rating(productSku, rating, userId);
            ratingRepository.save(newRating);
        }
    }

    public double getAverageRating(String productSku) {
        var ratings = ratingRepository.findAll()
                .stream().filter(r -> r.getProductSku().equals(productSku))
                .toList();

        if (ratings.isEmpty()) return 0;

        return ratings.stream().mapToInt(Rating::getRating).average().orElse(0);
    }
}
