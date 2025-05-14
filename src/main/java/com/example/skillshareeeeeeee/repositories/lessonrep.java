package com.example.skillshareeeeeeee.repositories;

import com.example.skillshareeeeeeee.models.lessonmdl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface lessonrep extends JpaRepository<lessonmdl, Integer> {
    List<lessonmdl> findByCourseId(Integer courseId);
}