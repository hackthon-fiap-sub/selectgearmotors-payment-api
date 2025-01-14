package br.com.selectgearmotors.pay.gateway.client;

import br.com.selectgearmotors.pay.application.api.v1.dto.response.ClientLegalResponse;
import br.com.selectgearmotors.pay.application.api.v1.dto.response.ClientPhysicalResponse;
import br.com.selectgearmotors.pay.application.api.v1.dto.response.ClientResponse;
import br.com.selectgearmotors.pay.commons.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientWebClient {

    private final HttpServletRequest request;

    @Value("${client.api.url}")
    private String clientAPiUrl;

    private final WebClient.Builder webClientBuilder;

    private WebClient webClient;

    public ClientWebClient(HttpServletRequest request, WebClient.Builder webClientBuilder) {
        this.request = request;
        this.webClientBuilder = webClientBuilder;
    }

    private WebClient getWebClient() {
        if (this.webClient == null) {
            this.webClient = webClientBuilder.baseUrl(getClientAPiUrl()).build();
        }
        return this.webClient;
    }

    public ClientResponse getClientByCode(String clientId) {
        // Pega o token armazenado no filtro
        String bearerToken = (String) request.getAttribute(Constants.BEARER_TOKEN_ATTRIBUTE);
        return getWebClient().get()
                .uri("/clients/code/{clientId}", clientId)
                .headers(headers -> headers.setBearerAuth(bearerToken))
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();
    }

    public ClientPhysicalResponse getClientPhysicalsById(Long clientId) {
        // Pega o token armazenado no filtro
        String bearerToken = (String) request.getAttribute(Constants.BEARER_TOKEN_ATTRIBUTE);
        return getWebClient().get()
                .uri("/client-physicals/{clientId}", clientId)
                .headers(headers -> headers.setBearerAuth(bearerToken))
                .retrieve()
                .bodyToMono(ClientPhysicalResponse.class)
                .block();
    }

    public ClientLegalResponse getClientLegalsById(Long clientId) {
        // Pega o token armazenado no filtro
        String bearerToken = (String) request.getAttribute(Constants.BEARER_TOKEN_ATTRIBUTE);
        return getWebClient().get()
                .uri("/client-legals/{clientId}", clientId)
                .headers(headers -> headers.setBearerAuth(bearerToken))
                .retrieve()
                .bodyToMono(ClientLegalResponse.class)
                .block();
    }

    private String getClientAPiUrl() {
        return clientAPiUrl;
    }
}