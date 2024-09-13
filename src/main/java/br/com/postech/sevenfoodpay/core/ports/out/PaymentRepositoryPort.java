package br.com.postech.sevenfoodpay.core.ports.out;

import br.com.postech.sevenfoodpay.core.domain.PaymentDomain;

import java.util.List;

public interface PaymentRepositoryPort {
    PaymentDomain save(PaymentDomain paymentDomain);
    boolean remove(Long id);
    PaymentDomain findById(Long id);
    PaymentDomain findByTransactionId(String transactionId);
    PaymentDomain findByPaymentId(String findByPaymentId);
    List<PaymentDomain> findAll();
    PaymentDomain update(Long id, PaymentDomain paymentDomain);
}
