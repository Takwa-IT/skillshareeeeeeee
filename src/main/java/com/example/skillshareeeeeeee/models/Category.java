package com.example.skillshareeeeeeee.models;


import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String urlImg;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<coursemdl> courses = new ArrayList<>();

    // Constructeurs
    public Category() {
        // Constructeur par défaut requis par JPA
    }

    public Category(String name, String urlImg) {
        this.name = name;
        this.urlImg = urlImg;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public List<coursemdl> getCourses() {
        return courses;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public void setCourses(List<coursemdl> courses) {
        this.courses = courses;
    }

    // Méthodes utilitaires pour la gestion bidirectionnelle
    public void addCourse(coursemdl course) {
        courses.add(course);
        course.setCategory(this);
    }

    public void removeCourse(coursemdl course) {
        courses.remove(course);
        course.setCategory(null);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", urlImg='" + urlImg + '\'' +
                ", coursesCount=" + courses.size() +
                '}';
    }
}