package br.com.postech.sevenfoodpay.application.api.v1.dto.response;

public record ClientResponse(Long id,
                             String name,
                             String email,
                             String mobile) {}
