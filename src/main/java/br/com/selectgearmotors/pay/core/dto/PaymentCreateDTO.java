package br.com.selectgearmotors.pay.core.dto;

import com.mercadopago.client.payment.PaymentCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateDTO implements Serializable {
    private PaymentCreateRequest paymentCreateRequest;
    private String transactionId;
    private String clientId;
}
