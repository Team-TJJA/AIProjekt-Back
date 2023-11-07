package com.example.aiproject.service;

import com.example.aiproject.dto.ChatRequest;
import com.example.aiproject.dto.ChatResponse;
import com.example.aiproject.dto.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatJobImplementation {
        private final String model = "gpt-3.5-turbo";
        private final double temperature = 0.8;
        private final int maxTokens = 500;
        private final double topP = 1.0;
        private final double frequencyPenalty= 0.0;
        private final double presencePenalty= 0.0;

        private WebClient webClient;

        public ChatJobImplementation(WebClient.Builder builder) {
            this.webClient = builder.build();
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

//        public Mono<ChatResponse> chatResponse() {
//            return fetchChatResponse().map(response -> {
//                ChatResponse chatResponse = new ChatResponse();
//                chatResponse.setAnswer(response.getChoices().get(0).getMessage().getContent());
//                chatResponse.setPromptTokens(response.getUsage().getPromptTokens());
//                chatResponse.setCompletionTokens(response.getUsage().getCompletionTokens());
//                chatResponse.setTotalTokens(response.getUsage().getTotalTokens());
//
//                persistData(chatResponse);
//
//                return chatResponse;
//            });
//        }


//        public void persistData(ChatResponse response) {
//            chatRepository.save(response);
//        }
}
