package br.com.selectgearmotors.pay.gateway.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionData {
    private BankInfo bankInfo;
    private String qrCode;
    private String qrCodeBase64;
    private String ticketUrl;
}
