package com.example.skillshareeeeeeee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class commentmdl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private usermdl user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private coursemdl course;

    // Constructeurs

    public commentmdl() {
        // Requis par JPA
    }

    public commentmdl(String description, usermdl user, coursemdl course) {
        this.description = description;
        this.user = user;
        this.course = course;
    }

    // Getters

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public usermdl getUser() {
        return user;
    }

    public coursemdl getCourse() {
        return course;
    }

    // Setters avec gestion bidirectionnelle

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(usermdl user) {
        if (this.user != null && this.user.getComments().contains(this)) {
            this.user.getComments().remove(this);
        }
        this.user = user;
        if (user != null && !user.getComments().contains(this)) {
            user.getComments().add(this);
        }
    }

    public void setCourse(coursemdl course) {
        if (this.course != null && this.course.getComments().contains(this)) {
            this.course.getComments().remove(this);
        }
        this.course = course;
        if (course != null && !course.getComments().contains(this)) {
            course.getComments().add(this);
        }
    }

    // Méthodes utilitaires

    public void associateWith(usermdl user, coursemdl course) {
        setUser(user);
        setCourse(course);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", userId=" + (user != null ? user.getId() : null) +
                ", courseId=" + (course != null ? course.getId() : null) +
                '}';
    }
}