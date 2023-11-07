package com.example.aiproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Choice {
    private int index;
    private Message message;
    @JsonProperty("finish_reason")
    private String finishReason;
}
