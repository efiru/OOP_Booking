package controller;

import model.review.Review;
import org.springframework.web.bind.annotation.*;
import service.review.IReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final IReviewService reviewService;

    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getReviews(@RequestParam int hotelId) {
        return reviewService.getReviewsForHotel(hotelId);
    }

    @PostMapping
    public Review addReview(@RequestBody AddReviewRequest req) {
        return reviewService.addReview(req.guestId(), req.hotelId(), req.stars(), req.comment());
    }

    record AddReviewRequest(int guestId, int hotelId, int stars, String comment) {}
}
