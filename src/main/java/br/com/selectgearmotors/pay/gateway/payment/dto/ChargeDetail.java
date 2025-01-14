package br.com.selectgearmotors.pay.gateway.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeDetail {
    private Accounts accounts;
    private Amounts amounts;
    private String name;
    private String dateCreated;
    private String lastUpdated;
    private String id;
}
