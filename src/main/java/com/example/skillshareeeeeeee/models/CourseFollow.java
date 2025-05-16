package com.example.skillshareeeeeeee.models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "course_follow")
public class CourseFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private Integer number;
    private String progressReport;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private usermdl user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private coursemdl course;
    public CourseFollow() {
    }

    public CourseFollow(Date date, Integer number, String progressReport, usermdl user, coursemdl course) {
        this.date = date;
        this.number = number;
        this.progressReport = progressReport;
        this.user = user;
        this.course = course;
    }
    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getNumber() {
        return number;
    }

    public String getProgressReport() {
        return progressReport;
    }

    public usermdl getUser() {
        return user;
    }

    public coursemdl getCourse() {
        return course;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    public void setProgressReport(String progressReport) {
        this.progressReport = progressReport;
    }


    @Override
    public String toString() {
        return "CourseFollow{" +
                "id=" + id +
                ", date=" + date +
                ", number=" + number +
                ", progressReport='" + progressReport + '\'' +
                ", userId=" + (user != null ? user.getId() : null) +
                ", courseId=" + (course != null ? course.getId() : null) +
                '}';
    }

    public void setUser(usermdl user) {
        this.user=user;
    }

    public void setCourse(coursemdl course) {
        this.course=course;
    }
}