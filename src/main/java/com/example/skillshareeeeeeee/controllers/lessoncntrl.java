package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.LessonDto;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.lessonmdl;
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.services.lessonsrvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
public class lessoncntrl {

    private final lessonsrvc lessonService;
    private final courserep courseRepository;

    public lessoncntrl(lessonsrvc lessonService, courserep courseRepository) {
        this.lessonService = lessonService;
        this.courseRepository = courseRepository;
    }

    // Mapping : LessonDto → lessonmdl
    private lessonmdl mapToLessonModel(LessonDto lessonDto) {
        lessonmdl lesson = new lessonmdl();
        lesson.setId(lessonDto.getId());
        lesson.setTitle(lessonDto.getTitle());
        lesson.setUrlPdf(lessonDto.getUrlPdf());
        return lesson;
    }

    // Récupérer toutes les leçons
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<LessonDto>>> getAllLessons() {
        try {
            List<LessonDto> lessons = lessonService.getAllLessons();
            if (lessons == null || lessons.isEmpty()) {
                ApiResponse<List<LessonDto>> errorResponse = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            ApiResponse<List<LessonDto>> response = new ApiResponse<>("SUCCESS", lessons);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<LessonDto>> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer une leçon par ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<lessonmdl>> getLessonById(@PathVariable Integer id) {
        try {
            Optional<LessonDto> lessonOptional = lessonService.getLessonById(id);

            if (lessonOptional.isPresent()) {
                lessonmdl lesson = mapToLessonModel(lessonOptional.get());
                ApiResponse<lessonmdl> response = new ApiResponse<>("SUCCESS", lesson);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<lessonmdl> errorResponse = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ApiResponse<lessonmdl> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Créer une nouvelle leçon
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<LessonDto>> createLesson(@RequestBody LessonDto lessonDto) {
        try {
            Optional<coursemdl> courseOptional = courseRepository.findById(lessonDto.getCourseId());

            if (!courseOptional.isPresent()) {
                ApiResponse<LessonDto> errorResponse = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            lessonmdl lesson = new lessonmdl();
            lesson.setTitle(lessonDto.getTitle());
            lesson.setUrlPdf(lessonDto.getUrlPdf());
            lesson.setCourse(courseOptional.get());

            lessonService.createLesson(lesson);

            ApiResponse<LessonDto> response = new ApiResponse<>("SUCCESS", lessonDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse<LessonDto> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Mettre à jour une leçon
    @PutMapping("/updateById/{id}")
    public ResponseEntity<ApiResponse<LessonDto>> updateLesson(
            @PathVariable Integer id,
            @RequestBody lessonmdl lessonDetails) {
        try {
            Optional<LessonDto> updatedLesson = lessonService.updateLesson(id, lessonDetails);
            if (updatedLesson.isPresent()) {
                ApiResponse<LessonDto> response = new ApiResponse<>("SUCCESS", updatedLesson.get());
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<LessonDto> errorResponse = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ApiResponse<LessonDto> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Supprimer une leçon
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLesson(@PathVariable Integer id) {
        try {
            boolean deleted = lessonService.deleteLesson(id);
            if (deleted) {
                ApiResponse<Void> response = new ApiResponse<>("SUCCESS", null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Void> errorResponse = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Récupérer les leçons par ID de cours
    @GetMapping("/byCourse/{courseId}")
    public ResponseEntity<ApiResponse<List<LessonDto>>> getLessonsByCourseId(@PathVariable Integer courseId) {
        try {
            List<LessonDto> lessons = lessonService.findLessonsByCourseId(courseId);

            if (lessons == null || lessons.isEmpty()) {
                ApiResponse<List<LessonDto>> errorResponse = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            ApiResponse<List<LessonDto>> response = new ApiResponse<>("SUCCESS", lessons);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<List<LessonDto>> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}