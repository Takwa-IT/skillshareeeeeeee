package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.CommentDTO;
import com.example.skillshareeeeeeee.dto.CourseDto;
import com.example.skillshareeeeeeee.dto.LessonDto;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl; // ✅ Add this line
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.services.coursesrvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class coursecntrl {

    private final coursesrvc courseService;
    private final courserep courseRepository;


    public coursecntrl(coursesrvc courseService, courserep courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }
    public CourseDto convertToDto(coursemdl course) {

        List<CommentDTO> commentDtos = Optional.ofNullable(course.getComments()).orElse(List.of()).stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getDescription(),
                        Optional.ofNullable(comment.getUser())
                                .map(usermdl::getId)
                                .orElse(null),
                        comment.getCourse().getId()// handle null user
                ))
                .toList();

        List<LessonDto> lessonDtos = Optional.ofNullable(course.getLessons()).orElse(List.of()).stream()
                .map(lesson -> new LessonDto(
                        lesson.getId(),
                        lesson.getTitle(),
                        lesson.getUrlPdf(),
                        Optional.ofNullable(lesson.getCourse())
                                .map(coursemdl::getId)
                                .orElse(null)
                ))
                .toList();

        Integer categoryId = Optional.ofNullable(course.getCategory())
                .map(cat -> cat.getId())
                .orElse(null);

        Integer ownerId = Optional.ofNullable(course.getOwner())
                .map(usermdl::getId)
                .orElse(null);

        return new CourseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getView_counts(),
                course.getDownload_counts(),
                ownerId,
                categoryId,
                commentDtos,
                lessonDtos
        );
    }

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

    @GetMapping("/getAll")
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }
}