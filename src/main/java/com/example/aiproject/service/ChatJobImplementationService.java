package com.example.aiproject.service;

import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.entity.ChatApplicationResponse;
import reactor.core.publisher.Mono;

public interface ChatJobImplementationService {

    Mono<ChatResponse> fetchChatResponse();

    Mono<ChatApplicationResponse> chatApplicationResponse();

}
