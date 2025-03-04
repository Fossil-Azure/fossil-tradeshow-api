package com.fossil.tradeshow.service;

import com.fossil.tradeshow.model.Rating;
import com.fossil.tradeshow.model.RatingResponse;
import com.fossil.tradeshow.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public RatingResponse getRatingsWithAverage(String productSku) {
        List<Rating> ratings = ratingRepository.findAll()
                .stream()
                .filter(r -> r.getProductSku().equals(productSku))
                .toList();

        double averageRating = ratings.stream().mapToInt(Rating::getRating).average().orElse(0);

        return new RatingResponse(ratings, averageRating);
    }

}
