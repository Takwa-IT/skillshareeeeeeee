package com.example.skillshareeeeeeee.dto;

import com.example.skillshareeeeeeee.models.commentmdl;

public class CommentResponseDTO {

    private Integer id;
    private String description;
    private Integer userId;
    private Integer courseId;

    // Constructeur
    public CommentResponseDTO(commentmdl comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.userId = comment.getUser().getId();
        this.courseId = comment.getCourse().getId();
    }
}
