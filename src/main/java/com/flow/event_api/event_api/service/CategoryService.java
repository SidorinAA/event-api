package com.flow.event_api.event_api.service;

import com.flow.event_api.event_api.entity.Category;
import com.flow.event_api.event_api.exeption.EntityNotFoundException;
import com.flow.event_api.event_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Category with id %s not found", id)));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Set<Category> upsertCategories(Set<Category> categories) {
        Set<String> eventCategories = categories.stream()
                .map(Category::getName).collect(Collectors.toSet());
        List<Category> existedCategories = categoryRepository.findAllByNameIn(eventCategories);
        Set<String> existedCategoryNames = existedCategories.stream()
                .map(Category::getName).collect(Collectors.toSet());
        List<Category> categoriesForUpdate = categories.stream()
                .filter(it -> !existedCategoryNames.contains(it.getName()))
                .toList();
        return Stream.concat(existedCategories.stream(), categoryRepository.saveAll(categoriesForUpdate).stream())
                .collect(Collectors.toSet());
    }
}
