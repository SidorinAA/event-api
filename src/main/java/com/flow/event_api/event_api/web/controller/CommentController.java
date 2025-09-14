package com.flow.event_api.event_api.web.controller;

import com.flow.event_api.event_api.aop.AccessCheckType;
import com.flow.event_api.event_api.aop.Accessible;
import com.flow.event_api.event_api.mapper.CommentMapper;
import com.flow.event_api.event_api.service.CommentService;
import com.flow.event_api.event_api.utils.AuthUtils;
import com.flow.event_api.event_api.web.dto.CommentDto;
import com.flow.event_api.event_api.web.dto.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ORGANIZATION_OWNER')")
    public ResponseEntity<CommentDto> createComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateCommentRequest request,
            @RequestParam Long eventId
            ) {
        var createComment = commentService.save(
                commentMapper.toEntity(request),
                AuthUtils.getCurrentUserId(userDetails),
                eventId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.toDto(createComment));
    }

    @DeleteMapping("/{id}")
    @Accessible(checkBy = AccessCheckType.COMMENT)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ORGANIZATION_OWNER')")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @RequestParam Long eventId) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
