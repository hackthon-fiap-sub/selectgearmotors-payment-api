package br.com.selectgearmotors.pay.gateway.payment;

import br.com.selectgearmotors.pay.gateway.payment.dto.PaymentApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class MercadoPagoGetPaymentIdClient {

    @Value("${gateway.payment.url}")
    private String API_URL;
    @Value("${mercado_pago_sample_access_token}")
    private String AUTH_TOKEN;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<PaymentApiResponse> getPaymentById(String paymentId) {
        return webClientBuilder.build()
                .get()
                .uri(API_URL+"/payments/{id}", paymentId)
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(PaymentApiResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    // Tratamento de erro HTTP
                    System.err.println("Erro HTTP: " + e.getRawStatusCode());
                    return Mono.empty();
                })
                .onErrorResume(e -> {
                    // Tratar erro aqui (opcional)
                    System.err.println("Erro ao chamar a API: " + e.getMessage());
                    return Mono.empty();
                });
    }
}
