package br.com.postech.sevenfoodpay.infraestruture.repository;

import br.com.postech.sevenfoodpay.infraestruture.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByTransactionId(String transactionId);
    Optional<PaymentEntity> findByPaymentId(String paymentId);
}
