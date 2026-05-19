package service.booking;

import model.booking.Booking;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    Booking createBooking(int guestId, int hotelId, int roomId, LocalDate checkInDate, int nights);
    boolean cancelBooking(int bookingId);
    List<Booking> getBookingsForGuest(int guestId);
    Booking getBooking(int bookingId);
}
