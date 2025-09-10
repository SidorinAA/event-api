package com.flow.event_api.event_api.service;

import com.flow.event_api.event_api.entity.Event;
import com.flow.event_api.event_api.model.EventFilterModel;
import com.flow.event_api.event_api.repository.EventRepository;
import com.flow.event_api.event_api.repository.LocationRepository;
import com.flow.event_api.event_api.repository.ScheduleRepository;
import com.flow.event_api.event_api.repository.specification.EventSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final ScheduleRepository scheduleRepository;
    private final LocationRepository locationRepository;
    private final OrganizationService organizationService;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Page<Event> filter(EventFilterModel filterModel) {
        return eventRepository.findAll(
                EventSpecification.withFilter(filterModel),
                filterModel.getPage().toPageRequest()
        );
    }

    // 1:42

}
