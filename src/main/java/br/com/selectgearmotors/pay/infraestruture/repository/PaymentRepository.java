package br.com.selectgearmotors.pay.infraestruture.repository;

import br.com.selectgearmotors.pay.infraestruture.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByTransactionId(String transactionId);
    Optional<PaymentEntity> findByPaymentId(String paymentId);
}
