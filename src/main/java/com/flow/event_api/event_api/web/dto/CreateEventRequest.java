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
public class CreateEventRequest {

    @NotBlank(message = "Name must be set!")
    private String name;

    @NotNull(message = "Start time must be set")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant startTime;

    @NotNull(message = "End time must be set")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant endTime;

    private Set<@NotBlank(message = "Category must be not blank") String> categories;

    @NotBlank(message = "Schedule must be not blank")
    private String schedule;

    @NotBlank(message = "City must be not blank")
    private String cityLocation;

    @NotBlank(message = "Street must be not blank")
    private String streetLocation;

    @NotNull(message = "Organization must be set")
    private Long organizationId;

    @NotBlank(message = "Creator must be set")
    private Long creatorId;
}
