package com.flow.event_api.event_api.mapper;

import com.flow.event_api.event_api.entity.Organization;
import com.flow.event_api.event_api.web.dto.CreateOrganizationRequest;
import com.flow.event_api.event_api.web.dto.OrganizationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrganizationMapper {

    Organization toEntity(CreateOrganizationRequest request);

    OrganizationDto toDto(Organization organization);

}