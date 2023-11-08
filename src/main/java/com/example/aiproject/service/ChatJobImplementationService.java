package com.example.aiproject.service;

import com.example.aiproject.dto.ChatRequest;
import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.entity.ChatApplicationResponse;
import com.example.aiproject.entity.PromptInput;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatJobImplementationService {

    void setUserContent(PromptInput content);

    Mono<ChatResponse> fetchChatResponse();

    Mono<ChatApplicationResponse> chatApplicationResponse();

}
