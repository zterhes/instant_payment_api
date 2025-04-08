package com.example.payment_api.service.client;

import com.example.payment_api.exception.ClientCallException;
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
public class UserApiClient {

    private final WebClient webClient;
    private final String userApiUrl;
    private final String getUserUri;

    public UserApiClient(
            @Autowired @Qualifier("webClientBuilder") WebClient.Builder webClientBuilder,
            @Value("${user_api.url}") String userApiUrl,
            @Value("${user_api.get-user}") String getUserUri) {
        this.webClient = webClientBuilder.baseUrl(userApiUrl).build();
        this.userApiUrl = userApiUrl;
        this.getUserUri = getUserUri;
    }

    public UUID getUserId(String token) {
        log.info("Calling user api {} ", userApiUrl);
        return webClient.get().uri(getUserUri)
                .header("user-token", token)
                .retrieve()
                .bodyToMono(UUID.class)
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
