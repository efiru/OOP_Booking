package controller;

import model.booking.Booking;
import org.springframework.web.bind.annotation.*;
import service.booking.IBookingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getBookings(@RequestParam int guestId) {
        return bookingService.getBookingsForGuest(guestId);
    }

    @PostMapping
    public Booking createBooking(@RequestBody CreateBookingRequest req) {
        return bookingService.createBooking(req.guestId(), req.hotelId(), req.roomId(),
                LocalDate.parse(req.checkInDate()), req.nights());
    }

    @PutMapping("/{id}/cancel")
    public boolean cancelBooking(@PathVariable int id) {
        return bookingService.cancelBooking(id);
    }

    record CreateBookingRequest(int guestId, int hotelId, int roomId, String checkInDate, int nights) {}
}
