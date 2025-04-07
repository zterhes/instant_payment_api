package com.example.payment_api.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("webClientBuilder")
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
