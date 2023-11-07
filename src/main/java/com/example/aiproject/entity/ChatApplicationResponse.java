package com.example.aiproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="chat_application_responses")
public class ChatApplicationResponse {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @JsonIgnore
    private int id;
    @Column(columnDefinition = "TEXT")
    private String answer;

    @JsonProperty("prompt_tokens") @JsonIgnore
    private int promptTokens;

    @JsonProperty("completion_tokens") @JsonIgnore
    private int completionTokens;

    @JsonProperty("total_tokens") @JsonIgnore
    private int totalTokens;
}
