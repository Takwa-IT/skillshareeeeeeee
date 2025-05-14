package com.example.skillshareeeeeeee.services;

import com.example.skillshareeeeeeee.dto.CourseDto;
import com.example.skillshareeeeeeee.models.Category;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.categoryrep;
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.repositories.userrep;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class coursesrvc {

    private final courserep courseRepository;
    private categoryrep categoryRepository;
    private userrep userRepository;
    public coursesrvc(courserep courseRepository, categoryrep categoryRepository, userrep userRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;

    }
    // Créer un cours
    // Récupérer tous les cours (version DTO)
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Récupérer un cours par ID
    public Optional<CourseDto> getCourseById(Integer id) {
        return courseRepository.findById(id)
                .map(this::convertToDto);
    }

    // Mettre à jour un cours
    @Transactional
    public Optional<CourseDto> updateCourse(Integer id, CourseDto dto) {
        return courseRepository.findById(id).map(existingCourse -> {

            existingCourse.setTitle(dto.getTitle());
            existingCourse.setDescription(dto.getDescription());
            existingCourse.setDownload_counts(dto.getDownloadCounts());
            existingCourse.setView_counts(dto.getViewCounts());


            // Gestion de l'utilisateur (owner)
            if (dto.getId() != null) {
                usermdl user;
                // Si l'utilisateur existe déjà en base
                if (userRepository.existsById(dto.getId())) {
                    user = userRepository.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                } else {
                    // Si c'est un nouvel utilisateur (transitoire)
                    user = new usermdl();
                    user.setId(dto.getId()); // ⚠️ Cela marche uniquement si l'utilisateur est déjà existant
                    // Si pas sûr, il faut charger l'utilisateur depuis la base
                }

                existingCourse.setOwner(user);
            }

            coursemdl updatedCourse = courseRepository.save(existingCourse);
            return convertToDto(updatedCourse);
        });
    }

    // Supprimer un cours
    public boolean deleteCourse(Integer id) {
        courseRepository.deleteById(id);
        return true;
    }

    // Conversion vers DTO
    // Mapper CourseDto vers Course

    // Vérifier si le titre du cours existe
    public boolean courseExistsByTitle(String title) {
        return courseRepository.existsByTitle(title);
    }

    // Trouver un cours par ID
    public coursemdl findCourseById(Integer id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    // Mapper CourseDto vers Course
    private coursemdl mapToCourseModel(CourseDto courseDto) {
        coursemdl course = new coursemdl();
        course.setId(courseDto.getId());
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());

        // Créer un objet Category en utilisant l'ID de la catégorie
        Category category = new Category();
        category.setId(courseDto.getCategoryId());  // Utiliser l'ID pour obtenir la catégorie
        // Vous pouvez également ajouter d'autres propriétés de la catégorie si nécessaire
        course.setCategory(category);  // Assurez-vous que le champ setCategory attend un objet de type Category

        return course;
    }

    public CourseDto convertToDto(coursemdl course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setTitle(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setViewCounts(course.getView_counts());
        courseDto.setDownloadCounts(course.getDownload_counts());

        // Associer l'ID de la catégorie dans le DTO
        Category category = course.getCategory();
        if (category != null) {
            courseDto.setCategoryId(category.getId());
        }

        return courseDto;
    }

    public coursemdl findById(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }


    @Transactional
    public coursemdl createCourse(coursemdl course) {
        usermdl user = course.getOwner();

        // Si l'utilisateur n'a pas encore été sauvegardé
        if (user.getId() == null) {
            user = userRepository.save(user); // ✔️ Sauvegarde d'abord l'utilisateur
            course.setOwner(user); // Met à jour la référence
        }

        return courseRepository.save(course); // Ensuite on sauvegarde le cours
    }

}
