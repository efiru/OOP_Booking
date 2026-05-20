package service.payment;

import model.booking.Booking;
import model.payment.Payment;
import repository.booking.BookingDao;
import repository.payment.PaymentDao;
import service.audit.AuditService;

public class PaymentService implements IPaymentService {
    private final PaymentDao paymentDao;
    private final BookingDao bookingDao;
    private final AuditService auditService;

    public PaymentService(PaymentDao paymentDao, BookingDao bookingDao, AuditService auditService) {
        this.paymentDao = paymentDao;
        this.bookingDao = bookingDao;
        this.auditService = auditService;
    }

    @Override
    public Payment registerPayment(int bookingId, String method) {
        auditService.log("registerPayment");
        Booking booking = bookingDao.findById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Nu exista rezervare cu id-ul " + bookingId + ".");
        }
        double amount = booking.calculateTotalPrice();
        return paymentDao.insert(new Payment(0, booking, amount, method, true));
    }
}
