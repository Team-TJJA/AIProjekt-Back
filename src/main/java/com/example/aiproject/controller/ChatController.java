package com.example.aiproject.controller;

import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.entity.ChatApplicationResponse;
import com.example.aiproject.entity.PromptInput;
import com.example.aiproject.service.ChatJobImplementationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ChatController {
     private ChatJobImplementationService chatService;

     public ChatController(ChatJobImplementationService chatService) {
         this.chatService = chatService;
     }

    @GetMapping("/fullresponse")
    public ChatResponse getFullResponse(){
        return chatService.fetchChatResponse();
    }


    @PostMapping("/chatresponse")
    public ChatApplicationResponse getChatResponseFromList(@RequestBody PromptInput content){
        chatService.setUserContent(content);
        return chatService.chatApplicationResponse();
    }
}
