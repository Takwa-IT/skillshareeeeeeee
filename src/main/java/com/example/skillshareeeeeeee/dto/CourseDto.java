package com.example.skillshareeeeeeee.dto;

import java.util.List;

public class CourseDto {

    private Integer id;
    private String title;
    private String description;
    private Integer viewCounts;
    private Integer downloadCounts;
    private Integer userId;
    private Integer categoryId;
    private List<CommentDTO> comments;
    private List<LessonDto> lessons;

    public CourseDto(Integer id, String title, String description, Integer downloadCounts,
                     Integer viewCounts, Integer userId, Integer categoryId,
                     List<CommentDTO> comments, List<LessonDto> lessons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.downloadCounts = downloadCounts;
        this.viewCounts = viewCounts;
        this.userId = userId;
        this.categoryId = categoryId;
        this.comments = comments;
        this.lessons = lessons;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getViewCounts() { return viewCounts; }
    public Integer getDownloadCounts() { return downloadCounts; }
    public Integer getUserId() { return userId; }
    public Integer getCategoryId() { return categoryId; }
    public List<CommentDTO> getComments() { return comments; }
    public List<LessonDto> getLessons() { return lessons; }
}