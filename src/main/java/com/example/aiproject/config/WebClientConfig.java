package com.example.aiproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {
    @Value("${chatGBT-key}")
    private String API_KEY;

    @Bean
    public WebClient.Builder ChatWebClientBuilder() {
        return WebClient.builder().baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader(HttpHeaders.AUTHORIZATION, API_KEY);
    }
}
