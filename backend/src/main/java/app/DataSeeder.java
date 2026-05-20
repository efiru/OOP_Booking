package app;

import model.booking.Booking;
import model.hotel.Hotel;
import model.person.Guest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import service.booking.IBookingService;
import service.employee.IEmployeeService;
import service.guest.IGuestService;
import service.hotel.IHotelService;
import service.payment.IPaymentService;
import service.review.IReviewService;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final IHotelService hotelService;
    private final IGuestService guestService;
    private final IEmployeeService employeeService;
    private final IBookingService bookingService;
    private final IPaymentService paymentService;
    private final IReviewService reviewService;

    public DataSeeder(IHotelService hotelService, IGuestService guestService,
                      IEmployeeService employeeService, IBookingService bookingService,
                      IPaymentService paymentService, IReviewService reviewService) {
        this.hotelService = hotelService;
        this.guestService = guestService;
        this.employeeService = employeeService;
        this.bookingService = bookingService;
        this.paymentService = paymentService;
        this.reviewService = reviewService;
    }

    @Override
    public void run(String... args) {
        if (!hotelService.getAllHotels().isEmpty()) {
            return;
        }

        Hotel h1 = hotelService.addHotel("Central Stay", "Bucuresti", 4);
        Hotel h2 = hotelService.addHotel("Mountain View", "Brasov", 3);
        Hotel h3 = hotelService.addHotel("City Lights", "Cluj", 5);

        hotelService.addStandardRoom(h1.getId(), "101", 2, 250, true);
        hotelService.addSuiteRoom(h1.getId(), "201", 4, 500, true);
        hotelService.addStandardRoom(h2.getId(), "12", 2, 180, false);
        hotelService.addSuiteRoom(h2.getId(), "20", 3, 350, true);
        hotelService.addStandardRoom(h3.getId(), "5", 1, 220, false);
        hotelService.addSuiteRoom(h3.getId(), "9", 2, 420, true);

        Guest guest1 = guestService.registerGuest("Ana Popescu", "ana@gmail.com");
        Guest guest2 = guestService.registerGuest("Mihai Ionescu", "mihai@gmail.com");

        employeeService.registerEmployee("Irina Pavel", "irina@hotel.ro", "Receptioner");
        employeeService.registerEmployee("Radu Muntean", "radu@hotel.ro", "Manager");

        Booking booking = bookingService.createBooking(guest1.getId(), h1.getId(), 1, LocalDate.now().plusDays(3), 2);
        paymentService.registerPayment(booking.getId(), "card");
        reviewService.addReview(guest2.getId(), h2.getId(), 5, "Hotel curat si aproape de centru.");
    }
}
