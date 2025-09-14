package com.flow.event_api.event_api.service;

import com.flow.event_api.event_api.entity.Organization;
import com.flow.event_api.event_api.entity.Role;
import com.flow.event_api.event_api.exeption.AccessDeniedException;
import com.flow.event_api.event_api.exeption.EntityNotFoundException;
import com.flow.event_api.event_api.repository.OrganizationRepository;
import com.flow.event_api.event_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final UserRepository userRepository;

    @Transactional
    public Organization save(Organization organization, Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (!user.hasRole(Role.ROLE_ORGANIZATION_OWNER)) {
                        throw new AccessDeniedException("You dont has rights");
                    }
                    organization.setOwner(user);
                    return organizationRepository.save(organization);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("User with id {0} not found", userId)));
    }

    public Organization findById(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Organization with id {0} not found!", id)
        ));
    }

    //2:32:56
}
