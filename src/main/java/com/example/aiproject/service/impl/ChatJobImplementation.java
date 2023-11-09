package com.example.aiproject.service.impl;

import com.example.aiproject.dto.ChatRequest;
import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.dto.Message;
import com.example.aiproject.entity.ChatApplicationResponse;
import com.example.aiproject.entity.PromptInput;
import com.example.aiproject.repository.ChatRepository;
import com.example.aiproject.service.ChatJobImplementationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatJobImplementation implements ChatJobImplementationService {
        private final String model = "gpt-3.5-turbo";
        private final double temperature = 1;
        private final int maxTokens = 1500;
        private final double topP = 0.8;
        private final double frequencyPenalty= 0.0;
        private final double presencePenalty= 0.0;

        private WebClient webClient;
        private ChatRepository chatRepository;
        private List<Message> messages = new ArrayList<>();

        public ChatJobImplementation(WebClient.Builder builder, ChatRepository chatRepository) {
            this.webClient = builder.build();
            this.chatRepository = chatRepository;
        }

        public void setUserContent(PromptInput content) {
            this.messages.clear();
            messages.add(new Message("system", "You are a helpful assistant that can take two user inputs and make a motivated job application written in the language of the job add"));
            messages.add(new Message("user", content.getResume()));
            messages.add(new Message("user", content.getJobAdd()));
            System.out.println(messages);
        }

        public ChatRequest setupChatRequest() {
            ChatRequest chatRequest = new ChatRequest();
            chatRequest.setModel(model);
            chatRequest.setMessages(messages);
            chatRequest.setTemperature(temperature);
            chatRequest.setMaxTokens(maxTokens);
            chatRequest.setTopP(topP);
            chatRequest.setFrequencyPenalty(frequencyPenalty);
            chatRequest.setPresencePenalty(presencePenalty);

            return chatRequest;
        }

        public ChatResponse fetchChatResponse() {
            ChatRequest chatRequest = setupChatRequest();
            return webClient
                    .post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(chatRequest)
                    .retrieve()
                    .bodyToMono(ChatResponse.class).block();
        }

        public ChatApplicationResponse chatApplicationResponse() {
           ChatResponse data = fetchChatResponse();
           ChatApplicationResponse response = new ChatApplicationResponse();
           response.setAnswer(data.getChoices().get(0).getMessage().getContent());
           response.setPromptTokens(data.getUsage().getPromptTokens());
           response.setCompletionTokens(data.getUsage().getCompletionTokens());
           response.setTotalTokens(data.getUsage().getTotalTokens());

           persistData(response);

           return response;
        }


        public void persistData(ChatApplicationResponse response) {
            chatRepository.save(response);
        }
}
