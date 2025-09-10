package com.flow.event_api.event_api.service.checker;

import com.flow.event_api.event_api.aop.Accessible;
import com.flow.event_api.event_api.security.AppUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractAccessCheckerService<T extends AbstractAccessCheckerService.AccessData> implements AccessCheckerService {

    @Override
    public boolean check(HttpServletRequest req, Accessible accessible) {
        if (!isUserAuthenticated()) {
            return false;
        }

        var accessData = getAccessData(req);
        return check(accessData);
    }

    @SuppressWarnings("unchecked")
    protected <V> V getFromPathVariable(HttpServletRequest request, String key, Function<String, V> converter) {
        var pathVariable = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return converter.apply(Objects.requireNonNull(pathVariable.get(key)));
    }

    protected <V> V getFromRequestParams(HttpServletRequest request, String key, Function<String, V> converter) {
        var paramValue = request.getParameter(key);
        if (Objects.isNull(paramValue)) {
            return null;
        }

        return converter.apply(paramValue);
    }

    private boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext() != null &&
               SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AppUserDetails;
    }

    protected abstract boolean check(T accessData);

    protected abstract T getAccessData(HttpServletRequest request);

    interface AccessData {

    }
}
