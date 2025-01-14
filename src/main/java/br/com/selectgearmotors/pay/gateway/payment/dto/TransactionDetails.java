package br.com.selectgearmotors.pay.gateway.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
    private String acquirerReference;
    private String bankTransferId;
    private String externalResourceUrl;
    private String financialInstitution;
    private Double totalPaidAmount;
    private Double netReceivedAmount;
    private Double overpaidAmount;
}
