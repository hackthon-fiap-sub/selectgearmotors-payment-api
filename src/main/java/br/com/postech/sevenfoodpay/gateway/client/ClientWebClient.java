package br.com.postech.sevenfoodpay.gateway.client;

import br.com.postech.sevenfoodpay.application.api.v1.dto.response.ClientLegalResponse;
import br.com.postech.sevenfoodpay.application.api.v1.dto.response.ClientPhysicalResponse;
import br.com.postech.sevenfoodpay.application.api.v1.dto.response.ClientResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientWebClient {

    @Value("${client.api.url}")
    private String clientAPiUrl;

    private final WebClient.Builder webClientBuilder;

    private WebClient webClient;

    public ClientWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private WebClient getWebClient() {
        if (this.webClient == null) {
            this.webClient = webClientBuilder.baseUrl(getClientAPiUrl()).build();
        }
        return this.webClient;
    }

    public ClientResponse getClientByCode(String clientId) {
        return getWebClient().get()
                .uri("/clients/code/{clientId}", clientId)
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();
    }

    public ClientPhysicalResponse getClientPhysicalsById(Long clientId) {
        return getWebClient().get()
                .uri("/client-physicals/client/{clientId}", clientId)
                .retrieve()
                .bodyToMono(ClientPhysicalResponse.class)
                .block();
    }

    public ClientLegalResponse getClientLegalsById(Long clientId) {
        return getWebClient().get()
                .uri("/client-legals/client/{clientId}", clientId)
                .retrieve()
                .bodyToMono(ClientLegalResponse.class)
                .block();
    }

    private String getClientAPiUrl() {
        return clientAPiUrl;
    }
}