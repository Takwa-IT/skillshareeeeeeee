package com.example.skillshareeeeeeee.repositories;

import com.example.skillshareeeeeeee.models.Category;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface courserep extends JpaRepository<coursemdl, Integer> {
    Optional<coursemdl> findById(Integer id);

}