package br.com.postech.sevenfoodpay.infraestruture.entities;

import br.com.postech.sevenfoodpay.core.domain.PaymentDomain;
import br.com.postech.sevenfoodpay.infraestruture.domain.AuditDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_payment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull(message = "o campo \"clientId\" é obrigario")
    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "payment_details", nullable = false)
    private String paymentDetails;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_amount", nullable = false)
    private BigDecimal paymentAmount;

    @Column(name = "qr_code", nullable = false)
    private String qrCode;

    @Column(name = "qr_code_base64", nullable = false)
    private String qrCodeBase64;

    @Column(name = "orders", nullable = false)
    private String orders;

    @Column(name = "notification_sent", nullable = false)
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
        this.orders = paymentDomain.getOrderId();
        this.transactionId = paymentDomain.getTransactionId();
        this.notificationSent = paymentDomain.isNotificationSent();
    }
}
