package br.com.postech.sevenfoodpay.core.service;

import br.com.postech.sevenfoodpay.application.api.v1.resources.PaymentResource;
import br.com.postech.sevenfoodpay.core.domain.PaymentDomain;
import br.com.postech.sevenfoodpay.core.ports.out.PaymentRepositoryPort;
import br.com.postech.sevenfoodpay.gateway.payment.MercadoPagoGetPaymentIdClient;
import br.com.postech.sevenfoodpay.gateway.payment.dto.PaymentApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PaymentStatusService {

    private static final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    private final PaymentRepositoryPort paymentRepositoryPort;
    private final MercadoPagoGetPaymentIdClient mercadoPagoGetPaymentIdClient;

    public PaymentStatusService(PaymentRepositoryPort paymentRepositoryPort, MercadoPagoGetPaymentIdClient mercadoPagoGetPaymentIdClient) {
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.mercadoPagoGetPaymentIdClient = mercadoPagoGetPaymentIdClient;
    }

    @Scheduled(fixedRate = 60000)  // Executa a cada 60 segundos
    public void getStatusPayment() {
        List<PaymentDomain> allPayments = paymentRepositoryPort.findAll();
        allPayments.forEach(payment -> {
            PaymentApiResponse paymentById = getPaymentById(String.valueOf(payment.getPaymentId()));
            log.info("PaymentById-Founded: {}", paymentById);
            if (paymentById.getStatus().equals("approved")) {
                payment.setPaymentStatus("approved");
                PaymentDomain save = paymentRepositoryPort.save(payment);
                if (save != null) {
                    log.info("PaymentStatus-Updated: {}", save);
                    //TODO - enviar criacao de documentos
                    //TODO - enviar notificacao para o cliente de pagamento aprovado
                }

            }
        });
    }

    public PaymentApiResponse getPaymentById(String paymentId) {
        Mono<PaymentApiResponse> paymentById = mercadoPagoGetPaymentIdClient.getPaymentById(paymentId);
        log.info("PaymentById: {}", paymentById);
        return paymentById.block();
    }
}
