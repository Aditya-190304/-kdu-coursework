package com.cinestream.model;

import java.util.ArrayList;
import java.util.List;

public record Film(String id, String title, String genre, String directorId, List<Review> reviews) {
    public Film addReview(Review review) {
        var newReviews = new ArrayList<>(this.reviews);
        newReviews.add(review);
        return new Film(id, title, genre, directorId, newReviews);
    }
}