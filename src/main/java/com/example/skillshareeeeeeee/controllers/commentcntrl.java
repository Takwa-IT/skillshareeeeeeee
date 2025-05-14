package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.CommentDTO;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.services.commentsrvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class commentcntrl {

    private final commentsrvc commentService;

    public commentcntrl(commentsrvc commentService) {
        this.commentService = commentService;
    }

    // Créer un commentaire
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CommentDTO>> createComment(@RequestBody CommentDTO request) {
        try {
            CommentDTO comment = commentService.createComment(request.getUserId(), request.getCourseId(), request.getDescription());
            ApiResponse<CommentDTO> response = new ApiResponse<>("SUCCESS", comment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<CommentDTO> errorResponse = new ApiResponse<>("FAILURE", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Récupérer tous les commentaires
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getAllComments() {
        try {
            List<CommentDTO> comments = commentService.getAllComments();
            ApiResponse<List<CommentDTO>> response = new ApiResponse<>("SUCCESS", comments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<CommentDTO>> errorResponse = new ApiResponse<>("FAILURE", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Récupérer un commentaire par ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> getCommentById(@PathVariable Integer id) {
        try {
            // Appel explicite à getCommentById qui peut lever une CommentNotFoundException
            CommentDTO comment = commentService.getCommentById(id)
                    .orElseThrow(() -> new commentsrvc.CommentNotFoundException("Comment not found"));

            ApiResponse<CommentDTO> response = new ApiResponse<>("SUCCESS", comment);
            return ResponseEntity.ok(response);

        } catch (commentsrvc.CommentNotFoundException e) {
            ApiResponse<CommentDTO> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiResponse<CommentDTO> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Mettre à jour un commentaire
    @PutMapping("/Update/{id}")
    public ResponseEntity<ApiResponse<CommentDTO>> updateComment(@PathVariable Integer id,
                                                                 @RequestParam String description) {
        try {
            CommentDTO updated = commentService.updateComment(id, description);
            ApiResponse<CommentDTO> response = new ApiResponse<>("SUCCESS", updated);
            return ResponseEntity.ok(response);
        } catch (commentsrvc.CommentNotFoundException e) {
            ApiResponse<CommentDTO> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiResponse<CommentDTO> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Supprimer un commentaire
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Integer id) {
        try {
            commentService.deleteComment(id);
            ApiResponse<Void> response = new ApiResponse<>("SUCCESS", null);
            return ResponseEntity.ok(response);
        } catch (commentsrvc.CommentNotFoundException e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Filtrer par utilisateur
    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentsByUserId(@PathVariable Integer userId) {
        try {
            List<CommentDTO> comments = commentService.getCommentsByUserId(userId);

            // Si la liste est vide, cela peut signifier qu'aucun commentaire n'a été trouvé pour cet utilisateur
            if (comments.isEmpty()) {
                ApiResponse<List<CommentDTO>> errorResponse = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            ApiResponse<List<CommentDTO>> successResponse = new ApiResponse<>("SUCCESS", comments);
            return ResponseEntity.ok(successResponse);

        } catch (Exception e) {
            ApiResponse<List<CommentDTO>> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Filtrer par cours
    @GetMapping("/getByCourse/{courseId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentsByCourseId(@PathVariable Integer courseId) {
        try {
            List<CommentDTO> comments = commentService.getCommentsByCourseId(courseId);

            if (comments == null || comments.isEmpty()) {
                ApiResponse<List<CommentDTO>> errorResponse = new ApiResponse<>("FAILURE", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            ApiResponse<List<CommentDTO>> successResponse = new ApiResponse<>("SUCCESS", comments);
            return ResponseEntity.ok(successResponse);

        } catch (Exception e) {
            ApiResponse<List<CommentDTO>> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}