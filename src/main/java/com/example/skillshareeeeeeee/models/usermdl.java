package com.example.skillshareeeeeeee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class usermdl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    @Lob // Pour stocker l'image en BLOB
    private byte[] image;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<commentmdl> comments = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<coursemdl> courses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CourseDeposit> deposits = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CourseFollow> follows = new ArrayList<>();

    public usermdl() {
    }

    public usermdl(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public List<coursemdl> getCourses() {
        return courses;
    }
    public List<commentmdl> getComments() {
        return comments;
    }

    public void setComments(List<commentmdl> comments) {
        this.comments = comments;
    }

    public void setCourses(List<coursemdl> courses) {
        this.courses = courses;
    }

}