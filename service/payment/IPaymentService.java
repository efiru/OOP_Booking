package service.payment;

import model.payment.Payment;

public interface IPaymentService {
    Payment registerPayment(int bookingId, String method);
}
