package com.flow.event_api.event_api.repository;

import com.flow.event_api.event_api.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
