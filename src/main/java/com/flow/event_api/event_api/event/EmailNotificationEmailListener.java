package com.flow.event_api.event_api.event;

import com.flow.event_api.event_api.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationEmailListener {

    private final SubscriptionService subscriptionService;

    @EventListener(EmailNotificationEvent.class)
    public void onEvent(EmailNotificationEvent event) {
        log.info("Email notification event received: {}", event);
        subscriptionService.sendNotification(
                event.getOrganization(),
                event.getCategories(),
                event.getEventName()
        );
    }
}
