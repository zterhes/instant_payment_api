package com.example.payment_api.service.client;

import com.example.payment_api.exception.ClientCallException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserApiClient {

    private final WebClient webClient;
    private final String userApiUrl;
    private final String getUserUri;

    public UserApiClient(WebClient.Builder webClientBuilder,
                         @Value("${user_api.url}") String userApiUrl,
                         @Value("${user_api.get-user}") String getUserUri) {
        this.webClient = webClientBuilder.baseUrl(userApiUrl).build();
        this.userApiUrl = userApiUrl;
        this.getUserUri = getUserUri;
    }

    public UUID getUserId(String token) {
        return webClient.get().uri(getUserUri)
                .header("user-token", token)
                .retrieve()
                .bodyToMono(UUID.class)
                .onErrorResume(e -> {throw new ClientCallException(e.getMessage());})
                .block();

    }
}
