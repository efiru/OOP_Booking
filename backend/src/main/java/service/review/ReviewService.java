package service.review;

import model.hotel.Hotel;
import model.person.Guest;
import model.review.Review;
import repository.guest.GuestDao;
import repository.hotel.HotelDao;
import repository.review.ReviewDao;
import service.audit.AuditService;

import java.util.List;

public class ReviewService implements IReviewService {
    private final ReviewDao reviewDao;
    private final GuestDao guestDao;
    private final HotelDao hotelDao;
    private final AuditService auditService;

    public ReviewService(ReviewDao reviewDao, GuestDao guestDao, HotelDao hotelDao, AuditService auditService) {
        this.reviewDao = reviewDao;
        this.guestDao = guestDao;
        this.hotelDao = hotelDao;
        this.auditService = auditService;
    }

    @Override
    public Review addReview(int guestId, int hotelId, int stars, String comment) {
        auditService.log("addReview");
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Numarul de stele trebuie sa fie intre 1 si 5.");
        }
        Guest guest = guestDao.findById(guestId);
        if (guest == null) throw new IllegalArgumentException("Nu exista client cu id-ul " + guestId + ".");
        Hotel hotel = hotelDao.findById(hotelId);
        if (hotel == null) throw new IllegalArgumentException("Nu exista hotel cu id-ul " + hotelId + ".");
        return reviewDao.insert(new Review(0, guest, hotel, stars, comment));
    }

    @Override
    public List<Review> getReviewsForHotel(int hotelId) {
        auditService.log("getReviewsForHotel");
        return reviewDao.findAllByHotelId(hotelId);
    }
}
