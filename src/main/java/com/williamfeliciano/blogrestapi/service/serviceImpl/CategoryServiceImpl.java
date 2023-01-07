package com.williamfeliciano.blogrestapi.service.serviceImpl;

import com.williamfeliciano.blogrestapi.dto.CategoryDto;
import com.williamfeliciano.blogrestapi.entity.Category;
import com.williamfeliciano.blogrestapi.exception.ResourceNotFoundException;
import com.williamfeliciano.blogrestapi.repository.CategoryRepository;
import com.williamfeliciano.blogrestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","Id",id));
        return mapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(cat -> mapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category categoryToUpdate = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","id",id));
        categoryToUpdate.setId(id);
        categoryToUpdate.setName(categoryDto.getName());
        categoryToUpdate.setDescription(categoryDto.getDescription());
        Category updatedCategory =categoryRepository.save(categoryToUpdate);
        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category categoryToDelete = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","id",id));
        categoryRepository.delete(categoryToDelete);
    }
}
