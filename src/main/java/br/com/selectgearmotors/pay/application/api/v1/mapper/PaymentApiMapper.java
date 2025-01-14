package br.com.selectgearmotors.pay.application.api.v1.mapper;

import br.com.selectgearmotors.pay.application.api.v1.dto.response.PaymentResponse;
import br.com.selectgearmotors.pay.core.domain.PaymentDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentApiMapper {

    private static final Logger log = LoggerFactory.getLogger(PaymentApiMapper.class);

    public PaymentResponse toPaymentResponse(PaymentDomain paymentDomain) {
        log.info("PaymentId: {}", paymentDomain.getId());
        return new PaymentResponse(paymentDomain.getId(), paymentDomain.getPaymentStatus(), paymentDomain.getPaymentDetails(), paymentDomain.getQrCodeBase64(), paymentDomain.getQrCode());
    }

    public List<PaymentResponse> map(List<PaymentDomain> paymentDomainList) {
        return paymentDomainList.stream().map(this::toPaymentResponse).toList();
    }
}
