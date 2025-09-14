package com.flow.event_api.event_api.web.controller;

import com.flow.event_api.event_api.aop.AccessCheckType;
import com.flow.event_api.event_api.aop.Accessible;
import com.flow.event_api.event_api.exeption.AccessDeniedException;
import com.flow.event_api.event_api.mapper.EventMapper;
import com.flow.event_api.event_api.service.EventService;
import com.flow.event_api.event_api.utils.AuthUtils;
import com.flow.event_api.event_api.web.dto.CreateEventRequest;
import com.flow.event_api.event_api.web.dto.EventDto;
import com.flow.event_api.event_api.web.dto.UpdateEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ORGANIZATION_OWNER')")
    public ResponseEntity<EventDto> createEvent(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateEventRequest request
    ) {
        var isNotSameUser = !ObjectUtils.nullSafeEquals(request.getCreatorId(), AuthUtils.getCurrentUserId(userDetails));
        if (isNotSameUser) {
            throw new AccessDeniedException("You cant create event");
        }

        var createdEvent = eventService.create(eventMapper.toEntity(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(eventMapper.toDto(createdEvent));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ORGANIZATION_OWNER')")
    @Accessible(checkBy = AccessCheckType.EVENT)
    public ResponseEntity<EventDto> updateEvent(
            @RequestBody UpdateEventRequest request,
            @PathVariable Long id
    ) {
        var updatedEvent = eventService.update(id, eventMapper.toEntity(request));

        return ResponseEntity.ok(eventMapper.toDto(updatedEvent));
    }

    @PutMapping("/{id}/participant")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ORGANIZATION_OWNER')")
    public ResponseEntity<String> addParticipantToEvent(@AuthenticationPrincipal UserDetails userDetails,
                                                        @PathVariable Long id) {
        var isAdded = eventService.addParticipant(id, AuthUtils.getCurrentUserId(userDetails));
        if (isAdded) {
            return ResponseEntity.ok("User was add to event");
        } else {
            return ResponseEntity.badRequest().body("Can't set user on event");
        }
    }

    @DeleteMapping("/{id}/participant")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ORGANIZATION_OWNER')")
    @Accessible(checkBy = AccessCheckType.PARTICIPANT)
    public ResponseEntity<String> removeParticipantFromEvent(@AuthenticationPrincipal UserDetails userDetails,
                                                             @PathVariable Long id) {
        var isRemoved = eventService.removeParticipant(id, AuthUtils.getCurrentUserId(userDetails));
        if (isRemoved) {
            return ResponseEntity.ok("User was remove from event");
        } else {
            return ResponseEntity.badRequest().body("Can't remove user from event");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ORGANIZATION_OWNER')")
    @Accessible(checkBy = AccessCheckType.EVENT)
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
