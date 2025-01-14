package br.com.selectgearmotors.pay.core.ports.in;

import br.com.selectgearmotors.pay.application.api.v1.dto.request.PaymentRequest;
import br.com.selectgearmotors.pay.application.api.v1.dto.response.PaymentResponse;
import br.com.selectgearmotors.pay.gateway.payment.dto.PaymentApiResponse;

import java.util.Optional;

public interface PaymentPort {
    Optional<PaymentResponse> processPayment(PaymentRequest paymentRequest);
    PaymentApiResponse getPaymentById(String paymentId);
}
