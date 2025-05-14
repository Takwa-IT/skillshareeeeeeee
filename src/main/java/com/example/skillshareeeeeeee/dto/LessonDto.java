package com.example.skillshareeeeeeee.dto;

public class LessonDto {
    private Integer id;
    private String title;
    private String urlPdf;
    private Integer courseId;

    // Constructeurs
    public LessonDto() {}

    public LessonDto(Integer id, String title, String urlPdf, Integer courseId) {
        this.id = id;
        this.title = title;
        this.urlPdf = urlPdf;
        this.courseId = courseId;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}