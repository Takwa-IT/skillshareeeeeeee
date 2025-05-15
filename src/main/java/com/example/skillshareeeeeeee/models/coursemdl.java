package com.example.skillshareeeeeeee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class coursemdl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;

    @Column(columnDefinition = "integer default 0")
    private Integer viewCounts = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer downloadCounts = 0;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_owner")
    private usermdl owner;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<lessonmdl> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<commentmdl> comments = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseDeposit> deposits = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseFollow> follows = new ArrayList<>();

    @Transient
    private Integer number; // Champ non persisté en base

    // Constructeurs
    public coursemdl() {}

    public coursemdl(String title, String description, usermdl owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }

    // Getters & Setters (sans doublons)

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getView_counts() {
        return viewCounts;
    }

    public void setView_counts(Integer viewCounts) {
        this.viewCounts = viewCounts;
    }

    public Integer getDownload_counts() {
        return downloadCounts;
    }

    public void setDownload_counts(Integer downloadCounts) {
        this.downloadCounts = downloadCounts;
    }

    public usermdl getOwner() {
        return owner;
    }

    public void setOwner(usermdl owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<lessonmdl> getLessons() {
        return lessons;
    }

    public void setLessons(List<lessonmdl> lessons) {
        this.lessons = lessons;
    }

    public List<commentmdl> getComments() {
        return comments;
    }

    public void setComments(List<commentmdl> comments) {
        this.comments = comments;
    }

    public List<CourseDeposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<CourseDeposit> deposits) {
        this.deposits = deposits;
    }

    public List<CourseFollow> getFollows() {
        return follows;
    }

    public void setFollows(List<CourseFollow> follows) {
        this.follows = follows;
    }

    public Integer getNumber() {
        return lessons != null ? lessons.size() : 0;
    }

    // Méthodes utilitaires pour relations bidirectionnelles
    public void incrementViewCount() {
        this.viewCounts++;
    }

    public void incrementDownloadCount() {
        this.downloadCounts++;
    }

    public void addLesson(lessonmdl lesson) {
        lessons.add(lesson);
        lesson.setCourse(this);
    }

    public void removeLesson(lessonmdl lesson) {
        lessons.remove(lesson);
        if (lesson.getCourse() != null) {
            lesson.setCourse(null);
        }
    }

    public void addComment(commentmdl comment) {
        comments.add(comment);
        comment.setCourse(this);
    }

    public void removeComment(commentmdl comment) {
        comments.remove(comment);
        comment.setCourse(null);
    }
}