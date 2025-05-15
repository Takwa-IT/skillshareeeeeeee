package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.LessonDto;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.lessonmdl;
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.services.FileStorageService;
import com.example.skillshareeeeeeee.services.lessonsrvc;
import org.springframework.core.io.Resource; // ✅ Import correct
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lessons")
public class lessoncntrl {

    private final lessonsrvc lessonService;
    private final courserep courseRepository;
    private final FileStorageService fileStorageService;

    private static final String FILE_DIRECTORY = "C:/Users/takwa/IdeaProjects/skillshareeeeeeee/uploads/profiles/";



    public lessoncntrl(lessonsrvc lessonService, courserep courseRepository, FileStorageService fileStorageService) {
        this.lessonService = lessonService;
        this.courseRepository = courseRepository;
        this.fileStorageService = fileStorageService;
        ;
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

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<LessonDto>> uploadPdf(
            @RequestParam("title") String title,
            @RequestParam("pdf") MultipartFile pdfFile,
            @RequestParam("courseId") Integer courseId) {

        try {
            // 1. Trouver le cours
            coursemdl course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

            // 2. Stocker le PDF
            String fileUrl = fileStorageService.storeFile(pdfFile);

            // 3. Créer la leçon
            lessonmdl lesson = new lessonmdl();
            lesson.setTitle(title);
            lesson.setUrlPdf(fileUrl);
            lesson.setCourse(course); // ✅ Associer le cours

            lessonmdl savedLesson = lessonService.createLesson(lesson);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("success", lessonService.convertToDto(savedLesson)));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("failure", null));
        }
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Décoder les caractères spéciaux (ex: é, à, espaces encodés)
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

            // Construire le chemin complet vers le fichier
            Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erreur lors de la récupération du fichier.");
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> listAllFiles() {
        try {
            List<String> files = Files.list(Paths.get(FILE_DIRECTORY))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(files);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}