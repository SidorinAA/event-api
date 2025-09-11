package com.flow.event_api.event_api.mapper;

import com.flow.event_api.event_api.entity.Category;
import com.flow.event_api.event_api.entity.Event;
import com.flow.event_api.event_api.web.dto.CreateEventRequest;
import com.flow.event_api.event_api.web.dto.EventDto;
import com.flow.event_api.event_api.web.dto.UpdateEventRequest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventMapper {

    @Mapping(target = "schedule.description", source = "schedule")
    @Mapping(target = "location.city", source = "cityLocation")
    @Mapping(target = "location.street", source = "streetLocation")
    @Mapping(target = "organization.id", source = "organizationId")
    @Mapping(target = "organization.owner.id", source = "creatorId")
    Event toEntity(CreateEventRequest request);

    @Mapping(target = "schedule.description", source = "schedule")
    Event toEntity(UpdateEventRequest request);

    EventDto toDto(Event event);

    List<EventDto> toDtoList(List<Event> events);

    @IterableMapping(qualifiedByName = "mapToCategory")
    Set<Category> mapToCategories(Set<String> categories);

    @Named("mapToCategory")
    default Category mapToCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        return category;
    }
}
