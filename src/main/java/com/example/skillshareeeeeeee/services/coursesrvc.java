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

    // 🔍 Convertir un modèle en DTO
    public CourseDto convertToDto(coursemdl course) {
        List<CommentDTO> commentDtos = course.getComments().stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getDescription(),
                        comment.getUser().getId(),
                        comment.getCourse().getId()
                ))
                .collect(Collectors.toList());

        List<LessonDto> lessonDtos = course.getLessons().stream()
                .map(lesson -> new LessonDto(
                        lesson.getId(),
                        lesson.getTitle(),
                        lesson.getUrlPdf(),
                        lesson.getCourse().getId()
                ))
                .collect(Collectors.toList());

        return new CourseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getDownload_counts(),
                course.getView_counts(),
                course.getOwner().getId(),
                course.getCategory().getId(),
                commentDtos,
                lessonDtos
        );
    }

    // 📤 Retourne une liste de DTOs
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 📥 Récupérer un cours par ID
    public Optional<CourseDto> getCourseById(Integer id) {
        return courseRepository.findById(id).map(this::convertToDto);
    }

    // ✅ Créer un nouveau cours
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

    // 🔄 Mettre à jour un cours existant
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

    // 🗑️ Supprimer un cours
    public boolean deleteCourse(Integer id) {
        if (!courseRepository.existsById(id)) {
            return false;
        }

        coursemdl course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Optionnel : désassocier les relations avant suppression
        course.getLessons().clear();
        course.getComments().clear();

        courseRepository.delete(course); // ou deleteById si tu veux juste supprimer
        return true;
    }
}