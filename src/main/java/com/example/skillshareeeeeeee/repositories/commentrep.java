package com.example.skillshareeeeeeee.repositories;

import com.example.skillshareeeeeeee.models.commentmdl;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface commentrep extends JpaRepository<commentmdl, Integer> {
    List<commentmdl> findByUser_Id(Integer userId);
    List<commentmdl> findByCourse_Id(Integer courseId);
}