package br.com.postech.sevenfoodpay.gateway.order;

import br.com.postech.sevenfoodpay.gateway.dto.TransactionResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OrderWebClient {

    @Getter
    @Value("${transaction.api.url}")
    private String transactionAPiUrl;

    private final WebClient.Builder webClientBuilder;

    private WebClient webClient;

    public OrderWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private WebClient getWebClient() {
        if (this.webClient == null) {
            this.webClient = webClientBuilder.baseUrl(getTransactionAPiUrl()).build();
        }
        return this.webClient;
    }

    public TransactionResponse getOrderById(String transactionId) {
        return getWebClient().get()
                .uri("/transactions/code/{transactionId}", transactionId)
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }

    public void updateOrderStatus(String transactionId, String status) {
        getWebClient().put()
                .uri("/transactions/{transactionId}/status/{status}", transactionId, status)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private String getTransactionAPiUrl() {
        return transactionAPiUrl;
    }
}