package br.com.selectgearmotors.pay.application.api.v1.dto.response;

public record ClientPhysicalResponse(Long id,
                                     String socialId,
                                     String socialIdDispatchDate,
                                     String documentId,
                                     String documentDistrict,
                                     String documentDispatchDate,
                                     String birthDate,
                                     Long clientId) {
}
