package br.com.postech.sevenfoodpay.gateway.client;

import br.com.postech.sevenfoodpay.application.api.v1.dto.response.ClientLegalResponse;
import br.com.postech.sevenfoodpay.application.api.v1.dto.response.ClientPhysicalResponse;
import br.com.postech.sevenfoodpay.application.api.v1.dto.response.ClientResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientWebClient {

    @Value("${client.api.url}")
    @Getter
    private String clientAPiUrl;

    private final WebClient webClient;

    public ClientWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(getClientAPiUrl()).build();
    }

    public ClientResponse getClientByCode(String clientId) {
        return webClient.get()
                .uri("/clients/code/{clientId}", clientId)
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();
    }

    public ClientPhysicalResponse getClientPhysicalsById(Long clientId) {
        return webClient.get()
                .uri("/client-physicals/client/{clientId}", clientId)
                .retrieve()
                .bodyToMono(ClientPhysicalResponse.class)
                .block();
    }

    public ClientLegalResponse getClientLegalsById(Long clientId) {
        return webClient.get()
                .uri("/client-legals/client/{clientId}", clientId)
                .retrieve()
                .bodyToMono(ClientLegalResponse.class)
                .block();
    }
}