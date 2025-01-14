package br.com.selectgearmotors.pay.application.api.v1.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record PaymentRequest(@NotNull(message = "Amount should not be null") @JsonProperty("transactionAmount") BigDecimal transactionAmount,
                             @NotNull(message = "ClientId should not be null") @JsonProperty("clientId") String clientId,
                             @NotNull(message = "Orders should not be null") @JsonProperty("transactionId") String transactionId,
                             @NotNull(message = "Orders should not be null") @JsonProperty("personType") String personType) {}