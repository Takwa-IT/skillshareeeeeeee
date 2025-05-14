package com.example.skillshareeeeeeee.models;


import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "course_deposit")
public class CourseDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private usermdl user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private coursemdl course;

    // Constructeurs
    public CourseDeposit() {
    }

    public CourseDeposit(Date date, Integer number, usermdl user, coursemdl course) {
        this.date = date;
        this.number = number;
        this.user = user;
        this.course = course;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getNumber() {
        return number;
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

    // Méthode utilitaire pour la relation bidirectionnelle

    @Override
    public String toString() {
        return "CourseDeposit{" +
                "id=" + id +
                ", date=" + date +
                ", number=" + number +
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