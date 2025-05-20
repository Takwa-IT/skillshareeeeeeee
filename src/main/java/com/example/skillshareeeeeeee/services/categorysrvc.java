package com.example.skillshareeeeeeee.services;

import com.example.skillshareeeeeeee.dto.CategoryDto;
import com.example.skillshareeeeeeee.models.Category;
import com.example.skillshareeeeeeee.repositories.categoryrep;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class categorysrvc {

    private final categoryrep categoryRepository;

    public categorysrvc(categoryrep categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDto> getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(this::convertToDto);
    }

    public Optional<CategoryDto> updateCategory(Integer id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    categoryRepository.save(category);
                    return convertToDto(category);
                });
    }

    public boolean deleteCategory(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public CategoryDto convertToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getImage()
        );
    }
    public byte[] getCategoryImageById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return category.getImage(); // Suppose que getImage() retourne le BLOB (byte[])
    }
}
