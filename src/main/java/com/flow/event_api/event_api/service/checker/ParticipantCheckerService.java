package com.flow.event_api.event_api.service.checker;

import com.flow.event_api.event_api.aop.AccessCheckType;
import com.flow.event_api.event_api.service.EventService;
import com.flow.event_api.event_api.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantCheckerService extends AbstractAccessCheckerService<ParticipantCheckerService.ParticipantAccessData> {

    private final EventService eventService;

    @Override
    protected boolean check(ParticipantAccessData accessData) {
        return eventService.hasParticipant(accessData.eventId, accessData.participantId);
    }

    @Override
    protected ParticipantAccessData getAccessData(HttpServletRequest request) {
        var eventId = getFromPathVariable(
                request,
                "id",
                Long::valueOf
        );
        return new ParticipantAccessData(eventId, AuthUtils.getAuthenticatedUser().getId());
    }

    @Override
    public AccessCheckType getType() {
        return AccessCheckType.PARTICIPANT;
    }

    protected record ParticipantAccessData(Long eventId, Long participantId) implements AccessData {}
}
