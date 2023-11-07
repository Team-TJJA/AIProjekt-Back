package com.example.aiproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    @JsonProperty("max_tokens")
    private int maxTokens;
    @JsonProperty("top_p")
    private double topP;
    @JsonProperty("frequency_penalty")
    private double frequencyPenalty;
    @JsonProperty("presence_penalty")
    private double presencePenalty;
}