package com.example.aiproject.service;

import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.entity.ChatApplicationResponse;
import com.example.aiproject.entity.PromptInput;
import reactor.core.publisher.Mono;

public interface ChatJobImplementationService {

    void setUserContent(PromptInput content);

    ChatResponse fetchChatResponse();

    ChatApplicationResponse chatApplicationResponse();

}
