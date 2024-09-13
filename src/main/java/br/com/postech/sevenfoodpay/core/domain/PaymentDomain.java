package br.com.postech.sevenfoodpay.core.domain;

import com.mercadopago.resources.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDomain implements Serializable {

    private Long id;

    private String clientId;

    private String paymentId;

    private String paymentStatus;

    private String paymentDetails;

    private LocalDateTime paymentDate;

    private BigDecimal paymentAmount;

    private String qrCode;

    private String qrCodeBase64;

    private String orderId;

    private String transactionId;

    private boolean notificationSent;

    public void update(Long id, PaymentDomain paymentDomain) {
        this.id = id;
        this.clientId = paymentDomain.getClientId();
        this.paymentId = paymentDomain.getPaymentId();
        this.paymentStatus = paymentDomain.getPaymentStatus();
        this.paymentDetails = paymentDomain.getPaymentDetails();
        this.paymentDate = paymentDomain.getPaymentDate();
        this.paymentAmount = paymentDomain.getPaymentAmount();
        this.qrCode = paymentDomain.getQrCode();
        this.qrCodeBase64 = paymentDomain.getQrCodeBase64();
        this.orderId = paymentDomain.getOrderId();
        this.transactionId = paymentDomain.getTransactionId();
        this.notificationSent = paymentDomain.isNotificationSent();
    }
}