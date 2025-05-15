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

    public coursecntrl(coursesrvc courseService) {
        this.courseService = courseService;
    }

    // 📤 Récupérer tous les cours
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

    // 📥 Récupérer un cours par ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseById(@PathVariable Integer id) {
        Optional<CourseDto> courseOptional = courseService.getCourseById(id);

        if (courseOptional.isPresent()) {
            ApiResponse<CourseDto> response = new ApiResponse<>("SUCCESS", courseOptional.get());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<CourseDto> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // ➕ Créer un cours
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CourseDto>> createCourse(@RequestBody CourseDto dto) {
        try {
            CourseDto created = courseService.createCourse(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("SUCCESS", created));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("FAILURE", null));
        }
    }

    // 🔁 Modifier un cours
    @PutMapping("/updateById/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> updateCourse(
            @PathVariable Integer id,
            @RequestBody CourseDto dto) {

        Optional<CourseDto> updated = courseService.updateCourse(id, dto);

        if (updated.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", updated.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("FAILURE", null));
        }
    }

    // ❌ Supprimer un cours
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Integer id) {
        boolean deleted = courseService.deleteCourse(id);

        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("FAILURE", null));
        }
    }
}