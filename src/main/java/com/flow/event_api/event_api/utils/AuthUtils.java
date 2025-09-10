package com.flow.event_api.event_api.utils;

import com.flow.event_api.event_api.security.AppUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class AuthUtils {

    public Long getCurrentUserId(UserDetails userDetails) {
        if (userDetails instanceof AppUserDetails details) {
            return details.getId();
        }

        throw new SecurityException("UserDetails is not instanceOf AppUserDetails");
    }

    public AppUserDetails getAuthenticatedUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof AppUserDetails details) {
            return details;
        }

        throw new SecurityException("Principal in security context is not instanceOf AppUserDetails");
    }

}
