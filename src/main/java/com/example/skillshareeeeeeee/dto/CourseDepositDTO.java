package com.example.skillshareeeeeeee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class CourseDepositDTO {

    private Integer id;
    private Integer userId;
    private Integer courseId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date date;
    private Integer number;

    public CourseDepositDTO(Integer id, Date date, Integer number, Integer userId, Integer courseId) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.date = date;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "CourseDepositDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", courseId=" + courseId +
                ", date=" + date +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseDepositDTO)) return false;
        CourseDepositDTO that = (CourseDepositDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, courseId, date, number);
    }
}