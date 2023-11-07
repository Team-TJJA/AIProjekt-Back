package com.example.aiproject.service.impl;

import com.example.aiproject.dto.ChatRequest;
import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.dto.Message;
import com.example.aiproject.entity.ChatApplicationResponse;
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
        private final double temperature = 0.8;
        private final int maxTokens = 500;
        private final double topP = 1.0;
        private final double frequencyPenalty= 0.0;
        private final double presencePenalty= 0.0;

        private WebClient webClient;
        private ChatRepository chatRepository;

        public ChatJobImplementation(WebClient.Builder builder, ChatRepository chatRepository) {
            this.webClient = builder.build();
            this.chatRepository = chatRepository;
        }

        public ChatRequest setupChatRequest() {
            ChatRequest chatRequest = new ChatRequest();
            chatRequest.setModel(model);

            List<Message> list = new ArrayList<>();

            list.add(new Message("system", "You are a helpful assistant that only provides jokes from user input"));
            list.add(new Message("user", "teacher"));

            chatRequest.setMessages(list);
            System.out.println(list);


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
