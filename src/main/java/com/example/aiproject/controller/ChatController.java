package com.example.aiproject.controller;

import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.entity.ChatApplicationResponse;
import com.example.aiproject.service.ChatJobImplementationService;
import com.example.aiproject.service.impl.ChatJobImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ChatController {
     private ChatJobImplementationService chatService;

     public ChatController(ChatJobImplementationService chatService) {
         this.chatService = chatService;
     }

    @GetMapping("/fullresponse")
    public Mono<ChatResponse> getFullResponse(){
        return chatService.fetchChatResponse();
    }

    @GetMapping("/chatresponse")
    public Mono<ChatApplicationResponse> getChatResponse(){
        return chatService.chatApplicationResponse();
    }

}
