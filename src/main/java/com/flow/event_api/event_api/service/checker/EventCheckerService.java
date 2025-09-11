package com.flow.event_api.event_api.service.checker;

import com.flow.event_api.event_api.aop.AccessCheckType;
import com.flow.event_api.event_api.aop.Accessible;
import com.flow.event_api.event_api.service.EventService;
import com.flow.event_api.event_api.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventCheckerService extends AbstractAccessCheckerService<EventCheckerService.EventAccessData> {

    private final EventService eventService;

    @Override
    protected EventAccessData getAccessData(HttpServletRequest request) {
        var eventId = getFromPathVariable(
                request,
                "id",
                Long::valueOf
        );
        return new EventAccessData(eventId, AuthUtils.getAuthenticatedUser().getId());
    }

    @Override
    public AccessCheckType getType() {
        return AccessCheckType.EVENT;
    }

    @Override
    protected boolean check(EventAccessData accessData) {
        return eventService.isEventCreator(
                accessData.eventId,
                accessData.currentUserId
        );
    }

    protected record EventAccessData(Long eventId, Long currentUserId) implements AccessData {

    }
}
