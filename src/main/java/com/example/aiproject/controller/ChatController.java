package com.example.aiproject.controller;

import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.service.ChatJobImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ChatController {

    @Autowired
    ChatJobImplementation chatJobImplementation;


    @GetMapping("/test")
    public Mono<ChatResponse> getChatResponse(){
        return chatJobImplementation.fetchChatResponse();
    }
}
