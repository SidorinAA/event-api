package com.flow.event_api.event_api.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {

    private String name;

    @NotNull(message = "Start time must be set")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant startTime;

    @NotNull(message = "End time must be set")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant endTime;

    private Set<@NotBlank(message = "Category must be not blank") String> categories;

    private String schedule;
}
