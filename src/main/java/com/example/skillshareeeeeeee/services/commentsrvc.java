package com.example.skillshareeeeeeee.services;

import com.example.skillshareeeeeeee.dto.CommentDTO;
import com.example.skillshareeeeeeee.models.commentmdl;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.commentrep;
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.repositories.userrep;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class commentsrvc {

    private final commentrep commentRepository;
    private final userrep userRepository;
    private final courserep courseRepository;

    public commentsrvc(commentrep commentRepository, userrep userRepository, courserep courseRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public static class CommentNotFoundException extends RuntimeException {
        public CommentNotFoundException(String message) {
            super(message);
        }
    }

    private CommentDTO convertToDto(commentmdl comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getDescription(),
                comment.getUser().getId(),
                comment.getCourse().getId()
        );
    }

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public Optional<CommentDTO> getCommentById(Integer id) {
        return commentRepository.findById(id).map(this::convertToDto);
    }

    @Transactional
    public CommentDTO createComment(Integer userId, Integer courseId, String description) {
        usermdl user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        coursemdl course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        commentmdl comment = new commentmdl(description, user, course);
        commentmdl savedComment = commentRepository.save(comment);

        return convertToDto(savedComment);
    }

    @Transactional
    public CommentDTO updateComment(Integer id, String newDescription) {
        commentmdl comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
        comment.setDescription(newDescription);
        return convertToDto(commentRepository.save(comment));
    }

    public void deleteComment(Integer id) {
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }

    public List<CommentDTO> getCommentsByUserId(Integer userId) {
        return commentRepository.findByUser_Id(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByCourseId(Integer courseId) {
        return commentRepository.findByCourse_Id(courseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}