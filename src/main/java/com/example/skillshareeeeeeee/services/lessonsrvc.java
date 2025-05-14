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

    // Récupérer toutes les leçons (version DTO)
    public List<LessonDto> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Récupérer une leçon par ID
    public Optional<LessonDto> getLessonById(Integer id) {
        return lessonRepository.findById(id)
                .map(this::convertToDto);
    }

    // Mettre à jour une leçon
    public Optional<LessonDto> updateLesson(Integer id, lessonmdl lessonDetails) {
        return lessonRepository.findById(id)
                .map(lesson -> {
                    lesson.setTitle(lessonDetails.getTitle());
                    lesson.setUrlPdf(lessonDetails.getUrlPdf());
                    lesson.setCourse(lessonDetails.getCourse());  // Mettre à jour le cours associé si nécessaire
                    lessonRepository.save(lesson);
                    return convertToDto(lesson);
                });
    }

    // Supprimer une leçon
    public boolean deleteLesson(Integer id) {
        lessonRepository.deleteById(id);
        return true;
    }

    // Conversion vers DTO
    public LessonDto convertToDto(lessonmdl lesson) {
        return new LessonDto(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getUrlPdf(),
                lesson.getCourse().getId()  // L'ID du cours associé
        );
    }

    // Trouver les leçons par ID de cours
    public List<LessonDto> findLessonsByCourseId(Integer courseId) {
        return lessonRepository.findByCourseId(courseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
