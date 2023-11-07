package com.example.aiproject.service;

import com.example.aiproject.dto.ChatRequest;
import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.entity.ChatApplicationResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatJobImplementationService {

    void setUserContent(List<String> userContent);

    Mono<ChatResponse> fetchChatResponse();

    Mono<ChatApplicationResponse> chatApplicationResponse();

}
