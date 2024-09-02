package br.com.postech.sevenfoodpay.gateway.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessInfo {
    private String branch;
    private String subUnit;
    private String unit;
}
