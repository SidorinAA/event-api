package com.flow.event_api.event_api.repository;

import com.flow.event_api.event_api.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByCityAndStreet(String city, String street);
}
