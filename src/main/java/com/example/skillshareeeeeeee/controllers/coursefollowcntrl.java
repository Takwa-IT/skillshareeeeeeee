package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.CourseFollowDTO;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.services.coursefollowsrvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-follows")
public class coursefollowcntrl {

    private final coursefollowsrvc followService;

    public coursefollowcntrl(coursefollowsrvc followService) {
        this.followService = followService;
    }

    // Créer un nouveau suivi
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CourseFollowDTO>> create(@RequestBody CourseFollowDTO dto) {
        ApiResponse<CourseFollowDTO> response = followService.createFollow(dto);
        int status = (response.getData() == null) ? 400 : 201;
        return ResponseEntity.status(status).body(response);
    }

    // Récupérer tous les suivis
    @GetMapping("/getAll")
    public ResponseEntity<List<ApiResponse<CourseFollowDTO>>> getAll() {
        List<ApiResponse<CourseFollowDTO>> follows = followService.getAllFollows();
        return ResponseEntity.ok(follows);
    }

    // Récupérer un suivi par ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<CourseFollowDTO>> getById(@PathVariable Integer id) {
        ApiResponse<CourseFollowDTO> response = followService.getFollowById(id);
        int status = "SUCCESS".equals(response.getStatus()) ? 200 : 404;
        return ResponseEntity.status(status).body(response);
    }

    // Mettre à jour un suivi
    @PutMapping("/updateById/{id}")
    public ResponseEntity<ApiResponse<CourseFollowDTO>> update(@PathVariable Integer id, @RequestBody CourseFollowDTO dto) {
        ApiResponse<CourseFollowDTO> response = followService.updateFollow(id, dto);
        int status = "SUCCESS".equals(response.getStatus()) ? 200 : 404;
        return ResponseEntity.status(status).body(response);
    }

    // Supprimer un suivi
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        ApiResponse<Void> response = followService.deleteFollow(id);
        int status = "SUCCESS".equals(response.getStatus()) ? 200 : 404;
        return ResponseEntity.status(status).body(response);
    }
}