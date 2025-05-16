package com.example.skillshareeeeeeee.dto;

public class CommentDTO {

    private Integer id;
    private String description;
    private Integer userId;
    private Integer courseId;

    public CommentDTO(Integer id, String description, Integer userId, Integer courseId) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.courseId = courseId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", courseId=" + courseId +
                '}';
    }
}