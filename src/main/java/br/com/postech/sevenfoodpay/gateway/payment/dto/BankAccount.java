package br.com.postech.sevenfoodpay.gateway.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private String accountHolderName;
    private String accountId;
    private Identification identification;
}
