package service.booking;

import model.booking.Booking;
import model.booking.BookingStatus;
import model.hotel.Hotel;
import model.person.Guest;
import model.room.Room;
import service.guest.IGuestService;
import service.hotel.IHotelService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingService implements IBookingService {
    private final IHotelService hotelService;
    private final IGuestService guestService;
    private final List<Booking> bookings = new ArrayList<>();
    private int nextBookingId = 1;

    public BookingService(IHotelService hotelService, IGuestService guestService) {
        this.hotelService = hotelService;
        this.guestService = guestService;
    }

    @Override
    public Booking createBooking(int guestId, int hotelId, int roomId, LocalDate checkInDate, int nights) {
        if (nights <= 0) {
            throw new IllegalArgumentException("Numarul de nopti trebuie sa fie pozitiv.");
        }

        Guest guest = guestService.getGuest(guestId);
        Hotel hotel = hotelService.getHotel(hotelId);
        Room room = hotelService.getRoomInHotel(hotelId, roomId);

        if (!room.isAvailable()) {
            throw new IllegalArgumentException("Camera selectata nu este disponibila.");
        }

        Booking booking = new Booking(nextBookingId++, guest, hotel, room, checkInDate, nights, BookingStatus.CONFIRMED);
        bookings.add(booking);
        room.setAvailable(false);
        guest.setLoyaltyPoints(guest.getLoyaltyPoints() + nights * 10);
        return booking;
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId() == bookingId && booking.getStatus() == BookingStatus.CONFIRMED) {
                booking.setStatus(BookingStatus.CANCELLED);
                booking.getRoom().setAvailable(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Booking> getBookingsForGuest(int guestId) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getGuest().getId() == guestId) {
                result.add(booking);
            }
        }
        return result;
    }

    @Override
    public Booking getBooking(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId() == bookingId) {
                return booking;
            }
        }
        throw new IllegalArgumentException("Nu exista rezervare cu id-ul " + bookingId + ".");
    }
}
