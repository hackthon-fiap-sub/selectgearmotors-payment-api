package br.com.postech.sevenfoodpay.application.api.v1.dto.response;

public record ClientLegalResponse(Long id,
                                  String socialName,
                                  String fantasyName,
                                  String companyId,
                                  String foundationDate,
                                  Long clientId) {
}