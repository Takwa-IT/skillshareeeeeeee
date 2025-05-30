package com.example.skillshareeeeeeee.models;


import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "lessons")
public class lessonmdl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Lob
    @Column(name = "url_pdf")
    private String urlPdf;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private coursemdl course;

    // Constructeurs
    public lessonmdl() {
    }

    public lessonmdl(String title, String urlPdf, coursemdl course) {
        this.title = title;
        this.urlPdf = urlPdf;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public coursemdl getCourse() {
        return course;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public void setCourse(coursemdl course) {
        if (this.course != null) {
            this.course.getLessons().remove(this);
        }

        this.course = course;

        if (course != null && !course.getLessons().contains(this)) {
            course.getLessons().add(this);
        }
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", urlPdf='" + urlPdf + '\'' +
                ", courseId=" + (course != null ? course.getId() : null) +
                '}';
    }
}