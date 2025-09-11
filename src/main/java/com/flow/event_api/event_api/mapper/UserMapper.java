package com.flow.event_api.event_api.mapper;


import com.flow.event_api.event_api.entity.User;
import com.flow.event_api.event_api.web.dto.CreateUserRequest;
import com.flow.event_api.event_api.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User toEntity(CreateUserRequest request);

    UserDto toDto(User user);

}