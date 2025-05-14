package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.CategoryDto;
import com.example.skillshareeeeeeee.dto.CourseDto;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.models.Category;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.userrep;
import com.example.skillshareeeeeeee.services.coursesrvc;
import com.example.skillshareeeeeeee.services.categorysrvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class coursecntrl {

    private final coursesrvc courseService;
    private final categorysrvc categoryService;
    private final userrep userRepository;

    public coursecntrl(coursesrvc courseService, categorysrvc categoryService, userrep userRepository) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }

    // Méthode pour mapper CourseDto vers coursemdl (si nécessaire)
    private coursemdl mapToCourseModel(CourseDto courseDto) {
        coursemdl course = new coursemdl();
        course.setId(courseDto.getId());
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());

        if (courseDto.getCategoryId() != null) {
            CategoryDto categoryDto = categoryService.getCategoryById(courseDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Category category = new Category();
            category.setId(categoryDto.getId());
            category.setName(categoryDto.getName());
            category.setUrlImg(categoryDto.getUrlImg());

            course.setCategory(category);
        }

        return course;
    }

    // Conversion du DTO vers entité avec gestion de l'utilisateur
    private coursemdl convertToEntity(CourseDto dto) {
        coursemdl course = new coursemdl();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setDownload_counts(dto.getDownloadCounts());
        course.setView_counts(dto.getViewCounts());

        if (dto.getId() != null) {
            usermdl user = userRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            course.setOwner(user);
        }

        return course;
    }

    // Récupérer tous les cours
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<CourseDto>>> getAllCourses() {
        try {
            List<CourseDto> courses = courseService.getAllCourses();
            ApiResponse<List<CourseDto>> response = new ApiResponse<>("SUCCESS", courses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<CourseDto>> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer un cours par ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseById(@PathVariable Integer id) {
        try {
            Optional<CourseDto> courseOptional = courseService.getCourseById(id);

            if (courseOptional.isPresent()) {
                ApiResponse<CourseDto> response = new ApiResponse<>("SUCCESS", courseOptional.get());
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<CourseDto> response = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<CourseDto> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Créer un nouveau cours
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<coursemdl>> createCourse(@RequestBody CourseDto dto) {
        try {
            coursemdl course = convertToEntity(dto);
            coursemdl saved = courseService.createCourse(course);

            ApiResponse<coursemdl> response = new ApiResponse<>("SUCCESS", saved);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse<coursemdl> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Mettre à jour un cours
    @PutMapping("/updateById/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> updateCourse(
            @PathVariable Integer id,
            @RequestBody CourseDto dto) {
        try {
            Optional<CourseDto> updatedCourse = courseService.updateCourse(id, dto);

            if (updatedCourse.isPresent()) {
                ApiResponse<CourseDto> response = new ApiResponse<>("SUCCESS", updatedCourse.get());
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<CourseDto> response = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<CourseDto> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Supprimer un cours
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Integer id) {
        try {
            boolean deleted = courseService.deleteCourse(id);

            if (deleted) {
                ApiResponse<Void> response = new ApiResponse<>("SUCCESS", null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Void> response = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}