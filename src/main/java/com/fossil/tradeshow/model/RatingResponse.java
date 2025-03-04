package com.fossil.tradeshow.model;

import java.util.List;

public class RatingResponse {

    private List<Rating> ratings;
    private double averageRating;

    public RatingResponse(List<Rating> ratings, double averageRating) {
        this.ratings = ratings;
        this.averageRating = averageRating;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public double getAverageRating() {
        return averageRating;
    }
}
