package com.flow.event_api.event_api.repository;

import com.flow.event_api.event_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByNameIn(Set<String> names);
}
