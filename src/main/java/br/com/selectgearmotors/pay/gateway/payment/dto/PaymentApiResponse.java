package br.com.selectgearmotors.pay.gateway.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentApiResponse {
    private Long id;
    private String status;
    private String statusDetail;
    private String description;
    private String currencyId;
    private Double transactionAmount;
    private Double transactionAmountRefunded;
    private TransactionDetails transactionDetails;
    private Payer payer;
    private PaymentMethod paymentMethod;
    private PointOfInteraction pointOfInteraction;
    private List<ChargeDetail> chargesDetails;
}
