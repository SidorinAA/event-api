package com.flow.event_api.event_api.web.controller;

import com.flow.event_api.event_api.aop.AccessCheckType;
import com.flow.event_api.event_api.aop.Accessible;
import com.flow.event_api.event_api.exeption.ClientException;
import com.flow.event_api.event_api.service.SubscriptionService;
import com.flow.event_api.event_api.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ORGANIZATION_OWNER')")
    public ResponseEntity<?> subscribe(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long organizationId
    ) {
        if (categoryId == null && organizationId == null) {
            throw new ClientException("You need to set one of subscription type!");
        }
        Long userId = AuthUtils.getCurrentUserId(userDetails);

        if (categoryId != null && organizationId != null) {
            subscriptionService.subscribeOnOrganization(userId, organizationId);
            subscriptionService.subscribeOnCategory(userId, categoryId);

            return ResponseEntity.ok().build();
        }

        if (categoryId == null) {
            subscriptionService.subscribeOnOrganization(userId, organizationId);
        } else {
            subscriptionService.subscribeOnCategory(userId, categoryId);
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ORGANIZATION_OWNER')")
    @Accessible(checkBy = AccessCheckType.SUBSCRIPTION)
    public ResponseEntity<?> unsubscribe(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long organizationId
    ) {
        if (categoryId == null && organizationId == null) {
            throw new ClientException("You need to set one of subscription type!");
        }
        Long userId = AuthUtils.getCurrentUserId(userDetails);
        if (categoryId != null && organizationId != null) {
            subscriptionService.unsubscribeFromOrganization(userId, organizationId);
            subscriptionService.unsubscribeFromCategory(userId, categoryId);

            return ResponseEntity.ok().build();
        }

        if (categoryId == null) {
            subscriptionService.unsubscribeFromOrganization(userId, organizationId);
        } else {
            subscriptionService.unsubscribeFromCategory(userId, categoryId);
        }

        return ResponseEntity.noContent().build();
    }

}