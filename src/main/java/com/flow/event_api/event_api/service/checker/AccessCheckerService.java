package com.flow.event_api.event_api.service.checker;

import com.flow.event_api.event_api.aop.AccessCheckType;
import com.flow.event_api.event_api.aop.Accessible;
import jakarta.servlet.http.HttpServletRequest;

public interface AccessCheckerService {

    boolean check(HttpServletRequest req, Accessible accessible);

    AccessCheckType getType();
}
