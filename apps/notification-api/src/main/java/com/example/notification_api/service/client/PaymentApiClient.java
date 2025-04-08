package com.example.notification_api.service.client;

import com.example.shared_lib.dto.GetTransactionResponse;
import com.example.shared_lib.exception.ClientCallException;
import com.example.shared_lib.exception.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Log4j2
@Service
public class PaymentApiClient {

    private final WebClient webClient;
    private final String userApiUrl;
    private final String getPayment;

    public PaymentApiClient(
            @Autowired @Qualifier("webClientBuilder") WebClient.Builder webClientBuilder,
            @Value("${payment_api.url}") String userApiUrl,
            @Value("${payment-api.get-payment}") String getPayment) {
        this.webClient = webClientBuilder.baseUrl(userApiUrl).build();
        this.userApiUrl = userApiUrl;
        this.getPayment = getPayment;
    }

    public GetTransactionResponse getTransaction(String token) {
        log.info("Calling user api {} ", userApiUrl);
        return webClient.get().uri(getPayment)
                .retrieve()
                .bodyToMono(GetTransactionResponse.class)
                .onErrorResume(e -> {
                    if (e instanceof WebClientResponseException) {
                        WebClientResponseException webClientResponseException = (WebClientResponseException) e;
                        ErrorResponse errorResponse = webClientResponseException.getResponseBodyAs(ErrorResponse.class);
                        log.error("errorResponse: {}", errorResponse);
                        throw new ClientCallException(errorResponse.message());

                    } else {
                        log.error(e.getMessage());
                        throw new ClientCallException(e.getMessage());
                    }
                })
                .block();

    }
}
