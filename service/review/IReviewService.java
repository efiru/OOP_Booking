package service.review;

import model.review.Review;

import java.util.List;

public interface IReviewService {
    Review addReview(int guestId, int hotelId, int stars, String comment);
    List<Review> getReviewsForHotel(int hotelId);
}
