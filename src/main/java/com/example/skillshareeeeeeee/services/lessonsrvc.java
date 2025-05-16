package com.example.skillshareeeeeeee.services;

import com.example.skillshareeeeeeee.dto.LessonDto;
import com.example.skillshareeeeeeee.models.lessonmdl;
import com.example.skillshareeeeeeee.repositories.lessonrep;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class lessonsrvc {

    private final lessonrep lessonRepository;

    public lessonsrvc(lessonrep lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    // Créer une leçon
    public lessonmdl createLesson(lessonmdl lesson) {
        return lessonRepository.save(lesson);
    }

    public List<LessonDto> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<LessonDto> getLessonById(Integer id) {
        return lessonRepository.findById(id)
                .map(this::convertToDto);
    }

    public Optional<LessonDto> updateLesson(Integer id, lessonmdl lessonDetails) {
        return lessonRepository.findById(id)
                .map(lesson -> {
                    lesson.setTitle(lessonDetails.getTitle());
                    lesson.setUrlPdf(lessonDetails.getUrlPdf());
                    lesson.setCourse(lessonDetails.getCourse());
                    lessonRepository.save(lesson);
                    return convertToDto(lesson);
                });
    }

    public boolean deleteLesson(Integer id) {
        lessonRepository.deleteById(id);
        return true;
    }

    public LessonDto convertToDto(lessonmdl lesson) {
        return new LessonDto(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getUrlPdf(),
                lesson.getCourse().getId()
        );
    }

    public List<LessonDto> findLessonsByCourseId(Integer courseId) {
        return lessonRepository.findByCourseId(courseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
