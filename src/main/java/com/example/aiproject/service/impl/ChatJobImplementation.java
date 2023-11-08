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
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatJobImplementation implements ChatJobImplementationService {
        private final String model = "gpt-3.5-turbo";
        private final double temperature = 1;
        private final int maxTokens = 500;
        private final double topP = 1.0;
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
            messages.add(new Message("system", "You are a helpful assistant that can take two user inputs and make a motivated job application"));
            //messages.add(new Message("system", "You are a helpful assistant that can take one to multiple user input and combine them to one joke "));
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

        public Mono<ChatResponse> fetchChatResponse() {
            ChatRequest chatRequest = setupChatRequest();
            return webClient
                    .post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(chatRequest)
                    .retrieve()
                    .bodyToMono(ChatResponse.class);
        }

        public Mono<ChatApplicationResponse> chatApplicationResponse() {
            return fetchChatResponse().map(response -> {
                ChatApplicationResponse chatApplication = new ChatApplicationResponse();
                chatApplication.setAnswer(response.getChoices().get(0).getMessage().getContent());
                chatApplication.setPromptTokens(response.getUsage().getPromptTokens());
                chatApplication.setCompletionTokens(response.getUsage().getCompletionTokens());
                chatApplication.setTotalTokens(response.getUsage().getTotalTokens());

                persistData(chatApplication);

                return chatApplication;
            });
        }


        public void persistData(ChatApplicationResponse response) {
            chatRepository.save(response);
        }
}
