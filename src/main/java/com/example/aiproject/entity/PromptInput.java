package com.example.aiproject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PromptInput {
    @JsonProperty("resume")
    private String resume;
    @JsonProperty("jobAdd")
    private String jobAdd;
}
