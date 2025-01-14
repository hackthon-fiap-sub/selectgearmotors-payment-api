package br.com.selectgearmotors.pay.gateway.order;

import br.com.selectgearmotors.pay.commons.Constants;
import br.com.selectgearmotors.pay.gateway.dto.TransactionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OrderWebClient {

    private final HttpServletRequest request;

    @Getter
    @Value("${transaction.api.url}")
    private String transactionAPiUrl;

    private final WebClient.Builder webClientBuilder;

    private WebClient webClient;

    public OrderWebClient(HttpServletRequest request, WebClient.Builder webClientBuilder) {
        this.request = request;
        this.webClientBuilder = webClientBuilder;
    }

    private WebClient getWebClient() {
        if (this.webClient == null) {
            this.webClient = webClientBuilder.baseUrl(getTransactionAPiUrl()).build();
        }
        return this.webClient;
    }

    public TransactionResponse getOrderById(String transactionId) {
        // Pega o token armazenado no filtro
        String bearerToken = (String) request.getAttribute(Constants.BEARER_TOKEN_ATTRIBUTE);
        return getWebClient().get()
                .uri("/transactions/code/{transactionId}", transactionId)
                .headers(headers -> headers.setBearerAuth(bearerToken))
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }

    public void updateOrderStatus(String transactionId, String status) {
        // Pega o token armazenado no filtro
        String bearerToken = (String) request.getAttribute(Constants.BEARER_TOKEN_ATTRIBUTE);
        getWebClient().put()
                .uri("/transactions/{transactionId}/status/{status}", transactionId, status)
                .headers(headers -> headers.setBearerAuth(bearerToken))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private String getTransactionAPiUrl() {
        return transactionAPiUrl;
    }
}