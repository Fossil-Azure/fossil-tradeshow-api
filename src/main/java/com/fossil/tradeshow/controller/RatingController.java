package com.fossil.tradeshow.controller;

import com.fossil.tradeshow.model.Rating;
import com.fossil.tradeshow.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/product-rating")
    public String saveOrUpdateRating(@RequestBody Rating rating) {
        if (rating.getRating() > 0) {
            ratingService.saveOrUpdateRating(rating.getProductSku(), rating.getRating(), rating.getUserId());
            return "Rating saved successfully!";
        }
        return "No rating provided.";
    }

    @GetMapping("/avg-rating/{productSku}")
    public double getAverageRating(@PathVariable String productSku) {
        return ratingService.getAverageRating(productSku);
    }
}
