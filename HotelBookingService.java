import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class HotelBookingService {
    private TreeSet<Hotel> hotels = new TreeSet<>();
    private Map<Integer, Hotel> hotelById = new HashMap<>();
    private Map<Integer, Guest> guests = new HashMap<>();
    private Map<Integer, Employee> employees = new HashMap<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    private int nextHotelId = 1;
    private int nextRoomId = 1;
    private int nextGuestId = 1;
    private int nextEmployeeId = 1;
    private int nextBookingId = 1;
    private int nextPaymentId = 1;
    private int nextReviewId = 1;

    public Hotel addHotel(String name, String city, int stars) {
        Hotel hotel = new Hotel(nextHotelId++, name, city, stars);
        hotels.add(hotel);
        hotelById.put(hotel.getId(), hotel);
        return hotel;
    }

    public Room addStandardRoom(int hotelId, String number, int capacity, double pricePerNight, boolean balcony) {
        Hotel hotel = getHotelByIdOrThrow(hotelId);
        Room room = new StandardRoom(nextRoomId++, number, capacity, pricePerNight, true, balcony);
        hotel.addRoom(room);
        return room;
    }

    public Room addSuiteRoom(int hotelId, String number, int capacity, double pricePerNight, boolean livingArea) {
        Hotel hotel = getHotelByIdOrThrow(hotelId);
        Room room = new SuiteRoom(nextRoomId++, number, capacity, pricePerNight, true, livingArea);
        hotel.addRoom(room);
        return room;
    }

    public Guest registerGuest(String name, String email) {
        Guest guest = new Guest(nextGuestId++, name, email, 0);
        guests.put(guest.getId(), guest);
        return guest;
    }

    public Employee registerEmployee(String name, String email, String position) {
        Employee employee = new Employee(nextEmployeeId++, name, email, position);
        employees.put(employee.getId(), employee);
        return employee;
    }

    public List<Hotel> getAllHotels() {
        return new ArrayList<>(hotels);
    }

    public List<Hotel> findHotelsByCity(String city) {
        List<Hotel> result = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (hotel.getCity().equalsIgnoreCase(city)) {
                result.add(hotel);
            }
        }
        return result;
    }

    public List<Room> getRoomsForHotel(int hotelId) {
        Hotel hotel = getHotelByIdOrThrow(hotelId);
        return new ArrayList<>(hotel.getRooms());
    }

    public List<Room> getAvailableRoomsUnderPrice(double maxPrice) {
        List<Room> result = new ArrayList<>();
        for (Hotel hotel : hotels) {
            for (Room room : hotel.getRooms()) {
                if (room.isAvailable() && room.getPricePerNight() <= maxPrice) {
                    result.add(room);
                }
            }
        }
        return result;
    }

    public Booking createBooking(int guestId, int hotelId, int roomId, LocalDate checkInDate, int nights) {
        if (nights <= 0) {
            throw new IllegalArgumentException("Numarul de nopti trebuie sa fie pozitiv.");
        }

        Guest guest = getGuestByIdOrThrow(guestId);
        Hotel hotel = getHotelByIdOrThrow(hotelId);
        Room room = getRoomByIdOrThrow(hotel, roomId);

        if (!room.isAvailable()) {
            throw new IllegalArgumentException("Camera selectata nu este disponibila.");
        }

        Booking booking = new Booking(nextBookingId++, guest, hotel, room, checkInDate, nights, BookingStatus.CONFIRMED);
        bookings.add(booking);
        room.setAvailable(false);
        guest.setLoyaltyPoints(guest.getLoyaltyPoints() + nights * 10);
        return booking;
    }

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

    public List<Booking> getBookingsForGuest(int guestId) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getGuest().getId() == guestId) {
                result.add(booking);
            }
        }
        return result;
    }

    public Payment registerPayment(int bookingId, String method) {
        Booking booking = getBookingByIdOrThrow(bookingId);
        Payment payment = new Payment(nextPaymentId++, booking, booking.calculateTotalPrice(), method, true);
        payments.add(payment);
        return payment;
    }

    public Review addReview(int guestId, int hotelId, int stars, String comment) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Numarul de stele trebuie sa fie intre 1 si 5.");
        }

        Guest guest = getGuestByIdOrThrow(guestId);
        Hotel hotel = getHotelByIdOrThrow(hotelId);
        Review review = new Review(nextReviewId++, guest, hotel, stars, comment);
        reviews.add(review);
        return review;
    }

    public List<Review> getReviewsForHotel(int hotelId) {
        Hotel hotel = getHotelByIdOrThrow(hotelId);
        List<Review> result = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getHotel().getId() == hotel.getId()) {
                result.add(review);
            }
        }
        return result;
    }

    public List<Guest> getAllGuests() {
        return new ArrayList<>(guests.values());
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public void seedData() {
        Hotel h1 = addHotel("Central Stay", "Bucuresti", 4);
        Hotel h2 = addHotel("Mountain View", "Brasov", 3);
        Hotel h3 = addHotel("City Lights", "Cluj", 5);

        addStandardRoom(h1.getId(), "101", 2, 250, true);
        addSuiteRoom(h1.getId(), "201", 4, 500, true);
        addStandardRoom(h2.getId(), "12", 2, 180, false);
        addSuiteRoom(h2.getId(), "20", 3, 350, true);
        addStandardRoom(h3.getId(), "5", 1, 220, false);
        addSuiteRoom(h3.getId(), "9", 2, 420, true);

        Guest guest1 = registerGuest("Ana Popescu", "ana@gmail.com");
        Guest guest2 = registerGuest("Mihai Ionescu", "mihai@gmail.com");

        registerEmployee("Irina Pavel", "irina@hotel.ro", "Receptioner");
        registerEmployee("Radu Muntean", "radu@hotel.ro", "Manager");

        Booking booking = createBooking(guest1.getId(), h1.getId(), 1, LocalDate.now().plusDays(3), 2);
        registerPayment(booking.getId(), "card");
        addReview(guest2.getId(), h2.getId(), 5, "Hotel curat si aproape de centru.");
    }

    private Hotel getHotelByIdOrThrow(int hotelId) {
        Hotel hotel = hotelById.get(hotelId);
        if (hotel == null) {
            throw new IllegalArgumentException("Nu exista hotel cu id-ul " + hotelId + ".");
        }
        return hotel;
    }

    private Guest getGuestByIdOrThrow(int guestId) {
        Guest guest = guests.get(guestId);
        if (guest == null) {
            throw new IllegalArgumentException("Nu exista client cu id-ul " + guestId + ".");
        }
        return guest;
    }

    private Room getRoomByIdOrThrow(Hotel hotel, int roomId) {
        for (Room room : hotel.getRooms()) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        throw new IllegalArgumentException("Nu exista camera cu id-ul " + roomId + " in hotelul selectat.");
    }

    private Booking getBookingByIdOrThrow(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId() == bookingId) {
                return booking;
            }
        }
        throw new IllegalArgumentException("Nu exista rezervare cu id-ul " + bookingId + ".");
    }
}
