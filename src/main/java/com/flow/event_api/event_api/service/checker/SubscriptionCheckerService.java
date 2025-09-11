package com.flow.event_api.event_api.service.checker;

import com.flow.event_api.event_api.aop.AccessCheckType;
import com.flow.event_api.event_api.service.SubscriptionService;
import com.flow.event_api.event_api.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionCheckerService extends AbstractAccessCheckerService<SubscriptionCheckerService.SubscriptionAccessData>{

    private final SubscriptionService subscriptionService;

    @Override
    protected boolean check(SubscriptionAccessData accessData) {
        if (accessData.categoryId != null && accessData.organizationId != null) {
            return subscriptionService.hasOrganizationSubscription(accessData.currentUser, accessData.organizationId)
                    && subscriptionService.hasCategorySubscription(accessData.currentUser, accessData.categoryId);
        }
        if (accessData.categoryId == null) {
            return subscriptionService.hasOrganizationSubscription(accessData.currentUser, accessData.organizationId);
        } else {
            return subscriptionService.hasCategorySubscription(accessData.currentUser, accessData.categoryId);
        }
    }

    @Override
    protected SubscriptionAccessData getAccessData(HttpServletRequest request) {
        return new SubscriptionAccessData(
                getFromRequestParams(request, "categoryId", Long::valueOf),
                getFromRequestParams(request, "organizationId", Long::valueOf),
                AuthUtils.getAuthenticatedUser().getId()
        );
    }

    @Override
    public AccessCheckType getType() {
        return AccessCheckType.SUBSCRIPTION;
    }

    protected record SubscriptionAccessData(Long categoryId, Long organizationId, Long currentUser) implements AbstractAccessCheckerService.AccessData {}
}
