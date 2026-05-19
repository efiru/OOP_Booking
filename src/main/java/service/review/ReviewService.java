package service.review;

import model.hotel.Hotel;
import model.person.Guest;
import model.review.Review;
import service.guest.IGuestService;
import service.hotel.IHotelService;

import java.util.ArrayList;
import java.util.List;

public class ReviewService implements IReviewService {
    private final IHotelService hotelService;
    private final IGuestService guestService;
    private final List<Review> reviews = new ArrayList<>();
    private int nextReviewId = 1;

    public ReviewService(IHotelService hotelService, IGuestService guestService) {
        this.hotelService = hotelService;
        this.guestService = guestService;
    }

    @Override
    public Review addReview(int guestId, int hotelId, int stars, String comment) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Numarul de stele trebuie sa fie intre 1 si 5.");
        }

        Guest guest = guestService.getGuest(guestId);
        Hotel hotel = hotelService.getHotel(hotelId);
        Review review = new Review(nextReviewId++, guest, hotel, stars, comment);
        reviews.add(review);
        return review;
    }

    @Override
    public List<Review> getReviewsForHotel(int hotelId) {
        Hotel hotel = hotelService.getHotel(hotelId);
        List<Review> result = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getHotel().getId() == hotel.getId()) {
                result.add(review);
            }
        }
        return result;
    }
}
