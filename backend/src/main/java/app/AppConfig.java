package app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import repository.booking.BookingDao;
import repository.guest.GuestDao;
import repository.hotel.HotelDao;
import repository.room.RoomDao;
import service.booking.BookingService;
import service.booking.IBookingService;
import service.employee.EmployeeService;
import service.employee.IEmployeeService;
import service.guest.GuestService;
import service.guest.IGuestService;
import service.hotel.HotelService;
import service.hotel.IHotelService;
import service.payment.IPaymentService;
import service.payment.PaymentService;
import service.review.IReviewService;
import service.review.ReviewService;

@Configuration
public class AppConfig {

    @Bean
    public HotelDao hotelDao() {
        return new HotelDao();
    }

    @Bean
    public GuestDao guestDao() {
        return new GuestDao();
    }

    @Bean
    public RoomDao roomDao() {
        return new RoomDao();
    }

    @Bean
    public BookingDao bookingDao(GuestDao guestDao, HotelDao hotelDao, RoomDao roomDao) {
        return new BookingDao(guestDao, hotelDao, roomDao);
    }

    @Bean
    public IHotelService hotelService(HotelDao hotelDao, RoomDao roomDao) {
        return new HotelService(hotelDao, roomDao);
    }

    @Bean
    public IGuestService guestService(GuestDao guestDao) {
        return new GuestService(guestDao);
    }

    @Bean
    public IEmployeeService employeeService() {
        return new EmployeeService();
    }

    @Bean
    public IBookingService bookingService(BookingDao bookingDao, HotelDao hotelDao, RoomDao roomDao, GuestDao guestDao) {
        return new BookingService(bookingDao, hotelDao, roomDao, guestDao);
    }

    @Bean
    public IPaymentService paymentService(IBookingService bookingService) {
        return new PaymentService(bookingService);
    }

    @Bean
    public IReviewService reviewService(IHotelService hotelService, IGuestService guestService) {
        return new ReviewService(hotelService, guestService);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
