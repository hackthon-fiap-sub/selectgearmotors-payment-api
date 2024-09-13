package br.com.postech.sevenfoodpay.application.event;

import br.com.postech.sevenfoodpay.core.domain.PaymentDomain;
import br.com.postech.sevenfoodpay.core.ports.out.PaymentRepositoryPort;
import br.com.postech.sevenfoodpay.gateway.order.OrderWebClient;
import br.com.postech.sevenfoodpay.gateway.payment.MercadoPagoGetPaymentIdClient;
import br.com.postech.sevenfoodpay.gateway.payment.dto.PaymentApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PaymentStatusAdapter {

    private static final Logger log = LoggerFactory.getLogger(PaymentStatusAdapter.class);

    public static final String MERCADO_PAGO_APPROVED = "approved";
    public static final String MERCADO_PAGO_PENDING = "pending";
    public static final String TRANSACTION_CONFIRMED = "CONFIRMED";

    private final PaymentRepositoryPort paymentRepositoryPort;
    private final MercadoPagoGetPaymentIdClient mercadoPagoGetPaymentIdClient;
    private final OrderWebClient orderWebClient;

    public PaymentStatusAdapter(PaymentRepositoryPort paymentRepositoryPort, MercadoPagoGetPaymentIdClient mercadoPagoGetPaymentIdClient, OrderWebClient orderWebClient) {
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.mercadoPagoGetPaymentIdClient = mercadoPagoGetPaymentIdClient;
        this.orderWebClient = orderWebClient;
    }

    //@Scheduled(cron = "0 0 18 * * ?") //VAi rodor todo os dias as 18:00
    @Scheduled(fixedRate = 60000)  // Executa a cada 60 segundos
    public void getStatusPayment() {
        List<PaymentDomain> allPayments = paymentRepositoryPort.findAll();
        allPayments.forEach(payment -> {
            if (payment.getPaymentStatus().equals(MERCADO_PAGO_PENDING)) {
                log.info("PaymentStatus-Pending: {}", payment);
                PaymentApiResponse paymentById = getPaymentById(String.valueOf(payment.getPaymentId()));
                log.info("PaymentById-Founded: {}", paymentById.getId());

                if (paymentById.getStatus().equals(MERCADO_PAGO_APPROVED)) {
                    log.info("PaymentStatus-Pending - Approved: {}", paymentById.getStatus());

                    payment.setPaymentStatus(MERCADO_PAGO_APPROVED);
                    PaymentDomain saved = paymentRepositoryPort.update(payment.getId(), payment);

                    if (saved != null) {
                        updateStatusTransactionToApproval(payment.getTransactionId());
                    }
                } else {
                    log.info("Pagamento Id: {} ainda está pedente (Status: Pending) - Será verificado novamente!", paymentById.getId());
                }
            } else if (payment.getPaymentStatus().equals(MERCADO_PAGO_APPROVED) && !payment.isNotificationSent()) {
                log.info("Pagamento Id: {} já foi aprovado (Status: Approved) - Não será verificado novamente!", payment.getPaymentId());
                updateStatusTransactionToApproval(payment.getTransactionId());
                payment.setNotificationSent(true);
                PaymentDomain saved = paymentRepositoryPort.update(payment.getId(), payment);
                if (saved != null) {
                    log.info("Pagamento Id: {} atualizado com sucesso!", payment.getPaymentId());
                }
            }
        });
    }

    private void updateStatusTransactionToApproval(String transactionId) {
        try {
            orderWebClient.updateOrderStatus(transactionId, TRANSACTION_CONFIRMED);
        } catch (Exception e) {
            log.error("Error to update status transaction to approval: {}", e.getMessage());
        }
    }

    public PaymentApiResponse getPaymentById(String paymentId) {
        Mono<PaymentApiResponse> paymentById = mercadoPagoGetPaymentIdClient.getPaymentById(paymentId);
        log.info("PaymentById: {}", paymentById);
        return paymentById.block();
    }
}

