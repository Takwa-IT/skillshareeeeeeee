package com.example.skillshareeeeeeee.services;

import com.example.skillshareeeeeeee.dto.CommentDTO;
import com.example.skillshareeeeeeee.dto.CourseDto;
import com.example.skillshareeeeeeee.dto.LessonDto;
import com.example.skillshareeeeeeee.models.Category;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.categoryrep;
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.repositories.userrep;
import com.example.skillshareeeeeeee.services.commentsrvc;
import com.example.skillshareeeeeeee.services.lessonsrvc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class coursesrvc {

    private final courserep courseRepository;
    private final lessonsrvc lessonService;
    private final commentsrvc commentService;

    private final userrep userRepository;
    private final categoryrep categoryRepository;

    public coursesrvc(courserep courseRepository, lessonsrvc lessonService,userrep userRepository, commentsrvc commentService,categoryrep categoryRepository) {
        this.courseRepository = courseRepository;
        this.lessonService = lessonService;
        this.commentService = commentService;
        this.userRepository=userRepository;
        this.categoryRepository=categoryRepository;
    }

    public CourseDto convertToDto(coursemdl course) {

        List<CommentDTO> commentDtos = Optional.ofNullable(course.getComments()).orElse(List.of()).stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getDescription(),
                        Optional.ofNullable(comment.getUser())
                                .map(usermdl::getId)
                                .orElse(null) , // handle null user
                        comment.getCourse().getId()
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

    public Optional<CourseDto> getCourseById(Integer id) {
        return courseRepository.findById(id).map(this::convertToDto);
    }

    public CourseDto createCourse(CourseDto dto) {
        coursemdl course = new coursemdl();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setDownload_counts(dto.getDownloadCounts());
        course.setView_counts(dto.getViewCounts());

        if (dto.getUserId() != null) {
            usermdl owner = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            course.setOwner(owner);
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            course.setCategory(category);
        }

        coursemdl saved = courseRepository.save(course);
        return convertToDto(saved);
    }

    public Optional<CourseDto> updateCourse(Integer id, CourseDto dto) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setTitle(dto.getTitle());
                    course.setDescription(dto.getDescription());
                    course.setDownload_counts(dto.getDownloadCounts());
                    course.setView_counts(dto.getViewCounts());

                    if (dto.getUserId() != null) {
                        usermdl owner = userRepository.findById(dto.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                        course.setOwner(owner);
                    }

                    if (dto.getCategoryId() != null) {
                        Category category = categoryRepository.findById(dto.getCategoryId())
                                .orElseThrow(() -> new RuntimeException("Category not found"));
                        course.setCategory(category);
                    }

                    coursemdl updated = courseRepository.save(course);
                    return convertToDto(updated);
                });
    }

    public boolean deleteCourse(Integer id) {
        if (!courseRepository.existsById(id)) {
            return false;
        }

        coursemdl course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.getLessons().clear();
        course.getComments().clear();

        courseRepository.delete(course);
        return true;
    }
}