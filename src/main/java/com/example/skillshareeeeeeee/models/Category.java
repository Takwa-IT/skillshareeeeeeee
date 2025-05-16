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

    @Lob // Indique que c'est un grand objet binaire
    private byte[] image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<coursemdl> courses = new ArrayList<>();

    public Category() {
    }

    public Category(String name, byte[] image) {
        this.name = name;
        this.image=image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() { return image; }

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

    public void setImage(byte[] getImage) {
        this.image = image;
    }

    public void setCourses(List<coursemdl> courses) {
        this.courses = courses;
    }

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
                ", image='" + image + '\'' +
                ", coursesCount=" + courses.size() +
                '}';
    }
}