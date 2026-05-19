package app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
    public IHotelService hotelService() {
        return new HotelService();
    }

    @Bean
    public IGuestService guestService() {
        return new GuestService();
    }

    @Bean
    public IEmployeeService employeeService() {
        return new EmployeeService();
    }

    @Bean
    public IBookingService bookingService(IHotelService hotelService, IGuestService guestService) {
        return new BookingService(hotelService, guestService);
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
