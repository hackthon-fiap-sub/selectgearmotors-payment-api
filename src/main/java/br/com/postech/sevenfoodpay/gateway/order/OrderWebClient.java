package br.com.postech.sevenfoodpay.gateway.order;

import br.com.postech.sevenfoodpay.gateway.dto.TransactionResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OrderWebClient {

    private final WebClient webClient;

    public OrderWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:9934/api/v1").build();
    }

    public TransactionResponse getOrderById(String transactionId) {
        return webClient.get()
                .uri("/transactions/code/{transactionId}", transactionId)
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }

    public void updateOrderStatus(String transactionId, String status) {
        webClient.put()
                .uri("/transactions/{transactionId}/status/{status}", transactionId, status)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}