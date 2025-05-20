package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.CategoryDto;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.models.Category;
import com.example.skillshareeeeeeee.services.categorysrvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class categorycntrl {

    private final categorysrvc categoryService;

    public categorycntrl(categorysrvc categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        ApiResponse<List<CategoryDto>> response = new ApiResponse<>("success", categories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable Integer id) {
        Optional<CategoryDto> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>("success", category.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("failure", null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        return ResponseEntity.ok(new ApiResponse<>("success", categoryService.convertToDto(created)));
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
            @PathVariable Integer id,
            @RequestBody Category categoryDetails) {
        Optional<CategoryDto> updated = categoryService.updateCategory(id, categoryDetails);
        if (updated.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>("success", updated.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("failure", null));
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Integer id) {
        boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>("success", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("failure", null));
        }
    }
    // Nouvel endpoint pour récupérer l'image par ID
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getCategoryImage(@PathVariable int id) {
        byte[] imageBytes = categoryService.getCategoryImageById(id); // Méthode pour récupérer le BLOB
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}
