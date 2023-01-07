package com.williamfeliciano.blogrestapi.service;

import com.williamfeliciano.blogrestapi.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long id);

    List<CategoryDto> getCategories();

    CategoryDto updateCategory(Long id, CategoryDto category);

    void deleteCategory(Long id);

}
