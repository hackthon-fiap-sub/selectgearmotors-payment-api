package br.com.selectgearmotors.pay.gateway.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointOfInteraction {
    private ApplicationData applicationData;
    private BusinessInfo businessInfo;
    private TransactionData transactionData;
}
