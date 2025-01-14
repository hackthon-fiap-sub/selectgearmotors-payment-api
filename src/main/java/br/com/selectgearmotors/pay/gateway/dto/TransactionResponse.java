package br.com.selectgearmotors.pay.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private String vehicleCode;
    private String clientCode;
    private String code;
    private BigDecimal price;
    private String transactionStatus;
    private Long transactionTypeId;
}