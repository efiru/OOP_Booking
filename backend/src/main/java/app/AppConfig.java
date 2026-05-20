package app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import repository.booking.BookingDao;
import repository.guest.GuestDao;
import repository.hotel.HotelDao;
import repository.payment.PaymentDao;
import repository.review.ReviewDao;
import repository.room.RoomDao;
import service.audit.AuditService;
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
    public AuditService auditService() {
        return AuditService.getInstance();
    }

    @Bean
    public IHotelService hotelService(HotelDao hotelDao, RoomDao roomDao, AuditService auditService) {
        return new HotelService(hotelDao, roomDao, auditService);
    }

    @Bean
    public IGuestService guestService(GuestDao guestDao, AuditService auditService) {
        return new GuestService(guestDao, auditService);
    }

    @Bean
    public IEmployeeService employeeService(AuditService auditService) {
        return new EmployeeService(auditService);
    }

    @Bean
    public IBookingService bookingService(BookingDao bookingDao, HotelDao hotelDao, RoomDao roomDao, GuestDao guestDao, AuditService auditService) {
        return new BookingService(bookingDao, hotelDao, roomDao, guestDao, auditService);
    }

    @Bean
    public PaymentDao paymentDao(BookingDao bookingDao) {
        return new PaymentDao(bookingDao);
    }

    @Bean
    public ReviewDao reviewDao(GuestDao guestDao, HotelDao hotelDao) {
        return new ReviewDao(guestDao, hotelDao);
    }

    @Bean
    public IPaymentService paymentService(PaymentDao paymentDao, BookingDao bookingDao, AuditService auditService) {
        return new PaymentService(paymentDao, bookingDao, auditService);
    }

    @Bean
    public IReviewService reviewService(ReviewDao reviewDao, GuestDao guestDao, HotelDao hotelDao, AuditService auditService) {
        return new ReviewService(reviewDao, guestDao, hotelDao, auditService);
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
