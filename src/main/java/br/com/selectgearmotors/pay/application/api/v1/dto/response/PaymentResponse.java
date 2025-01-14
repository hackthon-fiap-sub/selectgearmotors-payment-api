package br.com.selectgearmotors.pay.application.api.v1.dto.response;

public record PaymentResponse(Long id, String status, String detail, String qrCodeBase64, String qrCode) {
}
