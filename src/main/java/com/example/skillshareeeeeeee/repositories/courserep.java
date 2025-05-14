package com.example.skillshareeeeeeee.repositories;

import com.example.skillshareeeeeeee.models.coursemdl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface courserep extends JpaRepository<coursemdl, Integer> {
    List<coursemdl> findByOwnerId(Integer ownerId);
    List<coursemdl> findByCategoryId(Integer categoryId);
    <coursemdl> boolean existsByTitle(String title);

}