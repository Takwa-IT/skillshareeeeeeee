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

    // Créer une catégorie
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Récupérer toutes les catégories (version DTO)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Récupérer une catégorie par ID
    public Optional<CategoryDto> getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(this::convertToDto);
    }

    // Mettre à jour une catégorie
    public Optional<CategoryDto> updateCategory(Integer id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    categoryRepository.save(category);
                    return convertToDto(category);
                });
    }

    // Supprimer une catégorie
    public boolean deleteCategory(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Vérifier si une catégorie existe par nom
    public boolean categoryExistsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    // Trouver une catégorie par ID (modèle)
    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // Convertir une catégorie en DTO
    public CategoryDto convertToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getImage()
        );
    }
}
