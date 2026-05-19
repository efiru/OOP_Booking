package controller;

import model.payment.Payment;
import org.springframework.web.bind.annotation.*;
import service.payment.IPaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment registerPayment(@RequestBody RegisterPaymentRequest req) {
        return paymentService.registerPayment(req.bookingId(), req.method());
    }

    record RegisterPaymentRequest(int bookingId, String method) {}
}
