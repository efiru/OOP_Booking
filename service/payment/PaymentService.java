package service.payment;

import model.booking.Booking;
import model.payment.Payment;
import service.booking.IBookingService;

import java.util.ArrayList;
import java.util.List;

public class PaymentService implements IPaymentService {
    private final IBookingService bookingService;
    private final List<Payment> payments = new ArrayList<>();
    private int nextPaymentId = 1;

    public PaymentService(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public Payment registerPayment(int bookingId, String method) {
        Booking booking = bookingService.getBooking(bookingId);
        Payment payment = new Payment(nextPaymentId++, booking, booking.calculateTotalPrice(), method, true);
        payments.add(payment);
        return payment;
    }
}
