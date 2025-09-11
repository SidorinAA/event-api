package com.flow.event_api.event_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCommentRequest {

    @NotBlank(message = "Text for comment must be set")
    private String text;
}
