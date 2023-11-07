package com.example.aiproject.controller;

import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.entity.ChatApplicationResponse;
import com.example.aiproject.service.ChatJobImplementationService;
import com.example.aiproject.service.impl.ChatJobImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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

/*    @GetMapping("/chatresponse")
    public Mono<ChatApplicationResponse> getChatResponse(){
        return chatService.chatApplicationResponse();
    }*/

    @PostMapping("/chatresponse")
    public Mono<ChatApplicationResponse> getChatResponseFromList(@RequestBody List<String> content){
        chatService.setUserContent(content);
        return chatService.chatApplicationResponse();
    }

}
