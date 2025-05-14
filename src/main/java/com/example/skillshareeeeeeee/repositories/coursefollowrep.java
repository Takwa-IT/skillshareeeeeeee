package com.example.skillshareeeeeeee.repositories;

import com.example.skillshareeeeeeee.models.CourseFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface coursefollowrep extends JpaRepository<CourseFollow, Integer> {
}
