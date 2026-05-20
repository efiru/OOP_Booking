package service.booking;

import model.booking.Booking;
import model.booking.BookingStatus;
import model.hotel.Hotel;
import model.person.Guest;
import model.room.Room;
import repository.booking.BookingDao;
import repository.guest.GuestDao;
import repository.hotel.HotelDao;
import repository.room.RoomDao;

import java.time.LocalDate;
import java.util.List;

public class BookingService implements IBookingService {
    private final BookingDao bookingDao;
    private final HotelDao hotelDao;
    private final RoomDao roomDao;
    private final GuestDao guestDao;

    public BookingService(BookingDao bookingDao, HotelDao hotelDao, RoomDao roomDao, GuestDao guestDao) {
        this.bookingDao = bookingDao;
        this.hotelDao = hotelDao;
        this.roomDao = roomDao;
        this.guestDao = guestDao;
    }

    @Override
    public Booking createBooking(int guestId, int hotelId, int roomId, LocalDate checkInDate, int nights) {
        if (nights <= 0) {
            throw new IllegalArgumentException("Numarul de nopti trebuie sa fie pozitiv.");
        }

        Guest guest = guestDao.findById(guestId);
        if (guest == null) throw new IllegalArgumentException("Nu exista client cu id-ul " + guestId + ".");

        Hotel hotel = hotelDao.findById(hotelId);
        if (hotel == null) throw new IllegalArgumentException("Nu exista hotel cu id-ul " + hotelId + ".");

        Room room = findRoomInHotel(hotelId, roomId);
        if (!room.isAvailable()) throw new IllegalArgumentException("Camera selectata nu este disponibila.");

        Booking booking = bookingDao.insert(new Booking(0, guest, hotel, room, checkInDate, nights, BookingStatus.CONFIRMED));

        room.setAvailable(false);
        roomDao.update(room);

        guest.setLoyaltyPoints(guest.getLoyaltyPoints() + nights * 10);
        guestDao.update(guest);

        return booking;
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        Booking booking = bookingDao.findById(bookingId);
        if (booking == null || booking.getStatus() != BookingStatus.CONFIRMED) {
            return false;
        }
        booking.setStatus(BookingStatus.CANCELLED);
        bookingDao.update(booking);

        Room room = booking.getRoom();
        room.setAvailable(true);
        roomDao.update(room);

        return true;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingDao.findAll();
    }

    @Override
    public List<Booking> getBookingsForGuest(int guestId) {
        return bookingDao.findAllByGuestId(guestId);
    }

    @Override
    public Booking getBooking(int bookingId) {
        Booking booking = bookingDao.findById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Nu exista rezervare cu id-ul " + bookingId + ".");
        }
        return booking;
    }

    private Room findRoomInHotel(int hotelId, int roomId) {
        for (Room room : roomDao.findAllByHotelId(hotelId)) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        throw new IllegalArgumentException("Nu exista camera cu id-ul " + roomId + " in hotelul selectat.");
    }
}
