package com.example.skillshareeeeeeee.repositories;

import com.example.skillshareeeeeeee.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryrep extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);

}
