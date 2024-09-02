package br.com.postech.sevenfoodpay.core.ports.in;

import br.com.postech.sevenfoodpay.application.api.v1.dto.request.PaymentRequest;
import br.com.postech.sevenfoodpay.application.api.v1.dto.response.PaymentResponse;
import br.com.postech.sevenfoodpay.gateway.payment.dto.PaymentApiResponse;

import java.util.Optional;

public interface PaymentPort {
    Optional<PaymentResponse> processPayment(PaymentRequest paymentRequest);
    PaymentApiResponse getPaymentById(String paymentId);
}
